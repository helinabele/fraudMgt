package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.TeamLeadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.TeamLead}.
 */
public interface TeamLeadService {
    /**
     * Save a teamLead.
     *
     * @param teamLeadDTO the entity to save.
     * @return the persisted entity.
     */
    TeamLeadDTO save(TeamLeadDTO teamLeadDTO);

    /**
     * Updates a teamLead.
     *
     * @param teamLeadDTO the entity to update.
     * @return the persisted entity.
     */
    TeamLeadDTO update(TeamLeadDTO teamLeadDTO);

    /**
     * Partially updates a teamLead.
     *
     * @param teamLeadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeamLeadDTO> partialUpdate(TeamLeadDTO teamLeadDTO);

    /**
     * Get all the teamLeads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamLeadDTO> findAll(Pageable pageable);

    /**
     * Get all the teamLeads with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamLeadDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" teamLead.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamLeadDTO> findOne(String id);

    /**
     * Delete the "id" teamLead.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
