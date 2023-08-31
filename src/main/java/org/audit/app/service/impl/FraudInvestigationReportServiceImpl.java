package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.FraudInvestigationReport;
import org.audit.app.repository.FraudInvestigationReportRepository;
import org.audit.app.service.FraudInvestigationReportService;
import org.audit.app.service.dto.FraudInvestigationReportDTO;
import org.audit.app.service.mapper.FraudInvestigationReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link FraudInvestigationReport}.
 */
@Service
public class FraudInvestigationReportServiceImpl implements FraudInvestigationReportService {

    private final Logger log = LoggerFactory.getLogger(FraudInvestigationReportServiceImpl.class);

    private final FraudInvestigationReportRepository fraudInvestigationReportRepository;

    private final FraudInvestigationReportMapper fraudInvestigationReportMapper;

    public FraudInvestigationReportServiceImpl(
        FraudInvestigationReportRepository fraudInvestigationReportRepository,
        FraudInvestigationReportMapper fraudInvestigationReportMapper
    ) {
        this.fraudInvestigationReportRepository = fraudInvestigationReportRepository;
        this.fraudInvestigationReportMapper = fraudInvestigationReportMapper;
    }

    @Override
    public FraudInvestigationReportDTO save(FraudInvestigationReportDTO fraudInvestigationReportDTO) {
        log.debug("Request to save FraudInvestigationReport : {}", fraudInvestigationReportDTO);
        FraudInvestigationReport fraudInvestigationReport = fraudInvestigationReportMapper.toEntity(fraudInvestigationReportDTO);
        fraudInvestigationReport = fraudInvestigationReportRepository.save(fraudInvestigationReport);
        return fraudInvestigationReportMapper.toDto(fraudInvestigationReport);
    }

    @Override
    public FraudInvestigationReportDTO update(FraudInvestigationReportDTO fraudInvestigationReportDTO) {
        log.debug("Request to update FraudInvestigationReport : {}", fraudInvestigationReportDTO);
        FraudInvestigationReport fraudInvestigationReport = fraudInvestigationReportMapper.toEntity(fraudInvestigationReportDTO);
        fraudInvestigationReport = fraudInvestigationReportRepository.save(fraudInvestigationReport);
        return fraudInvestigationReportMapper.toDto(fraudInvestigationReport);
    }

    @Override
    public Optional<FraudInvestigationReportDTO> partialUpdate(FraudInvestigationReportDTO fraudInvestigationReportDTO) {
        log.debug("Request to partially update FraudInvestigationReport : {}", fraudInvestigationReportDTO);

        return fraudInvestigationReportRepository
            .findById(fraudInvestigationReportDTO.getId())
            .map(existingFraudInvestigationReport -> {
                fraudInvestigationReportMapper.partialUpdate(existingFraudInvestigationReport, fraudInvestigationReportDTO);

                return existingFraudInvestigationReport;
            })
            .map(fraudInvestigationReportRepository::save)
            .map(fraudInvestigationReportMapper::toDto);
    }

    @Override
    public Page<FraudInvestigationReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FraudInvestigationReports");
        return fraudInvestigationReportRepository.findAll(pageable).map(fraudInvestigationReportMapper::toDto);
    }

    public Page<FraudInvestigationReportDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fraudInvestigationReportRepository.findAllWithEagerRelationships(pageable).map(fraudInvestigationReportMapper::toDto);
    }

    @Override
    public Optional<FraudInvestigationReportDTO> findOne(String id) {
        log.debug("Request to get FraudInvestigationReport : {}", id);
        return fraudInvestigationReportRepository.findOneWithEagerRelationships(id).map(fraudInvestigationReportMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FraudInvestigationReport : {}", id);
        fraudInvestigationReportRepository.deleteById(id);
    }
}
