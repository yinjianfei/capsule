package com.capsule.web.rest;
import com.capsule.service.DoctorService;
import com.capsule.web.rest.errors.BadRequestAlertException;
import com.capsule.web.rest.util.HeaderUtil;
import com.capsule.web.rest.util.PaginationUtil;
import com.capsule.service.dto.DoctorDTO;
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
 * REST controller for managing Doctor.
 */
@RestController
@RequestMapping("/api")
public class DoctorResource {

    private final Logger log = LoggerFactory.getLogger(DoctorResource.class);

    private static final String ENTITY_NAME = "doctor";

    private final DoctorService doctorService;

    public DoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * POST  /doctors : Create a new doctor.
     *
     * @param doctorDTO the doctorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorDTO, or with status 400 (Bad Request) if the doctor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctors")
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) throws URISyntaxException {
        log.debug("REST request to save Doctor : {}", doctorDTO);
        if (doctorDTO.getId() != null) {
            throw new BadRequestAlertException("A new doctor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorDTO result = doctorService.save(doctorDTO);
        return ResponseEntity.created(new URI("/api/doctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctors : Updates an existing doctor.
     *
     * @param doctorDTO the doctorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorDTO,
     * or with status 400 (Bad Request) if the doctorDTO is not valid,
     * or with status 500 (Internal Server Error) if the doctorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctors")
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) throws URISyntaxException {
        log.debug("REST request to update Doctor : {}", doctorDTO);
        if (doctorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoctorDTO result = doctorService.save(doctorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctors : get all the doctors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doctors in body
     */
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors(Pageable pageable) {
        log.debug("REST request to get a page of Doctors");
        Page<DoctorDTO> page = doctorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /doctors/:id : get the "id" doctor.
     *
     * @param id the id of the doctorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id) {
        log.debug("REST request to get Doctor : {}", id);
        Optional<DoctorDTO> doctorDTO = doctorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorDTO);
    }

    /**
     * DELETE  /doctors/:id : delete the "id" doctor.
     *
     * @param id the id of the doctorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        log.debug("REST request to delete Doctor : {}", id);
        doctorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
