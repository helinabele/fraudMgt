package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.Managerial;
import org.audit.app.repository.ManagerialRepository;
import org.audit.app.service.ManagerialService;
import org.audit.app.service.dto.ManagerialDTO;
import org.audit.app.service.mapper.ManagerialMapper;
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

/**
 * Integration tests for the {@link ManagerialResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ManagerialResourceIT {

    private static final String DEFAULT_MANAGERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIRECTOR_ID = 1;
    private static final Integer UPDATED_DIRECTOR_ID = 2;

    private static final String ENTITY_API_URL = "/api/managerials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ManagerialRepository managerialRepository;

    @Mock
    private ManagerialRepository managerialRepositoryMock;

    @Autowired
    private ManagerialMapper managerialMapper;

    @Mock
    private ManagerialService managerialServiceMock;

    @Autowired
    private MockMvc restManagerialMockMvc;

    private Managerial managerial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Managerial createEntity() {
        Managerial managerial = new Managerial()
            .managerialName(DEFAULT_MANAGERIAL_NAME)
            .description(DEFAULT_DESCRIPTION)
            .directorId(DEFAULT_DIRECTOR_ID);
        return managerial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Managerial createUpdatedEntity() {
        Managerial managerial = new Managerial()
            .managerialName(UPDATED_MANAGERIAL_NAME)
            .description(UPDATED_DESCRIPTION)
            .directorId(UPDATED_DIRECTOR_ID);
        return managerial;
    }

    @BeforeEach
    public void initTest() {
        managerialRepository.deleteAll();
        managerial = createEntity();
    }

    @Test
    void createManagerial() throws Exception {
        int databaseSizeBeforeCreate = managerialRepository.findAll().size();
        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);
        restManagerialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managerialDTO)))
            .andExpect(status().isCreated());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeCreate + 1);
        Managerial testManagerial = managerialList.get(managerialList.size() - 1);
        assertThat(testManagerial.getManagerialName()).isEqualTo(DEFAULT_MANAGERIAL_NAME);
        assertThat(testManagerial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testManagerial.getDirectorId()).isEqualTo(DEFAULT_DIRECTOR_ID);
    }

    @Test
    void createManagerialWithExistingId() throws Exception {
        // Create the Managerial with an existing ID
        managerial.setId("existing_id");
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        int databaseSizeBeforeCreate = managerialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagerialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managerialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllManagerials() throws Exception {
        // Initialize the database
        managerialRepository.save(managerial);

        // Get all the managerialList
        restManagerialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(managerial.getId())))
            .andExpect(jsonPath("$.[*].managerialName").value(hasItem(DEFAULT_MANAGERIAL_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].directorId").value(hasItem(DEFAULT_DIRECTOR_ID)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllManagerialsWithEagerRelationshipsIsEnabled() throws Exception {
        when(managerialServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restManagerialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(managerialServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllManagerialsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(managerialServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restManagerialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(managerialRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getManagerial() throws Exception {
        // Initialize the database
        managerialRepository.save(managerial);

        // Get the managerial
        restManagerialMockMvc
            .perform(get(ENTITY_API_URL_ID, managerial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(managerial.getId()))
            .andExpect(jsonPath("$.managerialName").value(DEFAULT_MANAGERIAL_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.directorId").value(DEFAULT_DIRECTOR_ID));
    }

    @Test
    void getNonExistingManagerial() throws Exception {
        // Get the managerial
        restManagerialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingManagerial() throws Exception {
        // Initialize the database
        managerialRepository.save(managerial);

        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();

        // Update the managerial
        Managerial updatedManagerial = managerialRepository.findById(managerial.getId()).get();
        updatedManagerial.managerialName(UPDATED_MANAGERIAL_NAME).description(UPDATED_DESCRIPTION).directorId(UPDATED_DIRECTOR_ID);
        ManagerialDTO managerialDTO = managerialMapper.toDto(updatedManagerial);

        restManagerialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managerialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managerialDTO))
            )
            .andExpect(status().isOk());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
        Managerial testManagerial = managerialList.get(managerialList.size() - 1);
        assertThat(testManagerial.getManagerialName()).isEqualTo(UPDATED_MANAGERIAL_NAME);
        assertThat(testManagerial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testManagerial.getDirectorId()).isEqualTo(UPDATED_DIRECTOR_ID);
    }

    @Test
    void putNonExistingManagerial() throws Exception {
        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();
        managerial.setId(UUID.randomUUID().toString());

        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagerialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managerialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managerialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchManagerial() throws Exception {
        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();
        managerial.setId(UUID.randomUUID().toString());

        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagerialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managerialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamManagerial() throws Exception {
        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();
        managerial.setId(UUID.randomUUID().toString());

        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagerialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managerialDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateManagerialWithPatch() throws Exception {
        // Initialize the database
        managerialRepository.save(managerial);

        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();

        // Update the managerial using partial update
        Managerial partialUpdatedManagerial = new Managerial();
        partialUpdatedManagerial.setId(managerial.getId());

        restManagerialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagerial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagerial))
            )
            .andExpect(status().isOk());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
        Managerial testManagerial = managerialList.get(managerialList.size() - 1);
        assertThat(testManagerial.getManagerialName()).isEqualTo(DEFAULT_MANAGERIAL_NAME);
        assertThat(testManagerial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testManagerial.getDirectorId()).isEqualTo(DEFAULT_DIRECTOR_ID);
    }

    @Test
    void fullUpdateManagerialWithPatch() throws Exception {
        // Initialize the database
        managerialRepository.save(managerial);

        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();

        // Update the managerial using partial update
        Managerial partialUpdatedManagerial = new Managerial();
        partialUpdatedManagerial.setId(managerial.getId());

        partialUpdatedManagerial.managerialName(UPDATED_MANAGERIAL_NAME).description(UPDATED_DESCRIPTION).directorId(UPDATED_DIRECTOR_ID);

        restManagerialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagerial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagerial))
            )
            .andExpect(status().isOk());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
        Managerial testManagerial = managerialList.get(managerialList.size() - 1);
        assertThat(testManagerial.getManagerialName()).isEqualTo(UPDATED_MANAGERIAL_NAME);
        assertThat(testManagerial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testManagerial.getDirectorId()).isEqualTo(UPDATED_DIRECTOR_ID);
    }

    @Test
    void patchNonExistingManagerial() throws Exception {
        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();
        managerial.setId(UUID.randomUUID().toString());

        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagerialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, managerialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managerialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchManagerial() throws Exception {
        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();
        managerial.setId(UUID.randomUUID().toString());

        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagerialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managerialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamManagerial() throws Exception {
        int databaseSizeBeforeUpdate = managerialRepository.findAll().size();
        managerial.setId(UUID.randomUUID().toString());

        // Create the Managerial
        ManagerialDTO managerialDTO = managerialMapper.toDto(managerial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagerialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(managerialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Managerial in the database
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteManagerial() throws Exception {
        // Initialize the database
        managerialRepository.save(managerial);

        int databaseSizeBeforeDelete = managerialRepository.findAll().size();

        // Delete the managerial
        restManagerialMockMvc
            .perform(delete(ENTITY_API_URL_ID, managerial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Managerial> managerialList = managerialRepository.findAll();
        assertThat(managerialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
