package com.capsule.service;

import com.capsule.domain.ExaminationReading;
import com.capsule.repository.ExaminationReadingRepository;
import com.capsule.service.dto.ExaminationReadingDTO;
import com.capsule.service.mapper.ExaminationReadingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ExaminationReading.
 */
@Service
@Transactional
public class ExaminationReadingService {

    private final Logger log = LoggerFactory.getLogger(ExaminationReadingService.class);

    private final ExaminationReadingRepository examinationReadingRepository;

    private final ExaminationReadingMapper examinationReadingMapper;

    public ExaminationReadingService(ExaminationReadingRepository examinationReadingRepository, ExaminationReadingMapper examinationReadingMapper) {
        this.examinationReadingRepository = examinationReadingRepository;
        this.examinationReadingMapper = examinationReadingMapper;
    }

    /**
     * Save a examinationReading.
     *
     * @param examinationReadingDTO the entity to save
     * @return the persisted entity
     */
    public ExaminationReadingDTO save(ExaminationReadingDTO examinationReadingDTO) {
        log.debug("Request to save ExaminationReading : {}", examinationReadingDTO);
        ExaminationReading examinationReading = examinationReadingMapper.toEntity(examinationReadingDTO);
        examinationReading = examinationReadingRepository.save(examinationReading);
        return examinationReadingMapper.toDto(examinationReading);
    }

    /**
     * Get all the examinationReadings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExaminationReadingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExaminationReadings");
        return examinationReadingRepository.findAll(pageable)
            .map(examinationReadingMapper::toDto);
    }


    /**
     * Get one examinationReading by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExaminationReadingDTO> findOne(Long id) {
        log.debug("Request to get ExaminationReading : {}", id);
        return examinationReadingRepository.findById(id)
            .map(examinationReadingMapper::toDto);
    }

    /**
     * Delete the examinationReading by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExaminationReading : {}", id);
        examinationReadingRepository.deleteById(id);
    }
}
