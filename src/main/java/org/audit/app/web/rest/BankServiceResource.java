package org.audit.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.audit.app.repository.BankServiceRepository;
import org.audit.app.service.BankServiceService;
import org.audit.app.service.dto.BankServiceDTO;
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
 * REST controller for managing {@link org.audit.app.domain.BankService}.
 */
@RestController
@RequestMapping("/api")
public class BankServiceResource {

    private final Logger log = LoggerFactory.getLogger(BankServiceResource.class);

    private static final String ENTITY_NAME = "bankService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankServiceService bankServiceService;

    private final BankServiceRepository bankServiceRepository;

    public BankServiceResource(BankServiceService bankServiceService, BankServiceRepository bankServiceRepository) {
        this.bankServiceService = bankServiceService;
        this.bankServiceRepository = bankServiceRepository;
    }

    /**
     * {@code POST  /bank-services} : Create a new bankService.
     *
     * @param bankServiceDTO the bankServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankServiceDTO, or with status {@code 400 (Bad Request)} if the bankService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-services")
    public ResponseEntity<BankServiceDTO> createBankService(@Valid @RequestBody BankServiceDTO bankServiceDTO) throws URISyntaxException {
        log.debug("REST request to save BankService : {}", bankServiceDTO);
        if (bankServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankServiceDTO result = bankServiceService.save(bankServiceDTO);
        return ResponseEntity
            .created(new URI("/api/bank-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-services/:id} : Updates an existing bankService.
     *
     * @param id the id of the bankServiceDTO to save.
     * @param bankServiceDTO the bankServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankServiceDTO,
     * or with status {@code 400 (Bad Request)} if the bankServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-services/{id}")
    public ResponseEntity<BankServiceDTO> updateBankService(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody BankServiceDTO bankServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankService : {}, {}", id, bankServiceDTO);
        if (bankServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankServiceDTO result = bankServiceService.update(bankServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankServiceDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-services/:id} : Partial updates given fields of an existing bankService, field will ignore if it is null
     *
     * @param id the id of the bankServiceDTO to save.
     * @param bankServiceDTO the bankServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankServiceDTO,
     * or with status {@code 400 (Bad Request)} if the bankServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankServiceDTO> partialUpdateBankService(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody BankServiceDTO bankServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankService partially : {}, {}", id, bankServiceDTO);
        if (bankServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankServiceDTO> result = bankServiceService.partialUpdate(bankServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankServiceDTO.getId())
        );
    }

    /**
     * {@code GET  /bank-services} : get all the bankServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankServices in body.
     */
    @GetMapping("/bank-services")
    public ResponseEntity<List<BankServiceDTO>> getAllBankServices(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankServices");
        Page<BankServiceDTO> page = bankServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-services/:id} : get the "id" bankService.
     *
     * @param id the id of the bankServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-services/{id}")
    public ResponseEntity<BankServiceDTO> getBankService(@PathVariable String id) {
        log.debug("REST request to get BankService : {}", id);
        Optional<BankServiceDTO> bankServiceDTO = bankServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankServiceDTO);
    }

    /**
     * {@code DELETE  /bank-services/:id} : delete the "id" bankService.
     *
     * @param id the id of the bankServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-services/{id}")
    public ResponseEntity<Void> deleteBankService(@PathVariable String id) {
        log.debug("REST request to delete BankService : {}", id);
        bankServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
