package com.capsule.service;

import com.capsule.domain.Capsule;
import com.capsule.repository.CapsuleRepository;
import com.capsule.service.dto.CapsuleDTO;
import com.capsule.service.mapper.CapsuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Capsule.
 */
@Service
@Transactional
public class CapsuleService {

    private final Logger log = LoggerFactory.getLogger(CapsuleService.class);

    private final CapsuleRepository capsuleRepository;

    private final CapsuleMapper capsuleMapper;

    public CapsuleService(CapsuleRepository capsuleRepository, CapsuleMapper capsuleMapper) {
        this.capsuleRepository = capsuleRepository;
        this.capsuleMapper = capsuleMapper;
    }

    /**
     * Save a capsule.
     *
     * @param capsuleDTO the entity to save
     * @return the persisted entity
     */
    public CapsuleDTO save(CapsuleDTO capsuleDTO) {
        log.debug("Request to save Capsule : {}", capsuleDTO);
        Capsule capsule = capsuleMapper.toEntity(capsuleDTO);
        capsule = capsuleRepository.save(capsule);
        return capsuleMapper.toDto(capsule);
    }

    /**
     * Get all the capsules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CapsuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Capsules");
        return capsuleRepository.findAll(pageable)
            .map(capsuleMapper::toDto);
    }



    /**
     *  get all the capsules where Examination is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CapsuleDTO> findAllWhereExaminationIsNull() {
        log.debug("Request to get all capsules where Examination is null");
        return StreamSupport
            .stream(capsuleRepository.findAll().spliterator(), false)
            .filter(capsule -> capsule.getExamination() == null)
            .map(capsuleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one capsule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CapsuleDTO> findOne(Long id) {
        log.debug("Request to get Capsule : {}", id);
        return capsuleRepository.findById(id)
            .map(capsuleMapper::toDto);
    }

    /**
     * Delete the capsule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Capsule : {}", id);
        capsuleRepository.deleteById(id);
    }
}
