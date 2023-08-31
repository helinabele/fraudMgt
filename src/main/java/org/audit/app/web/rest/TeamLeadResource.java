package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.TeamLeadRepository;
import org.audit.app.service.TeamLeadService;
import org.audit.app.service.dto.TeamLeadDTO;
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
 * REST controller for managing {@link org.audit.app.domain.TeamLead}.
 */
@RestController
@RequestMapping("/api")
public class TeamLeadResource {

    private final Logger log = LoggerFactory.getLogger(TeamLeadResource.class);

    private static final String ENTITY_NAME = "teamLead";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamLeadService teamLeadService;

    private final TeamLeadRepository teamLeadRepository;

    public TeamLeadResource(TeamLeadService teamLeadService, TeamLeadRepository teamLeadRepository) {
        this.teamLeadService = teamLeadService;
        this.teamLeadRepository = teamLeadRepository;
    }

    /**
     * {@code POST  /team-leads} : Create a new teamLead.
     *
     * @param teamLeadDTO the teamLeadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamLeadDTO, or with status {@code 400 (Bad Request)} if the teamLead has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-leads")
    public ResponseEntity<TeamLeadDTO> createTeamLead(@Valid @RequestBody TeamLeadDTO teamLeadDTO) throws URISyntaxException {
        log.debug("REST request to save TeamLead : {}", teamLeadDTO);
        if (teamLeadDTO.getId() != null) {
            throw new BadRequestAlertException("A new teamLead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamLeadDTO result = teamLeadService.save(teamLeadDTO);
        return ResponseEntity
            .created(new URI("/api/team-leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /team-leads/:id} : Updates an existing teamLead.
     *
     * @param id the id of the teamLeadDTO to save.
     * @param teamLeadDTO the teamLeadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamLeadDTO,
     * or with status {@code 400 (Bad Request)} if the teamLeadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamLeadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-leads/{id}")
    public ResponseEntity<TeamLeadDTO> updateTeamLead(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody TeamLeadDTO teamLeadDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TeamLead : {}, {}", id, teamLeadDTO);
        if (teamLeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamLeadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamLeadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamLeadDTO result = teamLeadService.update(teamLeadDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamLeadDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-leads/:id} : Partial updates given fields of an existing teamLead, field will ignore if it is null
     *
     * @param id the id of the teamLeadDTO to save.
     * @param teamLeadDTO the teamLeadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamLeadDTO,
     * or with status {@code 400 (Bad Request)} if the teamLeadDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teamLeadDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamLeadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-leads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamLeadDTO> partialUpdateTeamLead(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody TeamLeadDTO teamLeadDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamLead partially : {}, {}", id, teamLeadDTO);
        if (teamLeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamLeadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamLeadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamLeadDTO> result = teamLeadService.partialUpdate(teamLeadDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamLeadDTO.getId())
        );
    }

    /**
     * {@code GET  /team-leads} : get all the teamLeads.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamLeads in body.
     */
    @GetMapping("/team-leads")
    public ResponseEntity<List<TeamLeadDTO>> getAllTeamLeads(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TeamLeads");
        Page<TeamLeadDTO> page;
        if (eagerload) {
            page = teamLeadService.findAllWithEagerRelationships(pageable);
        } else {
            page = teamLeadService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-leads/:id} : get the "id" teamLead.
     *
     * @param id the id of the teamLeadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamLeadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-leads/{id}")
    public ResponseEntity<TeamLeadDTO> getTeamLead(@PathVariable String id) {
        log.debug("REST request to get TeamLead : {}", id);
        Optional<TeamLeadDTO> teamLeadDTO = teamLeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamLeadDTO);
    }

    /**
     * {@code DELETE  /team-leads/:id} : delete the "id" teamLead.
     *
     * @param id the id of the teamLeadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-leads/{id}")
    public ResponseEntity<Void> deleteTeamLead(@PathVariable String id) {
        log.debug("REST request to delete TeamLead : {}", id);
        teamLeadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
