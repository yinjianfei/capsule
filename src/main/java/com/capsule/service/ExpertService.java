package com.capsule.service;

import com.capsule.domain.Expert;
import com.capsule.repository.ExpertRepository;
import com.capsule.service.dto.ExpertDTO;
import com.capsule.service.mapper.ExpertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Expert.
 */
@Service
@Transactional
public class ExpertService {

    private final Logger log = LoggerFactory.getLogger(ExpertService.class);

    private final ExpertRepository expertRepository;

    private final ExpertMapper expertMapper;

    public ExpertService(ExpertRepository expertRepository, ExpertMapper expertMapper) {
        this.expertRepository = expertRepository;
        this.expertMapper = expertMapper;
    }

    /**
     * Save a expert.
     *
     * @param expertDTO the entity to save
     * @return the persisted entity
     */
    public ExpertDTO save(ExpertDTO expertDTO) {
        log.debug("Request to save Expert : {}", expertDTO);
        Expert expert = expertMapper.toEntity(expertDTO);
        expert = expertRepository.save(expert);
        return expertMapper.toDto(expert);
    }

    /**
     * Get all the experts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExpertDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Experts");
        return expertRepository.findAll(pageable)
            .map(expertMapper::toDto);
    }


    /**
     * Get one expert by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExpertDTO> findOne(Long id) {
        log.debug("Request to get Expert : {}", id);
        return expertRepository.findById(id)
            .map(expertMapper::toDto);
    }

    /**
     * Delete the expert by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Expert : {}", id);
        expertRepository.deleteById(id);
    }
}
