package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.FraudKnowledgeManagementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.FraudKnowledgeManagement}.
 */
public interface FraudKnowledgeManagementService {
    /**
     * Save a fraudKnowledgeManagement.
     *
     * @param fraudKnowledgeManagementDTO the entity to save.
     * @return the persisted entity.
     */
    FraudKnowledgeManagementDTO save(FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO);

    /**
     * Updates a fraudKnowledgeManagement.
     *
     * @param fraudKnowledgeManagementDTO the entity to update.
     * @return the persisted entity.
     */
    FraudKnowledgeManagementDTO update(FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO);

    /**
     * Partially updates a fraudKnowledgeManagement.
     *
     * @param fraudKnowledgeManagementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FraudKnowledgeManagementDTO> partialUpdate(FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO);

    /**
     * Get all the fraudKnowledgeManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FraudKnowledgeManagementDTO> findAll(Pageable pageable);

    /**
     * Get all the fraudKnowledgeManagements with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FraudKnowledgeManagementDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fraudKnowledgeManagement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FraudKnowledgeManagementDTO> findOne(String id);

    /**
     * Delete the "id" fraudKnowledgeManagement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
