package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.AssignTask;
import org.audit.app.repository.AssignTaskRepository;
import org.audit.app.service.AssignTaskService;
import org.audit.app.service.dto.AssignTaskDTO;
import org.audit.app.service.mapper.AssignTaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AssignTaskResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssignTaskResourceIT {

    private static final Instant DEFAULT_TASK_ASSIGNMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TASK_ASSIGNMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TASK_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TASK_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TASK_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TASK_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/assign-tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AssignTaskRepository assignTaskRepository;

    @Mock
    private AssignTaskRepository assignTaskRepositoryMock;

    @Autowired
    private AssignTaskMapper assignTaskMapper;

    @Mock
    private AssignTaskService assignTaskServiceMock;

    @Autowired
    private MockMvc restAssignTaskMockMvc;

    private AssignTask assignTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignTask createEntity() {
        AssignTask assignTask = new AssignTask()
            .taskAssignmentDate(DEFAULT_TASK_ASSIGNMENT_DATE)
            .taskStartDate(DEFAULT_TASK_START_DATE)
            .taskEndDate(DEFAULT_TASK_END_DATE)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return assignTask;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignTask createUpdatedEntity() {
        AssignTask assignTask = new AssignTask()
            .taskAssignmentDate(UPDATED_TASK_ASSIGNMENT_DATE)
            .taskStartDate(UPDATED_TASK_START_DATE)
            .taskEndDate(UPDATED_TASK_END_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        return assignTask;
    }

    @BeforeEach
    public void initTest() {
        assignTaskRepository.deleteAll();
        assignTask = createEntity();
    }

    @Test
    void createAssignTask() throws Exception {
        int databaseSizeBeforeCreate = assignTaskRepository.findAll().size();
        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);
        restAssignTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeCreate + 1);
        AssignTask testAssignTask = assignTaskList.get(assignTaskList.size() - 1);
        assertThat(testAssignTask.getTaskAssignmentDate()).isEqualTo(DEFAULT_TASK_ASSIGNMENT_DATE);
        assertThat(testAssignTask.getTaskStartDate()).isEqualTo(DEFAULT_TASK_START_DATE);
        assertThat(testAssignTask.getTaskEndDate()).isEqualTo(DEFAULT_TASK_END_DATE);
        assertThat(testAssignTask.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testAssignTask.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testAssignTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createAssignTaskWithExistingId() throws Exception {
        // Create the AssignTask with an existing ID
        assignTask.setId("existing_id");
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        int databaseSizeBeforeCreate = assignTaskRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAssignTasks() throws Exception {
        // Initialize the database
        assignTaskRepository.save(assignTask);

        // Get all the assignTaskList
        restAssignTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignTask.getId())))
            .andExpect(jsonPath("$.[*].taskAssignmentDate").value(hasItem(DEFAULT_TASK_ASSIGNMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].taskStartDate").value(hasItem(DEFAULT_TASK_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].taskEndDate").value(hasItem(DEFAULT_TASK_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssignTasksWithEagerRelationshipsIsEnabled() throws Exception {
        when(assignTaskServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssignTaskMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assignTaskServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssignTasksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assignTaskServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssignTaskMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assignTaskRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAssignTask() throws Exception {
        // Initialize the database
        assignTaskRepository.save(assignTask);

        // Get the assignTask
        restAssignTaskMockMvc
            .perform(get(ENTITY_API_URL_ID, assignTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignTask.getId()))
            .andExpect(jsonPath("$.taskAssignmentDate").value(DEFAULT_TASK_ASSIGNMENT_DATE.toString()))
            .andExpect(jsonPath("$.taskStartDate").value(DEFAULT_TASK_START_DATE.toString()))
            .andExpect(jsonPath("$.taskEndDate").value(DEFAULT_TASK_END_DATE.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingAssignTask() throws Exception {
        // Get the assignTask
        restAssignTaskMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAssignTask() throws Exception {
        // Initialize the database
        assignTaskRepository.save(assignTask);

        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();

        // Update the assignTask
        AssignTask updatedAssignTask = assignTaskRepository.findById(assignTask.getId()).get();
        updatedAssignTask
            .taskAssignmentDate(UPDATED_TASK_ASSIGNMENT_DATE)
            .taskStartDate(UPDATED_TASK_START_DATE)
            .taskEndDate(UPDATED_TASK_END_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(updatedAssignTask);

        restAssignTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assignTaskDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
        AssignTask testAssignTask = assignTaskList.get(assignTaskList.size() - 1);
        assertThat(testAssignTask.getTaskAssignmentDate()).isEqualTo(UPDATED_TASK_ASSIGNMENT_DATE);
        assertThat(testAssignTask.getTaskStartDate()).isEqualTo(UPDATED_TASK_START_DATE);
        assertThat(testAssignTask.getTaskEndDate()).isEqualTo(UPDATED_TASK_END_DATE);
        assertThat(testAssignTask.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testAssignTask.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testAssignTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();
        assignTask.setId(UUID.randomUUID().toString());

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assignTaskDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();
        assignTask.setId(UUID.randomUUID().toString());

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();
        assignTask.setId(UUID.randomUUID().toString());

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAssignTaskWithPatch() throws Exception {
        // Initialize the database
        assignTaskRepository.save(assignTask);

        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();

        // Update the assignTask using partial update
        AssignTask partialUpdatedAssignTask = new AssignTask();
        partialUpdatedAssignTask.setId(assignTask.getId());

        partialUpdatedAssignTask
            .taskStartDate(UPDATED_TASK_START_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restAssignTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssignTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssignTask))
            )
            .andExpect(status().isOk());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
        AssignTask testAssignTask = assignTaskList.get(assignTaskList.size() - 1);
        assertThat(testAssignTask.getTaskAssignmentDate()).isEqualTo(DEFAULT_TASK_ASSIGNMENT_DATE);
        assertThat(testAssignTask.getTaskStartDate()).isEqualTo(UPDATED_TASK_START_DATE);
        assertThat(testAssignTask.getTaskEndDate()).isEqualTo(DEFAULT_TASK_END_DATE);
        assertThat(testAssignTask.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testAssignTask.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testAssignTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateAssignTaskWithPatch() throws Exception {
        // Initialize the database
        assignTaskRepository.save(assignTask);

        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();

        // Update the assignTask using partial update
        AssignTask partialUpdatedAssignTask = new AssignTask();
        partialUpdatedAssignTask.setId(assignTask.getId());

        partialUpdatedAssignTask
            .taskAssignmentDate(UPDATED_TASK_ASSIGNMENT_DATE)
            .taskStartDate(UPDATED_TASK_START_DATE)
            .taskEndDate(UPDATED_TASK_END_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restAssignTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssignTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssignTask))
            )
            .andExpect(status().isOk());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
        AssignTask testAssignTask = assignTaskList.get(assignTaskList.size() - 1);
        assertThat(testAssignTask.getTaskAssignmentDate()).isEqualTo(UPDATED_TASK_ASSIGNMENT_DATE);
        assertThat(testAssignTask.getTaskStartDate()).isEqualTo(UPDATED_TASK_START_DATE);
        assertThat(testAssignTask.getTaskEndDate()).isEqualTo(UPDATED_TASK_END_DATE);
        assertThat(testAssignTask.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testAssignTask.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testAssignTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();
        assignTask.setId(UUID.randomUUID().toString());

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assignTaskDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();
        assignTask.setId(UUID.randomUUID().toString());

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();
        assignTask.setId(UUID.randomUUID().toString());

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assignTaskDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAssignTask() throws Exception {
        // Initialize the database
        assignTaskRepository.save(assignTask);

        int databaseSizeBeforeDelete = assignTaskRepository.findAll().size();

        // Delete the assignTask
        restAssignTaskMockMvc
            .perform(delete(ENTITY_API_URL_ID, assignTask.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
