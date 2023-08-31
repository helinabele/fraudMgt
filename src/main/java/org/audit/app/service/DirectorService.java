package org.audit.app.service;

import java.util.Optional;
import org.audit.app.service.dto.DirectorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.audit.app.domain.Director}.
 */
public interface DirectorService {
    /**
     * Save a director.
     *
     * @param directorDTO the entity to save.
     * @return the persisted entity.
     */
    DirectorDTO save(DirectorDTO directorDTO);

    /**
     * Updates a director.
     *
     * @param directorDTO the entity to update.
     * @return the persisted entity.
     */
    DirectorDTO update(DirectorDTO directorDTO);

    /**
     * Partially updates a director.
     *
     * @param directorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DirectorDTO> partialUpdate(DirectorDTO directorDTO);

    /**
     * Get all the directors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DirectorDTO> findAll(Pageable pageable);

    /**
     * Get all the directors with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DirectorDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" director.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DirectorDTO> findOne(String id);

    /**
     * Delete the "id" director.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
