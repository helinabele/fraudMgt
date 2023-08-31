package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.InternalEmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.InternalEmployee}.
 */
public interface InternalEmployeeService {
    /**
     * Save a internalEmployee.
     *
     * @param internalEmployeeDTO the entity to save.
     * @return the persisted entity.
     */
    InternalEmployeeDTO save(InternalEmployeeDTO internalEmployeeDTO);

    /**
     * Updates a internalEmployee.
     *
     * @param internalEmployeeDTO the entity to update.
     * @return the persisted entity.
     */
    InternalEmployeeDTO update(InternalEmployeeDTO internalEmployeeDTO);

    /**
     * Partially updates a internalEmployee.
     *
     * @param internalEmployeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InternalEmployeeDTO> partialUpdate(InternalEmployeeDTO internalEmployeeDTO);

    /**
     * Get all the internalEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InternalEmployeeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" internalEmployee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InternalEmployeeDTO> findOne(String id);

    /**
     * Delete the "id" internalEmployee.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
