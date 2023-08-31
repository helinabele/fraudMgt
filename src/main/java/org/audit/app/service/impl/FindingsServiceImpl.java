package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.Findings;
import org.audit.app.repository.FindingsRepository;
import org.audit.app.service.FindingsService;
import org.audit.app.service.dto.FindingsDTO;
import org.audit.app.service.mapper.FindingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Findings}.
 */
@Service
public class FindingsServiceImpl implements FindingsService {

    private final Logger log = LoggerFactory.getLogger(FindingsServiceImpl.class);

    private final FindingsRepository findingsRepository;

    private final FindingsMapper findingsMapper;

    public FindingsServiceImpl(FindingsRepository findingsRepository, FindingsMapper findingsMapper) {
        this.findingsRepository = findingsRepository;
        this.findingsMapper = findingsMapper;
    }

    @Override
    public FindingsDTO save(FindingsDTO findingsDTO) {
        log.debug("Request to save Findings : {}", findingsDTO);
        Findings findings = findingsMapper.toEntity(findingsDTO);
        findings = findingsRepository.save(findings);
        return findingsMapper.toDto(findings);
    }

    @Override
    public FindingsDTO update(FindingsDTO findingsDTO) {
        log.debug("Request to update Findings : {}", findingsDTO);
        Findings findings = findingsMapper.toEntity(findingsDTO);
        findings = findingsRepository.save(findings);
        return findingsMapper.toDto(findings);
    }

    @Override
    public Optional<FindingsDTO> partialUpdate(FindingsDTO findingsDTO) {
        log.debug("Request to partially update Findings : {}", findingsDTO);

        return findingsRepository
            .findById(findingsDTO.getId())
            .map(existingFindings -> {
                findingsMapper.partialUpdate(existingFindings, findingsDTO);

                return existingFindings;
            })
            .map(findingsRepository::save)
            .map(findingsMapper::toDto);
    }

    @Override
    public Page<FindingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Findings");
        return findingsRepository.findAll(pageable).map(findingsMapper::toDto);
    }

    @Override
    public Optional<FindingsDTO> findOne(String id) {
        log.debug("Request to get Findings : {}", id);
        return findingsRepository.findById(id).map(findingsMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Findings : {}", id);
        findingsRepository.deleteById(id);
    }
}
