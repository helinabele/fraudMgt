package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.InternalEmployeeRepository;
import org.audit.app.service.InternalEmployeeService;
import org.audit.app.service.dto.InternalEmployeeDTO;
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
 * REST controller for managing {@link org.audit.app.domain.InternalEmployee}.
 */
@RestController
@RequestMapping("/api")
public class InternalEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(InternalEmployeeResource.class);

    private static final String ENTITY_NAME = "internalEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternalEmployeeService internalEmployeeService;

    private final InternalEmployeeRepository internalEmployeeRepository;

    public InternalEmployeeResource(
        InternalEmployeeService internalEmployeeService,
        InternalEmployeeRepository internalEmployeeRepository
    ) {
        this.internalEmployeeService = internalEmployeeService;
        this.internalEmployeeRepository = internalEmployeeRepository;
    }

    /**
     * {@code POST  /internal-employees} : Create a new internalEmployee.
     *
     * @param internalEmployeeDTO the internalEmployeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new internalEmployeeDTO, or with status {@code 400 (Bad Request)} if the internalEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/internal-employees")
    public ResponseEntity<InternalEmployeeDTO> createInternalEmployee(@Valid @RequestBody InternalEmployeeDTO internalEmployeeDTO)
        throws URISyntaxException {
        log.debug("REST request to save InternalEmployee : {}", internalEmployeeDTO);
        if (internalEmployeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new internalEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternalEmployeeDTO result = internalEmployeeService.save(internalEmployeeDTO);
        return ResponseEntity
            .created(new URI("/api/internal-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /internal-employees/:id} : Updates an existing internalEmployee.
     *
     * @param id the id of the internalEmployeeDTO to save.
     * @param internalEmployeeDTO the internalEmployeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internalEmployeeDTO,
     * or with status {@code 400 (Bad Request)} if the internalEmployeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the internalEmployeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internal-employees/{id}")
    public ResponseEntity<InternalEmployeeDTO> updateInternalEmployee(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody InternalEmployeeDTO internalEmployeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InternalEmployee : {}, {}", id, internalEmployeeDTO);
        if (internalEmployeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, internalEmployeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!internalEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InternalEmployeeDTO result = internalEmployeeService.update(internalEmployeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internalEmployeeDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /internal-employees/:id} : Partial updates given fields of an existing internalEmployee, field will ignore if it is null
     *
     * @param id the id of the internalEmployeeDTO to save.
     * @param internalEmployeeDTO the internalEmployeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internalEmployeeDTO,
     * or with status {@code 400 (Bad Request)} if the internalEmployeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the internalEmployeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the internalEmployeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/internal-employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InternalEmployeeDTO> partialUpdateInternalEmployee(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody InternalEmployeeDTO internalEmployeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InternalEmployee partially : {}, {}", id, internalEmployeeDTO);
        if (internalEmployeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, internalEmployeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!internalEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InternalEmployeeDTO> result = internalEmployeeService.partialUpdate(internalEmployeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internalEmployeeDTO.getId())
        );
    }

    /**
     * {@code GET  /internal-employees} : get all the internalEmployees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internalEmployees in body.
     */
    @GetMapping("/internal-employees")
    public ResponseEntity<List<InternalEmployeeDTO>> getAllInternalEmployees(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of InternalEmployees");
        Page<InternalEmployeeDTO> page = internalEmployeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /internal-employees/:id} : get the "id" internalEmployee.
     *
     * @param id the id of the internalEmployeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the internalEmployeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internal-employees/{id}")
    public ResponseEntity<InternalEmployeeDTO> getInternalEmployee(@PathVariable String id) {
        log.debug("REST request to get InternalEmployee : {}", id);
        Optional<InternalEmployeeDTO> internalEmployeeDTO = internalEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internalEmployeeDTO);
    }

    /**
     * {@code DELETE  /internal-employees/:id} : delete the "id" internalEmployee.
     *
     * @param id the id of the internalEmployeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internal-employees/{id}")
    public ResponseEntity<Void> deleteInternalEmployee(@PathVariable String id) {
        log.debug("REST request to delete InternalEmployee : {}", id);
        internalEmployeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
