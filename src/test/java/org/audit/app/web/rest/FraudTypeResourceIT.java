package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.FraudType;
import org.audit.app.repository.FraudTypeRepository;
import org.audit.app.service.dto.FraudTypeDTO;
import org.audit.app.service.mapper.FraudTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FraudTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FraudTypeResourceIT {

    private static final String DEFAULT_FRAUD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FRAUD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/fraud-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FraudTypeRepository fraudTypeRepository;

    @Autowired
    private FraudTypeMapper fraudTypeMapper;

    @Autowired
    private MockMvc restFraudTypeMockMvc;

    private FraudType fraudType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraudType createEntity() {
        FraudType fraudType = new FraudType()
            .fraudName(DEFAULT_FRAUD_NAME)
            .description(DEFAULT_DESCRIPTION)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        return fraudType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraudType createUpdatedEntity() {
        FraudType fraudType = new FraudType()
            .fraudName(UPDATED_FRAUD_NAME)
            .description(UPDATED_DESCRIPTION)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        return fraudType;
    }

    @BeforeEach
    public void initTest() {
        fraudTypeRepository.deleteAll();
        fraudType = createEntity();
    }

    @Test
    void createFraudType() throws Exception {
        int databaseSizeBeforeCreate = fraudTypeRepository.findAll().size();
        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);
        restFraudTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FraudType testFraudType = fraudTypeList.get(fraudTypeList.size() - 1);
        assertThat(testFraudType.getFraudName()).isEqualTo(DEFAULT_FRAUD_NAME);
        assertThat(testFraudType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFraudType.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testFraudType.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    void createFraudTypeWithExistingId() throws Exception {
        // Create the FraudType with an existing ID
        fraudType.setId("existing_id");
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        int databaseSizeBeforeCreate = fraudTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraudTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFraudTypes() throws Exception {
        // Initialize the database
        fraudTypeRepository.save(fraudType);

        // Get all the fraudTypeList
        restFraudTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraudType.getId())))
            .andExpect(jsonPath("$.[*].fraudName").value(hasItem(DEFAULT_FRAUD_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));
    }

    @Test
    void getFraudType() throws Exception {
        // Initialize the database
        fraudTypeRepository.save(fraudType);

        // Get the fraudType
        restFraudTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, fraudType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraudType.getId()))
            .andExpect(jsonPath("$.fraudName").value(DEFAULT_FRAUD_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)));
    }

    @Test
    void getNonExistingFraudType() throws Exception {
        // Get the fraudType
        restFraudTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFraudType() throws Exception {
        // Initialize the database
        fraudTypeRepository.save(fraudType);

        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();

        // Update the fraudType
        FraudType updatedFraudType = fraudTypeRepository.findById(fraudType.getId()).get();
        updatedFraudType
            .fraudName(UPDATED_FRAUD_NAME)
            .description(UPDATED_DESCRIPTION)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(updatedFraudType);

        restFraudTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraudTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
        FraudType testFraudType = fraudTypeList.get(fraudTypeList.size() - 1);
        assertThat(testFraudType.getFraudName()).isEqualTo(UPDATED_FRAUD_NAME);
        assertThat(testFraudType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFraudType.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testFraudType.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    void putNonExistingFraudType() throws Exception {
        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();
        fraudType.setId(UUID.randomUUID().toString());

        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraudTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFraudType() throws Exception {
        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();
        fraudType.setId(UUID.randomUUID().toString());

        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFraudType() throws Exception {
        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();
        fraudType.setId(UUID.randomUUID().toString());

        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFraudTypeWithPatch() throws Exception {
        // Initialize the database
        fraudTypeRepository.save(fraudType);

        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();

        // Update the fraudType using partial update
        FraudType partialUpdatedFraudType = new FraudType();
        partialUpdatedFraudType.setId(fraudType.getId());

        restFraudTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraudType))
            )
            .andExpect(status().isOk());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
        FraudType testFraudType = fraudTypeList.get(fraudTypeList.size() - 1);
        assertThat(testFraudType.getFraudName()).isEqualTo(DEFAULT_FRAUD_NAME);
        assertThat(testFraudType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFraudType.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testFraudType.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    void fullUpdateFraudTypeWithPatch() throws Exception {
        // Initialize the database
        fraudTypeRepository.save(fraudType);

        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();

        // Update the fraudType using partial update
        FraudType partialUpdatedFraudType = new FraudType();
        partialUpdatedFraudType.setId(fraudType.getId());

        partialUpdatedFraudType
            .fraudName(UPDATED_FRAUD_NAME)
            .description(UPDATED_DESCRIPTION)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restFraudTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraudType))
            )
            .andExpect(status().isOk());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
        FraudType testFraudType = fraudTypeList.get(fraudTypeList.size() - 1);
        assertThat(testFraudType.getFraudName()).isEqualTo(UPDATED_FRAUD_NAME);
        assertThat(testFraudType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFraudType.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testFraudType.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    void patchNonExistingFraudType() throws Exception {
        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();
        fraudType.setId(UUID.randomUUID().toString());

        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fraudTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFraudType() throws Exception {
        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();
        fraudType.setId(UUID.randomUUID().toString());

        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFraudType() throws Exception {
        int databaseSizeBeforeUpdate = fraudTypeRepository.findAll().size();
        fraudType.setId(UUID.randomUUID().toString());

        // Create the FraudType
        FraudTypeDTO fraudTypeDTO = fraudTypeMapper.toDto(fraudType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fraudTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraudType in the database
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFraudType() throws Exception {
        // Initialize the database
        fraudTypeRepository.save(fraudType);

        int databaseSizeBeforeDelete = fraudTypeRepository.findAll().size();

        // Delete the fraudType
        restFraudTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, fraudType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FraudType> fraudTypeList = fraudTypeRepository.findAll();
        assertThat(fraudTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
