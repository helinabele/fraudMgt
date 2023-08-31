package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.BankService;
import org.audit.app.repository.BankServiceRepository;
import org.audit.app.service.dto.BankServiceDTO;
import org.audit.app.service.mapper.BankServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link BankServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankServiceResourceIT {

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BankServiceRepository bankServiceRepository;

    @Autowired
    private BankServiceMapper bankServiceMapper;

    @Autowired
    private MockMvc restBankServiceMockMvc;

    private BankService bankService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankService createEntity() {
        BankService bankService = new BankService().serviceName(DEFAULT_SERVICE_NAME).description(DEFAULT_DESCRIPTION);
        return bankService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankService createUpdatedEntity() {
        BankService bankService = new BankService().serviceName(UPDATED_SERVICE_NAME).description(UPDATED_DESCRIPTION);
        return bankService;
    }

    @BeforeEach
    public void initTest() {
        bankServiceRepository.deleteAll();
        bankService = createEntity();
    }

    @Test
    void createBankService() throws Exception {
        int databaseSizeBeforeCreate = bankServiceRepository.findAll().size();
        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);
        restBankServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeCreate + 1);
        BankService testBankService = bankServiceList.get(bankServiceList.size() - 1);
        assertThat(testBankService.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testBankService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createBankServiceWithExistingId() throws Exception {
        // Create the BankService with an existing ID
        bankService.setId("existing_id");
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        int databaseSizeBeforeCreate = bankServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankServiceRepository.findAll().size();
        // set the field null
        bankService.setServiceName(null);

        // Create the BankService, which fails.
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        restBankServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllBankServices() throws Exception {
        // Initialize the database
        bankServiceRepository.save(bankService);

        // Get all the bankServiceList
        restBankServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankService.getId())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getBankService() throws Exception {
        // Initialize the database
        bankServiceRepository.save(bankService);

        // Get the bankService
        restBankServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, bankService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankService.getId()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingBankService() throws Exception {
        // Get the bankService
        restBankServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingBankService() throws Exception {
        // Initialize the database
        bankServiceRepository.save(bankService);

        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();

        // Update the bankService
        BankService updatedBankService = bankServiceRepository.findById(bankService.getId()).get();
        updatedBankService.serviceName(UPDATED_SERVICE_NAME).description(UPDATED_DESCRIPTION);
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(updatedBankService);

        restBankServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
        BankService testBankService = bankServiceList.get(bankServiceList.size() - 1);
        assertThat(testBankService.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testBankService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingBankService() throws Exception {
        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();
        bankService.setId(UUID.randomUUID().toString());

        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBankService() throws Exception {
        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();
        bankService.setId(UUID.randomUUID().toString());

        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBankService() throws Exception {
        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();
        bankService.setId(UUID.randomUUID().toString());

        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankServiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBankServiceWithPatch() throws Exception {
        // Initialize the database
        bankServiceRepository.save(bankService);

        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();

        // Update the bankService using partial update
        BankService partialUpdatedBankService = new BankService();
        partialUpdatedBankService.setId(bankService.getId());

        partialUpdatedBankService.serviceName(UPDATED_SERVICE_NAME);

        restBankServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankService))
            )
            .andExpect(status().isOk());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
        BankService testBankService = bankServiceList.get(bankServiceList.size() - 1);
        assertThat(testBankService.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testBankService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateBankServiceWithPatch() throws Exception {
        // Initialize the database
        bankServiceRepository.save(bankService);

        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();

        // Update the bankService using partial update
        BankService partialUpdatedBankService = new BankService();
        partialUpdatedBankService.setId(bankService.getId());

        partialUpdatedBankService.serviceName(UPDATED_SERVICE_NAME).description(UPDATED_DESCRIPTION);

        restBankServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankService))
            )
            .andExpect(status().isOk());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
        BankService testBankService = bankServiceList.get(bankServiceList.size() - 1);
        assertThat(testBankService.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testBankService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingBankService() throws Exception {
        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();
        bankService.setId(UUID.randomUUID().toString());

        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankServiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBankService() throws Exception {
        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();
        bankService.setId(UUID.randomUUID().toString());

        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBankService() throws Exception {
        int databaseSizeBeforeUpdate = bankServiceRepository.findAll().size();
        bankService.setId(UUID.randomUUID().toString());

        // Create the BankService
        BankServiceDTO bankServiceDTO = bankServiceMapper.toDto(bankService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankService in the database
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBankService() throws Exception {
        // Initialize the database
        bankServiceRepository.save(bankService);

        int databaseSizeBeforeDelete = bankServiceRepository.findAll().size();

        // Delete the bankService
        restBankServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankService> bankServiceList = bankServiceRepository.findAll();
        assertThat(bankServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
