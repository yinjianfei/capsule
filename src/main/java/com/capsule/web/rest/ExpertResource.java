package com.capsule.web.rest;
import com.capsule.service.ExpertService;
import com.capsule.web.rest.errors.BadRequestAlertException;
import com.capsule.web.rest.util.HeaderUtil;
import com.capsule.web.rest.util.PaginationUtil;
import com.capsule.service.dto.ExpertDTO;
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
 * REST controller for managing Expert.
 */
@RestController
@RequestMapping("/api")
public class ExpertResource {

    private final Logger log = LoggerFactory.getLogger(ExpertResource.class);

    private static final String ENTITY_NAME = "expert";

    private final ExpertService expertService;

    public ExpertResource(ExpertService expertService) {
        this.expertService = expertService;
    }

    /**
     * POST  /experts : Create a new expert.
     *
     * @param expertDTO the expertDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new expertDTO, or with status 400 (Bad Request) if the expert has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experts")
    public ResponseEntity<ExpertDTO> createExpert(@RequestBody ExpertDTO expertDTO) throws URISyntaxException {
        log.debug("REST request to save Expert : {}", expertDTO);
        if (expertDTO.getId() != null) {
            throw new BadRequestAlertException("A new expert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpertDTO result = expertService.save(expertDTO);
        return ResponseEntity.created(new URI("/api/experts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experts : Updates an existing expert.
     *
     * @param expertDTO the expertDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated expertDTO,
     * or with status 400 (Bad Request) if the expertDTO is not valid,
     * or with status 500 (Internal Server Error) if the expertDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experts")
    public ResponseEntity<ExpertDTO> updateExpert(@RequestBody ExpertDTO expertDTO) throws URISyntaxException {
        log.debug("REST request to update Expert : {}", expertDTO);
        if (expertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpertDTO result = expertService.save(expertDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expertDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experts : get all the experts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of experts in body
     */
    @GetMapping("/experts")
    public ResponseEntity<List<ExpertDTO>> getAllExperts(Pageable pageable) {
        log.debug("REST request to get a page of Experts");
        Page<ExpertDTO> page = expertService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /experts/:id : get the "id" expert.
     *
     * @param id the id of the expertDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the expertDTO, or with status 404 (Not Found)
     */
    @GetMapping("/experts/{id}")
    public ResponseEntity<ExpertDTO> getExpert(@PathVariable Long id) {
        log.debug("REST request to get Expert : {}", id);
        Optional<ExpertDTO> expertDTO = expertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expertDTO);
    }

    /**
     * DELETE  /experts/:id : delete the "id" expert.
     *
     * @param id the id of the expertDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experts/{id}")
    public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
        log.debug("REST request to delete Expert : {}", id);
        expertService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
