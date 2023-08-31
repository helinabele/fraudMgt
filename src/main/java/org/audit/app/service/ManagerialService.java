package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.ManagerialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.Managerial}.
 */
public interface ManagerialService {
    /**
     * Save a managerial.
     *
     * @param managerialDTO the entity to save.
     * @return the persisted entity.
     */
    ManagerialDTO save(ManagerialDTO managerialDTO);

    /**
     * Updates a managerial.
     *
     * @param managerialDTO the entity to update.
     * @return the persisted entity.
     */
    ManagerialDTO update(ManagerialDTO managerialDTO);

    /**
     * Partially updates a managerial.
     *
     * @param managerialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManagerialDTO> partialUpdate(ManagerialDTO managerialDTO);

    /**
     * Get all the managerials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManagerialDTO> findAll(Pageable pageable);

    /**
     * Get all the managerials with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManagerialDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" managerial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManagerialDTO> findOne(String id);

    /**
     * Delete the "id" managerial.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
