package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.InternalEmployee;
import org.audit.app.repository.InternalEmployeeRepository;
import org.audit.app.service.dto.InternalEmployeeDTO;
import org.audit.app.service.mapper.InternalEmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link InternalEmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InternalEmployeeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/internal-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private InternalEmployeeRepository internalEmployeeRepository;

    @Autowired
    private InternalEmployeeMapper internalEmployeeMapper;

    @Autowired
    private MockMvc restInternalEmployeeMockMvc;

    private InternalEmployee internalEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalEmployee createEntity() {
        InternalEmployee internalEmployee = new InternalEmployee()
            .name(DEFAULT_NAME)
            .position(DEFAULT_POSITION)
            .grade(DEFAULT_GRADE)
            .experience(DEFAULT_EXPERIENCE)
            .branch(DEFAULT_BRANCH);
        return internalEmployee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalEmployee createUpdatedEntity() {
        InternalEmployee internalEmployee = new InternalEmployee()
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .grade(UPDATED_GRADE)
            .experience(UPDATED_EXPERIENCE)
            .branch(UPDATED_BRANCH);
        return internalEmployee;
    }

    @BeforeEach
    public void initTest() {
        internalEmployeeRepository.deleteAll();
        internalEmployee = createEntity();
    }

    @Test
    void createInternalEmployee() throws Exception {
        int databaseSizeBeforeCreate = internalEmployeeRepository.findAll().size();
        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);
        restInternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        InternalEmployee testInternalEmployee = internalEmployeeList.get(internalEmployeeList.size() - 1);
        assertThat(testInternalEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInternalEmployee.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testInternalEmployee.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testInternalEmployee.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testInternalEmployee.getBranch()).isEqualTo(DEFAULT_BRANCH);
    }

    @Test
    void createInternalEmployeeWithExistingId() throws Exception {
        // Create the InternalEmployee with an existing ID
        internalEmployee.setId("existing_id");
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        int databaseSizeBeforeCreate = internalEmployeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalEmployeeRepository.findAll().size();
        // set the field null
        internalEmployee.setName(null);

        // Create the InternalEmployee, which fails.
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        restInternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalEmployeeRepository.findAll().size();
        // set the field null
        internalEmployee.setPosition(null);

        // Create the InternalEmployee, which fails.
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        restInternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllInternalEmployees() throws Exception {
        // Initialize the database
        internalEmployeeRepository.save(internalEmployee);

        // Get all the internalEmployeeList
        restInternalEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalEmployee.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)));
    }

    @Test
    void getInternalEmployee() throws Exception {
        // Initialize the database
        internalEmployeeRepository.save(internalEmployee);

        // Get the internalEmployee
        restInternalEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, internalEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(internalEmployee.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH));
    }

    @Test
    void getNonExistingInternalEmployee() throws Exception {
        // Get the internalEmployee
        restInternalEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingInternalEmployee() throws Exception {
        // Initialize the database
        internalEmployeeRepository.save(internalEmployee);

        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();

        // Update the internalEmployee
        InternalEmployee updatedInternalEmployee = internalEmployeeRepository.findById(internalEmployee.getId()).get();
        updatedInternalEmployee
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .grade(UPDATED_GRADE)
            .experience(UPDATED_EXPERIENCE)
            .branch(UPDATED_BRANCH);
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(updatedInternalEmployee);

        restInternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, internalEmployeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
        InternalEmployee testInternalEmployee = internalEmployeeList.get(internalEmployeeList.size() - 1);
        assertThat(testInternalEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInternalEmployee.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInternalEmployee.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testInternalEmployee.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testInternalEmployee.getBranch()).isEqualTo(UPDATED_BRANCH);
    }

    @Test
    void putNonExistingInternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();
        internalEmployee.setId(UUID.randomUUID().toString());

        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, internalEmployeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();
        internalEmployee.setId(UUID.randomUUID().toString());

        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();
        internalEmployee.setId(UUID.randomUUID().toString());

        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInternalEmployeeWithPatch() throws Exception {
        // Initialize the database
        internalEmployeeRepository.save(internalEmployee);

        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();

        // Update the internalEmployee using partial update
        InternalEmployee partialUpdatedInternalEmployee = new InternalEmployee();
        partialUpdatedInternalEmployee.setId(internalEmployee.getId());

        partialUpdatedInternalEmployee.position(UPDATED_POSITION).experience(UPDATED_EXPERIENCE);

        restInternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInternalEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInternalEmployee))
            )
            .andExpect(status().isOk());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
        InternalEmployee testInternalEmployee = internalEmployeeList.get(internalEmployeeList.size() - 1);
        assertThat(testInternalEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInternalEmployee.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInternalEmployee.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testInternalEmployee.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testInternalEmployee.getBranch()).isEqualTo(DEFAULT_BRANCH);
    }

    @Test
    void fullUpdateInternalEmployeeWithPatch() throws Exception {
        // Initialize the database
        internalEmployeeRepository.save(internalEmployee);

        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();

        // Update the internalEmployee using partial update
        InternalEmployee partialUpdatedInternalEmployee = new InternalEmployee();
        partialUpdatedInternalEmployee.setId(internalEmployee.getId());

        partialUpdatedInternalEmployee
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .grade(UPDATED_GRADE)
            .experience(UPDATED_EXPERIENCE)
            .branch(UPDATED_BRANCH);

        restInternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInternalEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInternalEmployee))
            )
            .andExpect(status().isOk());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
        InternalEmployee testInternalEmployee = internalEmployeeList.get(internalEmployeeList.size() - 1);
        assertThat(testInternalEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInternalEmployee.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInternalEmployee.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testInternalEmployee.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testInternalEmployee.getBranch()).isEqualTo(UPDATED_BRANCH);
    }

    @Test
    void patchNonExistingInternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();
        internalEmployee.setId(UUID.randomUUID().toString());

        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, internalEmployeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();
        internalEmployee.setId(UUID.randomUUID().toString());

        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = internalEmployeeRepository.findAll().size();
        internalEmployee.setId(UUID.randomUUID().toString());

        // Create the InternalEmployee
        InternalEmployeeDTO internalEmployeeDTO = internalEmployeeMapper.toDto(internalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internalEmployeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InternalEmployee in the database
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInternalEmployee() throws Exception {
        // Initialize the database
        internalEmployeeRepository.save(internalEmployee);

        int databaseSizeBeforeDelete = internalEmployeeRepository.findAll().size();

        // Delete the internalEmployee
        restInternalEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, internalEmployee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InternalEmployee> internalEmployeeList = internalEmployeeRepository.findAll();
        assertThat(internalEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
