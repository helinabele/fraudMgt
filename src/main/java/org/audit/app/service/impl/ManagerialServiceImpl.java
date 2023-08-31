package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.Managerial;
import org.audit.app.repository.ManagerialRepository;
import org.audit.app.service.ManagerialService;
import org.audit.app.service.dto.ManagerialDTO;
import org.audit.app.service.mapper.ManagerialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Managerial}.
 */
@Service
public class ManagerialServiceImpl implements ManagerialService {

    private final Logger log = LoggerFactory.getLogger(ManagerialServiceImpl.class);

    private final ManagerialRepository managerialRepository;

    private final ManagerialMapper managerialMapper;

    public ManagerialServiceImpl(ManagerialRepository managerialRepository, ManagerialMapper managerialMapper) {
        this.managerialRepository = managerialRepository;
        this.managerialMapper = managerialMapper;
    }

    @Override
    public ManagerialDTO save(ManagerialDTO managerialDTO) {
        log.debug("Request to save Managerial : {}", managerialDTO);
        Managerial managerial = managerialMapper.toEntity(managerialDTO);
        managerial = managerialRepository.save(managerial);
        return managerialMapper.toDto(managerial);
    }

    @Override
    public ManagerialDTO update(ManagerialDTO managerialDTO) {
        log.debug("Request to update Managerial : {}", managerialDTO);
        Managerial managerial = managerialMapper.toEntity(managerialDTO);
        managerial = managerialRepository.save(managerial);
        return managerialMapper.toDto(managerial);
    }

    @Override
    public Optional<ManagerialDTO> partialUpdate(ManagerialDTO managerialDTO) {
        log.debug("Request to partially update Managerial : {}", managerialDTO);

        return managerialRepository
            .findById(managerialDTO.getId())
            .map(existingManagerial -> {
                managerialMapper.partialUpdate(existingManagerial, managerialDTO);

                return existingManagerial;
            })
            .map(managerialRepository::save)
            .map(managerialMapper::toDto);
    }

    @Override
    public Page<ManagerialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Managerials");
        return managerialRepository.findAll(pageable).map(managerialMapper::toDto);
    }

    public Page<ManagerialDTO> findAllWithEagerRelationships(Pageable pageable) {
        return managerialRepository.findAllWithEagerRelationships(pageable).map(managerialMapper::toDto);
    }

    @Override
    public Optional<ManagerialDTO> findOne(String id) {
        log.debug("Request to get Managerial : {}", id);
        return managerialRepository.findOneWithEagerRelationships(id).map(managerialMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Managerial : {}", id);
        managerialRepository.deleteById(id);
    }
}
