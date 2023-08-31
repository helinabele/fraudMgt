package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.FraudInvestigationReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.FraudInvestigationReport}.
 */
public interface FraudInvestigationReportService {
    /**
     * Save a fraudInvestigationReport.
     *
     * @param fraudInvestigationReportDTO the entity to save.
     * @return the persisted entity.
     */
    FraudInvestigationReportDTO save(FraudInvestigationReportDTO fraudInvestigationReportDTO);

    /**
     * Updates a fraudInvestigationReport.
     *
     * @param fraudInvestigationReportDTO the entity to update.
     * @return the persisted entity.
     */
    FraudInvestigationReportDTO update(FraudInvestigationReportDTO fraudInvestigationReportDTO);

    /**
     * Partially updates a fraudInvestigationReport.
     *
     * @param fraudInvestigationReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FraudInvestigationReportDTO> partialUpdate(FraudInvestigationReportDTO fraudInvestigationReportDTO);

    /**
     * Get all the fraudInvestigationReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FraudInvestigationReportDTO> findAll(Pageable pageable);

    /**
     * Get all the fraudInvestigationReports with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FraudInvestigationReportDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fraudInvestigationReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FraudInvestigationReportDTO> findOne(String id);

    /**
     * Delete the "id" fraudInvestigationReport.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
