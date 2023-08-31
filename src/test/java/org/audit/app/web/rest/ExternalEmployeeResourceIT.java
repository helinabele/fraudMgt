package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.ExternalEmployee;
import org.audit.app.repository.ExternalEmployeeRepository;
import org.audit.app.service.dto.ExternalEmployeeDTO;
import org.audit.app.service.mapper.ExternalEmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ExternalEmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExternalEmployeeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/external-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ExternalEmployeeRepository externalEmployeeRepository;

    @Autowired
    private ExternalEmployeeMapper externalEmployeeMapper;

    @Autowired
    private MockMvc restExternalEmployeeMockMvc;

    private ExternalEmployee externalEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalEmployee createEntity() {
        ExternalEmployee externalEmployee = new ExternalEmployee()
            .name(DEFAULT_NAME)
            .occupation(DEFAULT_OCCUPATION)
            .telephone(DEFAULT_TELEPHONE)
            .address(DEFAULT_ADDRESS);
        return externalEmployee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalEmployee createUpdatedEntity() {
        ExternalEmployee externalEmployee = new ExternalEmployee()
            .name(UPDATED_NAME)
            .occupation(UPDATED_OCCUPATION)
            .telephone(UPDATED_TELEPHONE)
            .address(UPDATED_ADDRESS);
        return externalEmployee;
    }

    @BeforeEach
    public void initTest() {
        externalEmployeeRepository.deleteAll();
        externalEmployee = createEntity();
    }

    @Test
    void createExternalEmployee() throws Exception {
        int databaseSizeBeforeCreate = externalEmployeeRepository.findAll().size();
        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);
        restExternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalEmployee testExternalEmployee = externalEmployeeList.get(externalEmployeeList.size() - 1);
        assertThat(testExternalEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExternalEmployee.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testExternalEmployee.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testExternalEmployee.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void createExternalEmployeeWithExistingId() throws Exception {
        // Create the ExternalEmployee with an existing ID
        externalEmployee.setId("existing_id");
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        int databaseSizeBeforeCreate = externalEmployeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalEmployeeRepository.findAll().size();
        // set the field null
        externalEmployee.setName(null);

        // Create the ExternalEmployee, which fails.
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        restExternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalEmployeeRepository.findAll().size();
        // set the field null
        externalEmployee.setAddress(null);

        // Create the ExternalEmployee, which fails.
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        restExternalEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllExternalEmployees() throws Exception {
        // Initialize the database
        externalEmployeeRepository.save(externalEmployee);

        // Get all the externalEmployeeList
        restExternalEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalEmployee.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getExternalEmployee() throws Exception {
        // Initialize the database
        externalEmployeeRepository.save(externalEmployee);

        // Get the externalEmployee
        restExternalEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, externalEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(externalEmployee.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingExternalEmployee() throws Exception {
        // Get the externalEmployee
        restExternalEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingExternalEmployee() throws Exception {
        // Initialize the database
        externalEmployeeRepository.save(externalEmployee);

        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();

        // Update the externalEmployee
        ExternalEmployee updatedExternalEmployee = externalEmployeeRepository.findById(externalEmployee.getId()).get();
        updatedExternalEmployee.name(UPDATED_NAME).occupation(UPDATED_OCCUPATION).telephone(UPDATED_TELEPHONE).address(UPDATED_ADDRESS);
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(updatedExternalEmployee);

        restExternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, externalEmployeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
        ExternalEmployee testExternalEmployee = externalEmployeeList.get(externalEmployeeList.size() - 1);
        assertThat(testExternalEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExternalEmployee.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testExternalEmployee.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testExternalEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void putNonExistingExternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();
        externalEmployee.setId(UUID.randomUUID().toString());

        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, externalEmployeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchExternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();
        externalEmployee.setId(UUID.randomUUID().toString());

        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamExternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();
        externalEmployee.setId(UUID.randomUUID().toString());

        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateExternalEmployeeWithPatch() throws Exception {
        // Initialize the database
        externalEmployeeRepository.save(externalEmployee);

        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();

        // Update the externalEmployee using partial update
        ExternalEmployee partialUpdatedExternalEmployee = new ExternalEmployee();
        partialUpdatedExternalEmployee.setId(externalEmployee.getId());

        partialUpdatedExternalEmployee.occupation(UPDATED_OCCUPATION).telephone(UPDATED_TELEPHONE).address(UPDATED_ADDRESS);

        restExternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExternalEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExternalEmployee))
            )
            .andExpect(status().isOk());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
        ExternalEmployee testExternalEmployee = externalEmployeeList.get(externalEmployeeList.size() - 1);
        assertThat(testExternalEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExternalEmployee.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testExternalEmployee.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testExternalEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void fullUpdateExternalEmployeeWithPatch() throws Exception {
        // Initialize the database
        externalEmployeeRepository.save(externalEmployee);

        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();

        // Update the externalEmployee using partial update
        ExternalEmployee partialUpdatedExternalEmployee = new ExternalEmployee();
        partialUpdatedExternalEmployee.setId(externalEmployee.getId());

        partialUpdatedExternalEmployee
            .name(UPDATED_NAME)
            .occupation(UPDATED_OCCUPATION)
            .telephone(UPDATED_TELEPHONE)
            .address(UPDATED_ADDRESS);

        restExternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExternalEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExternalEmployee))
            )
            .andExpect(status().isOk());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
        ExternalEmployee testExternalEmployee = externalEmployeeList.get(externalEmployeeList.size() - 1);
        assertThat(testExternalEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExternalEmployee.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testExternalEmployee.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testExternalEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void patchNonExistingExternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();
        externalEmployee.setId(UUID.randomUUID().toString());

        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, externalEmployeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchExternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();
        externalEmployee.setId(UUID.randomUUID().toString());

        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamExternalEmployee() throws Exception {
        int databaseSizeBeforeUpdate = externalEmployeeRepository.findAll().size();
        externalEmployee.setId(UUID.randomUUID().toString());

        // Create the ExternalEmployee
        ExternalEmployeeDTO externalEmployeeDTO = externalEmployeeMapper.toDto(externalEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(externalEmployeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExternalEmployee in the database
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteExternalEmployee() throws Exception {
        // Initialize the database
        externalEmployeeRepository.save(externalEmployee);

        int databaseSizeBeforeDelete = externalEmployeeRepository.findAll().size();

        // Delete the externalEmployee
        restExternalEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, externalEmployee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExternalEmployee> externalEmployeeList = externalEmployeeRepository.findAll();
        assertThat(externalEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
