package com.capsule.web.rest;
import com.capsule.service.CapsuleImageService;
import com.capsule.web.rest.errors.BadRequestAlertException;
import com.capsule.web.rest.util.HeaderUtil;
import com.capsule.web.rest.util.PaginationUtil;
import com.capsule.service.dto.CapsuleImageDTO;
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

/**
 * REST controller for managing CapsuleImage.
 */
@RestController
@RequestMapping("/api")
public class CapsuleImageResource {

    private final Logger log = LoggerFactory.getLogger(CapsuleImageResource.class);

    private static final String ENTITY_NAME = "capsuleImage";

    private final CapsuleImageService capsuleImageService;

    public CapsuleImageResource(CapsuleImageService capsuleImageService) {
        this.capsuleImageService = capsuleImageService;
    }

    /**
     * POST  /capsule-images : Create a new capsuleImage.
     *
     * @param capsuleImageDTO the capsuleImageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new capsuleImageDTO, or with status 400 (Bad Request) if the capsuleImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/capsule-images")
    public ResponseEntity<CapsuleImageDTO> createCapsuleImage(@RequestBody CapsuleImageDTO capsuleImageDTO) throws URISyntaxException {
        log.debug("REST request to save CapsuleImage : {}", capsuleImageDTO);
        if (capsuleImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new capsuleImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapsuleImageDTO result = capsuleImageService.save(capsuleImageDTO);
        return ResponseEntity.created(new URI("/api/capsule-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /capsule-images : Updates an existing capsuleImage.
     *
     * @param capsuleImageDTO the capsuleImageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated capsuleImageDTO,
     * or with status 400 (Bad Request) if the capsuleImageDTO is not valid,
     * or with status 500 (Internal Server Error) if the capsuleImageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/capsule-images")
    public ResponseEntity<CapsuleImageDTO> updateCapsuleImage(@RequestBody CapsuleImageDTO capsuleImageDTO) throws URISyntaxException {
        log.debug("REST request to update CapsuleImage : {}", capsuleImageDTO);
        if (capsuleImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CapsuleImageDTO result = capsuleImageService.save(capsuleImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, capsuleImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /capsule-images : get all the capsuleImages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of capsuleImages in body
     */
    @GetMapping("/capsule-images")
    public ResponseEntity<List<CapsuleImageDTO>> getAllCapsuleImages(Pageable pageable) {
        log.debug("REST request to get a page of CapsuleImages");
        Page<CapsuleImageDTO> page = capsuleImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/capsule-images");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /capsule-images/:id : get the "id" capsuleImage.
     *
     * @param id the id of the capsuleImageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the capsuleImageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/capsule-images/{id}")
    public ResponseEntity<CapsuleImageDTO> getCapsuleImage(@PathVariable Long id) {
        log.debug("REST request to get CapsuleImage : {}", id);
        Optional<CapsuleImageDTO> capsuleImageDTO = capsuleImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(capsuleImageDTO);
    }

    /**
     * DELETE  /capsule-images/:id : delete the "id" capsuleImage.
     *
     * @param id the id of the capsuleImageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/capsule-images/{id}")
    public ResponseEntity<Void> deleteCapsuleImage(@PathVariable Long id) {
        log.debug("REST request to delete CapsuleImage : {}", id);
        capsuleImageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
