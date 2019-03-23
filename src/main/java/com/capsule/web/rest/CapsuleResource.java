package com.capsule.web.rest;
import com.capsule.service.CapsuleService;
import com.capsule.web.rest.errors.BadRequestAlertException;
import com.capsule.web.rest.util.HeaderUtil;
import com.capsule.web.rest.util.PaginationUtil;
import com.capsule.service.dto.CapsuleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Capsule.
 */
@RestController
@RequestMapping("/api")
public class CapsuleResource {

    private final Logger log = LoggerFactory.getLogger(CapsuleResource.class);

    private static final String ENTITY_NAME = "capsule";

    private final CapsuleService capsuleService;

    public CapsuleResource(CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    /**
     * POST  /capsules : Create a new capsule.
     *
     * @param capsuleDTO the capsuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new capsuleDTO, or with status 400 (Bad Request) if the capsule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/capsules")
    public ResponseEntity<CapsuleDTO> createCapsule(@RequestBody CapsuleDTO capsuleDTO) throws URISyntaxException {
        log.debug("REST request to save Capsule : {}", capsuleDTO);
        if (capsuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new capsule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapsuleDTO result = capsuleService.save(capsuleDTO);
        return ResponseEntity.created(new URI("/api/capsules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /capsules : Updates an existing capsule.
     *
     * @param capsuleDTO the capsuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated capsuleDTO,
     * or with status 400 (Bad Request) if the capsuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the capsuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/capsules")
    public ResponseEntity<CapsuleDTO> updateCapsule(@RequestBody CapsuleDTO capsuleDTO) throws URISyntaxException {
        log.debug("REST request to update Capsule : {}", capsuleDTO);
        if (capsuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CapsuleDTO result = capsuleService.save(capsuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, capsuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /capsules : get all the capsules.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of capsules in body
     */
    @GetMapping("/capsules")
    public ResponseEntity<List<CapsuleDTO>> getAllCapsules(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("examination-is-null".equals(filter)) {
            log.debug("REST request to get all Capsules where examination is null");
            return new ResponseEntity<>(capsuleService.findAllWhereExaminationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Capsules");
        Page<CapsuleDTO> page = capsuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/capsules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /capsules/:id : get the "id" capsule.
     *
     * @param id the id of the capsuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the capsuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/capsules/{id}")
    public ResponseEntity<CapsuleDTO> getCapsule(@PathVariable Long id) {
        log.debug("REST request to get Capsule : {}", id);
        Optional<CapsuleDTO> capsuleDTO = capsuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(capsuleDTO);
    }

    /**
     * DELETE  /capsules/:id : delete the "id" capsule.
     *
     * @param id the id of the capsuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/capsules/{id}")
    public ResponseEntity<Void> deleteCapsule(@PathVariable Long id) {
        log.debug("REST request to delete Capsule : {}", id);
        capsuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
