package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.FraudKnowledgeManagementRepository;
import org.audit.app.service.FraudKnowledgeManagementService;
import org.audit.app.service.dto.FraudKnowledgeManagementDTO;
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
 * REST controller for managing {@link org.audit.app.domain.FraudKnowledgeManagement}.
 */
@RestController
@RequestMapping("/api")
public class FraudKnowledgeManagementResource {

    private final Logger log = LoggerFactory.getLogger(FraudKnowledgeManagementResource.class);

    private static final String ENTITY_NAME = "fraudKnowledgeManagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraudKnowledgeManagementService fraudKnowledgeManagementService;

    private final FraudKnowledgeManagementRepository fraudKnowledgeManagementRepository;

    public FraudKnowledgeManagementResource(
        FraudKnowledgeManagementService fraudKnowledgeManagementService,
        FraudKnowledgeManagementRepository fraudKnowledgeManagementRepository
    ) {
        this.fraudKnowledgeManagementService = fraudKnowledgeManagementService;
        this.fraudKnowledgeManagementRepository = fraudKnowledgeManagementRepository;
    }

    /**
     * {@code POST  /fraud-knowledge-managements} : Create a new fraudKnowledgeManagement.
     *
     * @param fraudKnowledgeManagementDTO the fraudKnowledgeManagementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraudKnowledgeManagementDTO, or with status {@code 400 (Bad Request)} if the fraudKnowledgeManagement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fraud-knowledge-managements")
    public ResponseEntity<FraudKnowledgeManagementDTO> createFraudKnowledgeManagement(
        @Valid @RequestBody FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FraudKnowledgeManagement : {}", fraudKnowledgeManagementDTO);
        if (fraudKnowledgeManagementDTO.getId() != null) {
            throw new BadRequestAlertException("A new fraudKnowledgeManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FraudKnowledgeManagementDTO result = fraudKnowledgeManagementService.save(fraudKnowledgeManagementDTO);
        return ResponseEntity
            .created(new URI("/api/fraud-knowledge-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /fraud-knowledge-managements/:id} : Updates an existing fraudKnowledgeManagement.
     *
     * @param id the id of the fraudKnowledgeManagementDTO to save.
     * @param fraudKnowledgeManagementDTO the fraudKnowledgeManagementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudKnowledgeManagementDTO,
     * or with status {@code 400 (Bad Request)} if the fraudKnowledgeManagementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraudKnowledgeManagementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fraud-knowledge-managements/{id}")
    public ResponseEntity<FraudKnowledgeManagementDTO> updateFraudKnowledgeManagement(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FraudKnowledgeManagement : {}, {}", id, fraudKnowledgeManagementDTO);
        if (fraudKnowledgeManagementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudKnowledgeManagementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudKnowledgeManagementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FraudKnowledgeManagementDTO result = fraudKnowledgeManagementService.update(fraudKnowledgeManagementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraudKnowledgeManagementDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /fraud-knowledge-managements/:id} : Partial updates given fields of an existing fraudKnowledgeManagement, field will ignore if it is null
     *
     * @param id the id of the fraudKnowledgeManagementDTO to save.
     * @param fraudKnowledgeManagementDTO the fraudKnowledgeManagementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudKnowledgeManagementDTO,
     * or with status {@code 400 (Bad Request)} if the fraudKnowledgeManagementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fraudKnowledgeManagementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fraudKnowledgeManagementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fraud-knowledge-managements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FraudKnowledgeManagementDTO> partialUpdateFraudKnowledgeManagement(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FraudKnowledgeManagement partially : {}, {}", id, fraudKnowledgeManagementDTO);
        if (fraudKnowledgeManagementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudKnowledgeManagementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudKnowledgeManagementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FraudKnowledgeManagementDTO> result = fraudKnowledgeManagementService.partialUpdate(fraudKnowledgeManagementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraudKnowledgeManagementDTO.getId())
        );
    }

    /**
     * {@code GET  /fraud-knowledge-managements} : get all the fraudKnowledgeManagements.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraudKnowledgeManagements in body.
     */
    @GetMapping("/fraud-knowledge-managements")
    public ResponseEntity<List<FraudKnowledgeManagementDTO>> getAllFraudKnowledgeManagements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of FraudKnowledgeManagements");
        Page<FraudKnowledgeManagementDTO> page;
        if (eagerload) {
            page = fraudKnowledgeManagementService.findAllWithEagerRelationships(pageable);
        } else {
            page = fraudKnowledgeManagementService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fraud-knowledge-managements/:id} : get the "id" fraudKnowledgeManagement.
     *
     * @param id the id of the fraudKnowledgeManagementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraudKnowledgeManagementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fraud-knowledge-managements/{id}")
    public ResponseEntity<FraudKnowledgeManagementDTO> getFraudKnowledgeManagement(@PathVariable String id) {
        log.debug("REST request to get FraudKnowledgeManagement : {}", id);
        Optional<FraudKnowledgeManagementDTO> fraudKnowledgeManagementDTO = fraudKnowledgeManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fraudKnowledgeManagementDTO);
    }

    /**
     * {@code DELETE  /fraud-knowledge-managements/:id} : delete the "id" fraudKnowledgeManagement.
     *
     * @param id the id of the fraudKnowledgeManagementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fraud-knowledge-managements/{id}")
    public ResponseEntity<Void> deleteFraudKnowledgeManagement(@PathVariable String id) {
        log.debug("REST request to delete FraudKnowledgeManagement : {}", id);
        fraudKnowledgeManagementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
