package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.FraudType;
import org.audit.app.repository.FraudTypeRepository;
import org.audit.app.service.FraudTypeService;
import org.audit.app.service.dto.FraudTypeDTO;
import org.audit.app.service.mapper.FraudTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link FraudType}.
 */
@Service
public class FraudTypeServiceImpl implements FraudTypeService {

    private final Logger log = LoggerFactory.getLogger(FraudTypeServiceImpl.class);

    private final FraudTypeRepository fraudTypeRepository;

    private final FraudTypeMapper fraudTypeMapper;

    public FraudTypeServiceImpl(FraudTypeRepository fraudTypeRepository, FraudTypeMapper fraudTypeMapper) {
        this.fraudTypeRepository = fraudTypeRepository;
        this.fraudTypeMapper = fraudTypeMapper;
    }

    @Override
    public FraudTypeDTO save(FraudTypeDTO fraudTypeDTO) {
        log.debug("Request to save FraudType : {}", fraudTypeDTO);
        FraudType fraudType = fraudTypeMapper.toEntity(fraudTypeDTO);
        fraudType = fraudTypeRepository.save(fraudType);
        return fraudTypeMapper.toDto(fraudType);
    }

    @Override
    public FraudTypeDTO update(FraudTypeDTO fraudTypeDTO) {
        log.debug("Request to update FraudType : {}", fraudTypeDTO);
        FraudType fraudType = fraudTypeMapper.toEntity(fraudTypeDTO);
        fraudType = fraudTypeRepository.save(fraudType);
        return fraudTypeMapper.toDto(fraudType);
    }

    @Override
    public Optional<FraudTypeDTO> partialUpdate(FraudTypeDTO fraudTypeDTO) {
        log.debug("Request to partially update FraudType : {}", fraudTypeDTO);

        return fraudTypeRepository
            .findById(fraudTypeDTO.getId())
            .map(existingFraudType -> {
                fraudTypeMapper.partialUpdate(existingFraudType, fraudTypeDTO);

                return existingFraudType;
            })
            .map(fraudTypeRepository::save)
            .map(fraudTypeMapper::toDto);
    }

    @Override
    public Page<FraudTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FraudTypes");
        return fraudTypeRepository.findAll(pageable).map(fraudTypeMapper::toDto);
    }

    @Override
    public Optional<FraudTypeDTO> findOne(String id) {
        log.debug("Request to get FraudType : {}", id);
        return fraudTypeRepository.findById(id).map(fraudTypeMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FraudType : {}", id);
        fraudTypeRepository.deleteById(id);
    }
}
