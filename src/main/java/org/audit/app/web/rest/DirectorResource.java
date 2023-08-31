package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.DirectorRepository;
import org.audit.app.service.DirectorService;
import org.audit.app.service.dto.DirectorDTO;
import org.audit.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.audit.app.domain.Director}.
 */
@RestController
@RequestMapping("/api")
public class DirectorResource {

    private final Logger log = LoggerFactory.getLogger(DirectorResource.class);

    private static final String ENTITY_NAME = "director";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DirectorService directorService;

    private final DirectorRepository directorRepository;

    public DirectorResource(DirectorService directorService, DirectorRepository directorRepository) {
        this.directorService = directorService;
        this.directorRepository = directorRepository;
    }

    /**
     * {@code POST  /directors} : Create a new director.
     *
     * @param directorDTO the directorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new directorDTO, or with status {@code 400 (Bad Request)} if the director has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/directors")
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorDTO directorDTO) throws URISyntaxException {
        log.debug("REST request to save Director : {}", directorDTO);
        if (directorDTO.getId() != null) {
            throw new BadRequestAlertException("A new director cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DirectorDTO result = directorService.save(directorDTO);
        return ResponseEntity
            .created(new URI("/api/directors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /directors/:id} : Updates an existing director.
     *
     * @param id the id of the directorDTO to save.
     * @param directorDTO the directorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated directorDTO,
     * or with status {@code 400 (Bad Request)} if the directorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the directorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/directors/{id}")
    public ResponseEntity<DirectorDTO> updateDirector(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DirectorDTO directorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Director : {}, {}", id, directorDTO);
        if (directorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, directorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!directorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DirectorDTO result = directorService.update(directorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, directorDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /directors/:id} : Partial updates given fields of an existing director, field will ignore if it is null
     *
     * @param id the id of the directorDTO to save.
     * @param directorDTO the directorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated directorDTO,
     * or with status {@code 400 (Bad Request)} if the directorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the directorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the directorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/directors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DirectorDTO> partialUpdateDirector(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DirectorDTO directorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Director partially : {}, {}", id, directorDTO);
        if (directorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, directorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!directorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DirectorDTO> result = directorService.partialUpdate(directorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, directorDTO.getId())
        );
    }

    /**
     * {@code GET  /directors} : get all the directors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of directors in body.
     */
    @GetMapping("/directors")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Directors");
        Page<DirectorDTO> page;
        if (eagerload) {
            page = directorService.findAllWithEagerRelationships(pageable);
        } else {
            page = directorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /directors/:id} : get the "id" director.
     *
     * @param id the id of the directorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the directorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/directors/{id}")
    public ResponseEntity<DirectorDTO> getDirector(@PathVariable String id) {
        log.debug("REST request to get Director : {}", id);
        Optional<DirectorDTO> directorDTO = directorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(directorDTO);
    }

    /**
     * {@code DELETE  /directors/:id} : delete the "id" director.
     *
     * @param id the id of the directorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/directors/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable String id) {
        log.debug("REST request to delete Director : {}", id);
        directorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
