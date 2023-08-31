package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.FraudKnowledgeManagement;
import org.audit.app.repository.FraudKnowledgeManagementRepository;
import org.audit.app.service.FraudKnowledgeManagementService;
import org.audit.app.service.dto.FraudKnowledgeManagementDTO;
import org.audit.app.service.mapper.FraudKnowledgeManagementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link FraudKnowledgeManagement}.
 */
@Service
public class FraudKnowledgeManagementServiceImpl implements FraudKnowledgeManagementService {

    private final Logger log = LoggerFactory.getLogger(FraudKnowledgeManagementServiceImpl.class);

    private final FraudKnowledgeManagementRepository fraudKnowledgeManagementRepository;

    private final FraudKnowledgeManagementMapper fraudKnowledgeManagementMapper;

    public FraudKnowledgeManagementServiceImpl(
        FraudKnowledgeManagementRepository fraudKnowledgeManagementRepository,
        FraudKnowledgeManagementMapper fraudKnowledgeManagementMapper
    ) {
        this.fraudKnowledgeManagementRepository = fraudKnowledgeManagementRepository;
        this.fraudKnowledgeManagementMapper = fraudKnowledgeManagementMapper;
    }

    @Override
    public FraudKnowledgeManagementDTO save(FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO) {
        log.debug("Request to save FraudKnowledgeManagement : {}", fraudKnowledgeManagementDTO);
        FraudKnowledgeManagement fraudKnowledgeManagement = fraudKnowledgeManagementMapper.toEntity(fraudKnowledgeManagementDTO);
        fraudKnowledgeManagement = fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);
        return fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);
    }

    @Override
    public FraudKnowledgeManagementDTO update(FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO) {
        log.debug("Request to update FraudKnowledgeManagement : {}", fraudKnowledgeManagementDTO);
        FraudKnowledgeManagement fraudKnowledgeManagement = fraudKnowledgeManagementMapper.toEntity(fraudKnowledgeManagementDTO);
        fraudKnowledgeManagement = fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);
        return fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);
    }

    @Override
    public Optional<FraudKnowledgeManagementDTO> partialUpdate(FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO) {
        log.debug("Request to partially update FraudKnowledgeManagement : {}", fraudKnowledgeManagementDTO);

        return fraudKnowledgeManagementRepository
            .findById(fraudKnowledgeManagementDTO.getId())
            .map(existingFraudKnowledgeManagement -> {
                fraudKnowledgeManagementMapper.partialUpdate(existingFraudKnowledgeManagement, fraudKnowledgeManagementDTO);

                return existingFraudKnowledgeManagement;
            })
            .map(fraudKnowledgeManagementRepository::save)
            .map(fraudKnowledgeManagementMapper::toDto);
    }

    @Override
    public Page<FraudKnowledgeManagementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FraudKnowledgeManagements");
        return fraudKnowledgeManagementRepository.findAll(pageable).map(fraudKnowledgeManagementMapper::toDto);
    }

    public Page<FraudKnowledgeManagementDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fraudKnowledgeManagementRepository.findAllWithEagerRelationships(pageable).map(fraudKnowledgeManagementMapper::toDto);
    }

    @Override
    public Optional<FraudKnowledgeManagementDTO> findOne(String id) {
        log.debug("Request to get FraudKnowledgeManagement : {}", id);
        return fraudKnowledgeManagementRepository.findOneWithEagerRelationships(id).map(fraudKnowledgeManagementMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FraudKnowledgeManagement : {}", id);
        fraudKnowledgeManagementRepository.deleteById(id);
    }
}
