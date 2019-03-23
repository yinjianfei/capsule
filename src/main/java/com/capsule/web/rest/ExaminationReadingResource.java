package com.capsule.web.rest;
import com.capsule.service.ExaminationReadingService;
import com.capsule.web.rest.errors.BadRequestAlertException;
import com.capsule.web.rest.util.HeaderUtil;
import com.capsule.web.rest.util.PaginationUtil;
import com.capsule.service.dto.ExaminationReadingDTO;
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
 * REST controller for managing ExaminationReading.
 */
@RestController
@RequestMapping("/api")
public class ExaminationReadingResource {

    private final Logger log = LoggerFactory.getLogger(ExaminationReadingResource.class);

    private static final String ENTITY_NAME = "examinationReading";

    private final ExaminationReadingService examinationReadingService;

    public ExaminationReadingResource(ExaminationReadingService examinationReadingService) {
        this.examinationReadingService = examinationReadingService;
    }

    /**
     * POST  /examination-readings : Create a new examinationReading.
     *
     * @param examinationReadingDTO the examinationReadingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examinationReadingDTO, or with status 400 (Bad Request) if the examinationReading has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/examination-readings")
    public ResponseEntity<ExaminationReadingDTO> createExaminationReading(@RequestBody ExaminationReadingDTO examinationReadingDTO) throws URISyntaxException {
        log.debug("REST request to save ExaminationReading : {}", examinationReadingDTO);
        if (examinationReadingDTO.getId() != null) {
            throw new BadRequestAlertException("A new examinationReading cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExaminationReadingDTO result = examinationReadingService.save(examinationReadingDTO);
        return ResponseEntity.created(new URI("/api/examination-readings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examination-readings : Updates an existing examinationReading.
     *
     * @param examinationReadingDTO the examinationReadingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examinationReadingDTO,
     * or with status 400 (Bad Request) if the examinationReadingDTO is not valid,
     * or with status 500 (Internal Server Error) if the examinationReadingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/examination-readings")
    public ResponseEntity<ExaminationReadingDTO> updateExaminationReading(@RequestBody ExaminationReadingDTO examinationReadingDTO) throws URISyntaxException {
        log.debug("REST request to update ExaminationReading : {}", examinationReadingDTO);
        if (examinationReadingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExaminationReadingDTO result = examinationReadingService.save(examinationReadingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examinationReadingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examination-readings : get all the examinationReadings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examinationReadings in body
     */
    @GetMapping("/examination-readings")
    public ResponseEntity<List<ExaminationReadingDTO>> getAllExaminationReadings(Pageable pageable) {
        log.debug("REST request to get a page of ExaminationReadings");
        Page<ExaminationReadingDTO> page = examinationReadingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/examination-readings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /examination-readings/:id : get the "id" examinationReading.
     *
     * @param id the id of the examinationReadingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examinationReadingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/examination-readings/{id}")
    public ResponseEntity<ExaminationReadingDTO> getExaminationReading(@PathVariable Long id) {
        log.debug("REST request to get ExaminationReading : {}", id);
        Optional<ExaminationReadingDTO> examinationReadingDTO = examinationReadingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examinationReadingDTO);
    }

    /**
     * DELETE  /examination-readings/:id : delete the "id" examinationReading.
     *
     * @param id the id of the examinationReadingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/examination-readings/{id}")
    public ResponseEntity<Void> deleteExaminationReading(@PathVariable Long id) {
        log.debug("REST request to delete ExaminationReading : {}", id);
        examinationReadingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
