package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.InternalEmployee;
import org.audit.app.repository.InternalEmployeeRepository;
import org.audit.app.service.InternalEmployeeService;
import org.audit.app.service.dto.InternalEmployeeDTO;
import org.audit.app.service.mapper.InternalEmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link InternalEmployee}.
 */
@Service
public class InternalEmployeeServiceImpl implements InternalEmployeeService {

    private final Logger log = LoggerFactory.getLogger(InternalEmployeeServiceImpl.class);

    private final InternalEmployeeRepository internalEmployeeRepository;

    private final InternalEmployeeMapper internalEmployeeMapper;

    public InternalEmployeeServiceImpl(
        InternalEmployeeRepository internalEmployeeRepository,
        InternalEmployeeMapper internalEmployeeMapper
    ) {
        this.internalEmployeeRepository = internalEmployeeRepository;
        this.internalEmployeeMapper = internalEmployeeMapper;
    }

    @Override
    public InternalEmployeeDTO save(InternalEmployeeDTO internalEmployeeDTO) {
        log.debug("Request to save InternalEmployee : {}", internalEmployeeDTO);
        InternalEmployee internalEmployee = internalEmployeeMapper.toEntity(internalEmployeeDTO);
        internalEmployee = internalEmployeeRepository.save(internalEmployee);
        return internalEmployeeMapper.toDto(internalEmployee);
    }

    @Override
    public InternalEmployeeDTO update(InternalEmployeeDTO internalEmployeeDTO) {
        log.debug("Request to update InternalEmployee : {}", internalEmployeeDTO);
        InternalEmployee internalEmployee = internalEmployeeMapper.toEntity(internalEmployeeDTO);
        internalEmployee = internalEmployeeRepository.save(internalEmployee);
        return internalEmployeeMapper.toDto(internalEmployee);
    }

    @Override
    public Optional<InternalEmployeeDTO> partialUpdate(InternalEmployeeDTO internalEmployeeDTO) {
        log.debug("Request to partially update InternalEmployee : {}", internalEmployeeDTO);

        return internalEmployeeRepository
            .findById(internalEmployeeDTO.getId())
            .map(existingInternalEmployee -> {
                internalEmployeeMapper.partialUpdate(existingInternalEmployee, internalEmployeeDTO);

                return existingInternalEmployee;
            })
            .map(internalEmployeeRepository::save)
            .map(internalEmployeeMapper::toDto);
    }

    @Override
    public Page<InternalEmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InternalEmployees");
        return internalEmployeeRepository.findAll(pageable).map(internalEmployeeMapper::toDto);
    }

    @Override
    public Optional<InternalEmployeeDTO> findOne(String id) {
        log.debug("Request to get InternalEmployee : {}", id);
        return internalEmployeeRepository.findById(id).map(internalEmployeeMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete InternalEmployee : {}", id);
        internalEmployeeRepository.deleteById(id);
    }
}
