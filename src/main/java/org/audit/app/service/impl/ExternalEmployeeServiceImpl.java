package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.ExternalEmployee;
import org.audit.app.repository.ExternalEmployeeRepository;
import org.audit.app.service.ExternalEmployeeService;
import org.audit.app.service.dto.ExternalEmployeeDTO;
import org.audit.app.service.mapper.ExternalEmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ExternalEmployee}.
 */
@Service
public class ExternalEmployeeServiceImpl implements ExternalEmployeeService {

    private final Logger log = LoggerFactory.getLogger(ExternalEmployeeServiceImpl.class);

    private final ExternalEmployeeRepository externalEmployeeRepository;

    private final ExternalEmployeeMapper externalEmployeeMapper;

    public ExternalEmployeeServiceImpl(
        ExternalEmployeeRepository externalEmployeeRepository,
        ExternalEmployeeMapper externalEmployeeMapper
    ) {
        this.externalEmployeeRepository = externalEmployeeRepository;
        this.externalEmployeeMapper = externalEmployeeMapper;
    }

    @Override
    public ExternalEmployeeDTO save(ExternalEmployeeDTO externalEmployeeDTO) {
        log.debug("Request to save ExternalEmployee : {}", externalEmployeeDTO);
        ExternalEmployee externalEmployee = externalEmployeeMapper.toEntity(externalEmployeeDTO);
        externalEmployee = externalEmployeeRepository.save(externalEmployee);
        return externalEmployeeMapper.toDto(externalEmployee);
    }

    @Override
    public ExternalEmployeeDTO update(ExternalEmployeeDTO externalEmployeeDTO) {
        log.debug("Request to update ExternalEmployee : {}", externalEmployeeDTO);
        ExternalEmployee externalEmployee = externalEmployeeMapper.toEntity(externalEmployeeDTO);
        externalEmployee = externalEmployeeRepository.save(externalEmployee);
        return externalEmployeeMapper.toDto(externalEmployee);
    }

    @Override
    public Optional<ExternalEmployeeDTO> partialUpdate(ExternalEmployeeDTO externalEmployeeDTO) {
        log.debug("Request to partially update ExternalEmployee : {}", externalEmployeeDTO);

        return externalEmployeeRepository
            .findById(externalEmployeeDTO.getId())
            .map(existingExternalEmployee -> {
                externalEmployeeMapper.partialUpdate(existingExternalEmployee, externalEmployeeDTO);

                return existingExternalEmployee;
            })
            .map(externalEmployeeRepository::save)
            .map(externalEmployeeMapper::toDto);
    }

    @Override
    public Page<ExternalEmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExternalEmployees");
        return externalEmployeeRepository.findAll(pageable).map(externalEmployeeMapper::toDto);
    }

    @Override
    public Optional<ExternalEmployeeDTO> findOne(String id) {
        log.debug("Request to get ExternalEmployee : {}", id);
        return externalEmployeeRepository.findById(id).map(externalEmployeeMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ExternalEmployee : {}", id);
        externalEmployeeRepository.deleteById(id);
    }
}
