package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.ExternalEmployeeRepository;
import org.audit.app.service.ExternalEmployeeService;
import org.audit.app.service.dto.ExternalEmployeeDTO;
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
 * REST controller for managing {@link org.audit.app.domain.ExternalEmployee}.
 */
@RestController
@RequestMapping("/api")
public class ExternalEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(ExternalEmployeeResource.class);

    private static final String ENTITY_NAME = "externalEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExternalEmployeeService externalEmployeeService;

    private final ExternalEmployeeRepository externalEmployeeRepository;

    public ExternalEmployeeResource(
        ExternalEmployeeService externalEmployeeService,
        ExternalEmployeeRepository externalEmployeeRepository
    ) {
        this.externalEmployeeService = externalEmployeeService;
        this.externalEmployeeRepository = externalEmployeeRepository;
    }

    /**
     * {@code POST  /external-employees} : Create a new externalEmployee.
     *
     * @param externalEmployeeDTO the externalEmployeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new externalEmployeeDTO, or with status {@code 400 (Bad Request)} if the externalEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/external-employees")
    public ResponseEntity<ExternalEmployeeDTO> createExternalEmployee(@Valid @RequestBody ExternalEmployeeDTO externalEmployeeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ExternalEmployee : {}", externalEmployeeDTO);
        if (externalEmployeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new externalEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalEmployeeDTO result = externalEmployeeService.save(externalEmployeeDTO);
        return ResponseEntity
            .created(new URI("/api/external-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /external-employees/:id} : Updates an existing externalEmployee.
     *
     * @param id the id of the externalEmployeeDTO to save.
     * @param externalEmployeeDTO the externalEmployeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalEmployeeDTO,
     * or with status {@code 400 (Bad Request)} if the externalEmployeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the externalEmployeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/external-employees/{id}")
    public ResponseEntity<ExternalEmployeeDTO> updateExternalEmployee(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ExternalEmployeeDTO externalEmployeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExternalEmployee : {}, {}", id, externalEmployeeDTO);
        if (externalEmployeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, externalEmployeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!externalEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExternalEmployeeDTO result = externalEmployeeService.update(externalEmployeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalEmployeeDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /external-employees/:id} : Partial updates given fields of an existing externalEmployee, field will ignore if it is null
     *
     * @param id the id of the externalEmployeeDTO to save.
     * @param externalEmployeeDTO the externalEmployeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalEmployeeDTO,
     * or with status {@code 400 (Bad Request)} if the externalEmployeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the externalEmployeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the externalEmployeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/external-employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExternalEmployeeDTO> partialUpdateExternalEmployee(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ExternalEmployeeDTO externalEmployeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExternalEmployee partially : {}, {}", id, externalEmployeeDTO);
        if (externalEmployeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, externalEmployeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!externalEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExternalEmployeeDTO> result = externalEmployeeService.partialUpdate(externalEmployeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalEmployeeDTO.getId())
        );
    }

    /**
     * {@code GET  /external-employees} : get all the externalEmployees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of externalEmployees in body.
     */
    @GetMapping("/external-employees")
    public ResponseEntity<List<ExternalEmployeeDTO>> getAllExternalEmployees(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ExternalEmployees");
        Page<ExternalEmployeeDTO> page = externalEmployeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /external-employees/:id} : get the "id" externalEmployee.
     *
     * @param id the id of the externalEmployeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externalEmployeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/external-employees/{id}")
    public ResponseEntity<ExternalEmployeeDTO> getExternalEmployee(@PathVariable String id) {
        log.debug("REST request to get ExternalEmployee : {}", id);
        Optional<ExternalEmployeeDTO> externalEmployeeDTO = externalEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(externalEmployeeDTO);
    }

    /**
     * {@code DELETE  /external-employees/:id} : delete the "id" externalEmployee.
     *
     * @param id the id of the externalEmployeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/external-employees/{id}")
    public ResponseEntity<Void> deleteExternalEmployee(@PathVariable String id) {
        log.debug("REST request to delete ExternalEmployee : {}", id);
        externalEmployeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
