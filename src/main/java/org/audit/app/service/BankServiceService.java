package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.BankServiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.BankService}.
 */
public interface BankServiceService {
    /**
     * Save a bankService.
     *
     * @param bankServiceDTO the entity to save.
     * @return the persisted entity.
     */
    BankServiceDTO save(BankServiceDTO bankServiceDTO);

    /**
     * Updates a bankService.
     *
     * @param bankServiceDTO the entity to update.
     * @return the persisted entity.
     */
    BankServiceDTO update(BankServiceDTO bankServiceDTO);

    /**
     * Partially updates a bankService.
     *
     * @param bankServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankServiceDTO> partialUpdate(BankServiceDTO bankServiceDTO);

    /**
     * Get all the bankServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankServiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bankService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankServiceDTO> findOne(String id);

    /**
     * Delete the "id" bankService.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
