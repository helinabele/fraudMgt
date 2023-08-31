package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.Director;
import org.audit.app.repository.DirectorRepository;
import org.audit.app.service.DirectorService;
import org.audit.app.service.dto.DirectorDTO;
import org.audit.app.service.mapper.DirectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Director}.
 */
@Service
public class DirectorServiceImpl implements DirectorService {

    private final Logger log = LoggerFactory.getLogger(DirectorServiceImpl.class);

    private final DirectorRepository directorRepository;

    private final DirectorMapper directorMapper;

    public DirectorServiceImpl(DirectorRepository directorRepository, DirectorMapper directorMapper) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
    }

    @Override
    public DirectorDTO save(DirectorDTO directorDTO) {
        log.debug("Request to save Director : {}", directorDTO);
        Director director = directorMapper.toEntity(directorDTO);
        director = directorRepository.save(director);
        return directorMapper.toDto(director);
    }

    @Override
    public DirectorDTO update(DirectorDTO directorDTO) {
        log.debug("Request to update Director : {}", directorDTO);
        Director director = directorMapper.toEntity(directorDTO);
        director = directorRepository.save(director);
        return directorMapper.toDto(director);
    }

    @Override
    public Optional<DirectorDTO> partialUpdate(DirectorDTO directorDTO) {
        log.debug("Request to partially update Director : {}", directorDTO);

        return directorRepository
            .findById(directorDTO.getId())
            .map(existingDirector -> {
                directorMapper.partialUpdate(existingDirector, directorDTO);

                return existingDirector;
            })
            .map(directorRepository::save)
            .map(directorMapper::toDto);
    }

    @Override
    public Page<DirectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Directors");
        return directorRepository.findAll(pageable).map(directorMapper::toDto);
    }

    public Page<DirectorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return directorRepository.findAllWithEagerRelationships(pageable).map(directorMapper::toDto);
    }

    @Override
    public Optional<DirectorDTO> findOne(String id) {
        log.debug("Request to get Director : {}", id);
        return directorRepository.findOneWithEagerRelationships(id).map(directorMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Director : {}", id);
        directorRepository.deleteById(id);
    }
}
