package com.capsule.service;

import com.capsule.domain.Examination;
import com.capsule.repository.ExaminationRepository;
import com.capsule.service.dto.ExaminationDTO;
import com.capsule.service.mapper.ExaminationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Examination.
 */
@Service
@Transactional
public class ExaminationService {

    private final Logger log = LoggerFactory.getLogger(ExaminationService.class);

    private final ExaminationRepository examinationRepository;

    private final ExaminationMapper examinationMapper;

    public ExaminationService(ExaminationRepository examinationRepository, ExaminationMapper examinationMapper) {
        this.examinationRepository = examinationRepository;
        this.examinationMapper = examinationMapper;
    }

    /**
     * Save a examination.
     *
     * @param examinationDTO the entity to save
     * @return the persisted entity
     */
    public ExaminationDTO save(ExaminationDTO examinationDTO) {
        log.debug("Request to save Examination : {}", examinationDTO);
        Examination examination = examinationMapper.toEntity(examinationDTO);
        examination = examinationRepository.save(examination);
        return examinationMapper.toDto(examination);
    }

    /**
     * Get all the examinations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExaminationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examinations");
        return examinationRepository.findAll(pageable)
            .map(examinationMapper::toDto);
    }


    /**
     * Get one examination by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExaminationDTO> findOne(Long id) {
        log.debug("Request to get Examination : {}", id);
        return examinationRepository.findById(id)
            .map(examinationMapper::toDto);
    }

    /**
     * Delete the examination by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Examination : {}", id);
        examinationRepository.deleteById(id);
    }
}
