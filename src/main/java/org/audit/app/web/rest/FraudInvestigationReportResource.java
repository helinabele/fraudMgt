package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.FraudInvestigationReportRepository;
import org.audit.app.service.FraudInvestigationReportService;
import org.audit.app.service.dto.FraudInvestigationReportDTO;
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
 * REST controller for managing {@link org.audit.app.domain.FraudInvestigationReport}.
 */
@RestController
@RequestMapping("/api")
public class FraudInvestigationReportResource {

    private final Logger log = LoggerFactory.getLogger(FraudInvestigationReportResource.class);

    private static final String ENTITY_NAME = "fraudInvestigationReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraudInvestigationReportService fraudInvestigationReportService;

    private final FraudInvestigationReportRepository fraudInvestigationReportRepository;

    public FraudInvestigationReportResource(
        FraudInvestigationReportService fraudInvestigationReportService,
        FraudInvestigationReportRepository fraudInvestigationReportRepository
    ) {
        this.fraudInvestigationReportService = fraudInvestigationReportService;
        this.fraudInvestigationReportRepository = fraudInvestigationReportRepository;
    }

    /**
     * {@code POST  /fraud-investigation-reports} : Create a new fraudInvestigationReport.
     *
     * @param fraudInvestigationReportDTO the fraudInvestigationReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraudInvestigationReportDTO, or with status {@code 400 (Bad Request)} if the fraudInvestigationReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fraud-investigation-reports")
    public ResponseEntity<FraudInvestigationReportDTO> createFraudInvestigationReport(
        @Valid @RequestBody FraudInvestigationReportDTO fraudInvestigationReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FraudInvestigationReport : {}", fraudInvestigationReportDTO);
        if (fraudInvestigationReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new fraudInvestigationReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FraudInvestigationReportDTO result = fraudInvestigationReportService.save(fraudInvestigationReportDTO);
        return ResponseEntity
            .created(new URI("/api/fraud-investigation-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /fraud-investigation-reports/:id} : Updates an existing fraudInvestigationReport.
     *
     * @param id the id of the fraudInvestigationReportDTO to save.
     * @param fraudInvestigationReportDTO the fraudInvestigationReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudInvestigationReportDTO,
     * or with status {@code 400 (Bad Request)} if the fraudInvestigationReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraudInvestigationReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fraud-investigation-reports/{id}")
    public ResponseEntity<FraudInvestigationReportDTO> updateFraudInvestigationReport(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FraudInvestigationReportDTO fraudInvestigationReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FraudInvestigationReport : {}, {}", id, fraudInvestigationReportDTO);
        if (fraudInvestigationReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudInvestigationReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudInvestigationReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FraudInvestigationReportDTO result = fraudInvestigationReportService.update(fraudInvestigationReportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraudInvestigationReportDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /fraud-investigation-reports/:id} : Partial updates given fields of an existing fraudInvestigationReport, field will ignore if it is null
     *
     * @param id the id of the fraudInvestigationReportDTO to save.
     * @param fraudInvestigationReportDTO the fraudInvestigationReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudInvestigationReportDTO,
     * or with status {@code 400 (Bad Request)} if the fraudInvestigationReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fraudInvestigationReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fraudInvestigationReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fraud-investigation-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FraudInvestigationReportDTO> partialUpdateFraudInvestigationReport(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FraudInvestigationReportDTO fraudInvestigationReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FraudInvestigationReport partially : {}, {}", id, fraudInvestigationReportDTO);
        if (fraudInvestigationReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudInvestigationReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudInvestigationReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FraudInvestigationReportDTO> result = fraudInvestigationReportService.partialUpdate(fraudInvestigationReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraudInvestigationReportDTO.getId())
        );
    }

    /**
     * {@code GET  /fraud-investigation-reports} : get all the fraudInvestigationReports.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraudInvestigationReports in body.
     */
    @GetMapping("/fraud-investigation-reports")
    public ResponseEntity<List<FraudInvestigationReportDTO>> getAllFraudInvestigationReports(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of FraudInvestigationReports");
        Page<FraudInvestigationReportDTO> page;
        if (eagerload) {
            page = fraudInvestigationReportService.findAllWithEagerRelationships(pageable);
        } else {
            page = fraudInvestigationReportService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fraud-investigation-reports/:id} : get the "id" fraudInvestigationReport.
     *
     * @param id the id of the fraudInvestigationReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraudInvestigationReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fraud-investigation-reports/{id}")
    public ResponseEntity<FraudInvestigationReportDTO> getFraudInvestigationReport(@PathVariable String id) {
        log.debug("REST request to get FraudInvestigationReport : {}", id);
        Optional<FraudInvestigationReportDTO> fraudInvestigationReportDTO = fraudInvestigationReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fraudInvestigationReportDTO);
    }

    /**
     * {@code DELETE  /fraud-investigation-reports/:id} : delete the "id" fraudInvestigationReport.
     *
     * @param id the id of the fraudInvestigationReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fraud-investigation-reports/{id}")
    public ResponseEntity<Void> deleteFraudInvestigationReport(@PathVariable String id) {
        log.debug("REST request to delete FraudInvestigationReport : {}", id);
        fraudInvestigationReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
