package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.FraudTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.FraudType}.
 */
public interface FraudTypeService {
    /**
     * Save a fraudType.
     *
     * @param fraudTypeDTO the entity to save.
     * @return the persisted entity.
     */
    FraudTypeDTO save(FraudTypeDTO fraudTypeDTO);

    /**
     * Updates a fraudType.
     *
     * @param fraudTypeDTO the entity to update.
     * @return the persisted entity.
     */
    FraudTypeDTO update(FraudTypeDTO fraudTypeDTO);

    /**
     * Partially updates a fraudType.
     *
     * @param fraudTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FraudTypeDTO> partialUpdate(FraudTypeDTO fraudTypeDTO);

    /**
     * Get all the fraudTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FraudTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fraudType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FraudTypeDTO> findOne(String id);

    /**
     * Delete the "id" fraudType.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
