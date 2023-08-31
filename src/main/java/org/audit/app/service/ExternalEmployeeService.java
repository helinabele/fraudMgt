package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.ExternalEmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.ExternalEmployee}.
 */
public interface ExternalEmployeeService {
    /**
     * Save a externalEmployee.
     *
     * @param externalEmployeeDTO the entity to save.
     * @return the persisted entity.
     */
    ExternalEmployeeDTO save(ExternalEmployeeDTO externalEmployeeDTO);

    /**
     * Updates a externalEmployee.
     *
     * @param externalEmployeeDTO the entity to update.
     * @return the persisted entity.
     */
    ExternalEmployeeDTO update(ExternalEmployeeDTO externalEmployeeDTO);

    /**
     * Partially updates a externalEmployee.
     *
     * @param externalEmployeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExternalEmployeeDTO> partialUpdate(ExternalEmployeeDTO externalEmployeeDTO);

    /**
     * Get all the externalEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExternalEmployeeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" externalEmployee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExternalEmployeeDTO> findOne(String id);

    /**
     * Delete the "id" externalEmployee.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
