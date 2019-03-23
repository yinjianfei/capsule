package com.capsule.service;

import com.capsule.domain.CapsuleImage;
import com.capsule.repository.CapsuleImageRepository;
import com.capsule.service.dto.CapsuleImageDTO;
import com.capsule.service.mapper.CapsuleImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CapsuleImage.
 */
@Service
@Transactional
public class CapsuleImageService {

    private final Logger log = LoggerFactory.getLogger(CapsuleImageService.class);

    private final CapsuleImageRepository capsuleImageRepository;

    private final CapsuleImageMapper capsuleImageMapper;

    public CapsuleImageService(CapsuleImageRepository capsuleImageRepository, CapsuleImageMapper capsuleImageMapper) {
        this.capsuleImageRepository = capsuleImageRepository;
        this.capsuleImageMapper = capsuleImageMapper;
    }

    /**
     * Save a capsuleImage.
     *
     * @param capsuleImageDTO the entity to save
     * @return the persisted entity
     */
    public CapsuleImageDTO save(CapsuleImageDTO capsuleImageDTO) {
        log.debug("Request to save CapsuleImage : {}", capsuleImageDTO);
        CapsuleImage capsuleImage = capsuleImageMapper.toEntity(capsuleImageDTO);
        capsuleImage = capsuleImageRepository.save(capsuleImage);
        return capsuleImageMapper.toDto(capsuleImage);
    }

    /**
     * Get all the capsuleImages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CapsuleImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CapsuleImages");
        return capsuleImageRepository.findAll(pageable)
            .map(capsuleImageMapper::toDto);
    }


    /**
     * Get one capsuleImage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CapsuleImageDTO> findOne(Long id) {
        log.debug("Request to get CapsuleImage : {}", id);
        return capsuleImageRepository.findById(id)
            .map(capsuleImageMapper::toDto);
    }

    /**
     * Delete the capsuleImage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CapsuleImage : {}", id);
        capsuleImageRepository.deleteById(id);
    }
}
