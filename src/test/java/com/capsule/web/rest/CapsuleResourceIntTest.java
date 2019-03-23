package com.capsule.web.rest;

import com.capsule.CapsuleApp;

import com.capsule.domain.Capsule;
import com.capsule.repository.CapsuleRepository;
import com.capsule.service.CapsuleService;
import com.capsule.service.dto.CapsuleDTO;
import com.capsule.service.mapper.CapsuleMapper;
import com.capsule.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.capsule.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CapsuleResource REST controller.
 *
 * @see CapsuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapsuleApp.class)
public class CapsuleResourceIntTest {

    private static final String DEFAULT_CAPSULE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CAPSULE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CAPSULE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CAPSULE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private CapsuleMapper capsuleMapper;

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCapsuleMockMvc;

    private Capsule capsule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapsuleResource capsuleResource = new CapsuleResource(capsuleService);
        this.restCapsuleMockMvc = MockMvcBuilders.standaloneSetup(capsuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capsule createEntity(EntityManager em) {
        Capsule capsule = new Capsule()
            .capsuleType(DEFAULT_CAPSULE_TYPE)
            .capsuleNumber(DEFAULT_CAPSULE_NUMBER);
        return capsule;
    }

    @Before
    public void initTest() {
        capsule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapsule() throws Exception {
        int databaseSizeBeforeCreate = capsuleRepository.findAll().size();

        // Create the Capsule
        CapsuleDTO capsuleDTO = capsuleMapper.toDto(capsule);
        restCapsuleMockMvc.perform(post("/api/capsules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleDTO)))
            .andExpect(status().isCreated());

        // Validate the Capsule in the database
        List<Capsule> capsuleList = capsuleRepository.findAll();
        assertThat(capsuleList).hasSize(databaseSizeBeforeCreate + 1);
        Capsule testCapsule = capsuleList.get(capsuleList.size() - 1);
        assertThat(testCapsule.getCapsuleType()).isEqualTo(DEFAULT_CAPSULE_TYPE);
        assertThat(testCapsule.getCapsuleNumber()).isEqualTo(DEFAULT_CAPSULE_NUMBER);
    }

    @Test
    @Transactional
    public void createCapsuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capsuleRepository.findAll().size();

        // Create the Capsule with an existing ID
        capsule.setId(1L);
        CapsuleDTO capsuleDTO = capsuleMapper.toDto(capsule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapsuleMockMvc.perform(post("/api/capsules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Capsule in the database
        List<Capsule> capsuleList = capsuleRepository.findAll();
        assertThat(capsuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCapsules() throws Exception {
        // Initialize the database
        capsuleRepository.saveAndFlush(capsule);

        // Get all the capsuleList
        restCapsuleMockMvc.perform(get("/api/capsules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capsule.getId().intValue())))
            .andExpect(jsonPath("$.[*].capsuleType").value(hasItem(DEFAULT_CAPSULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].capsuleNumber").value(hasItem(DEFAULT_CAPSULE_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getCapsule() throws Exception {
        // Initialize the database
        capsuleRepository.saveAndFlush(capsule);

        // Get the capsule
        restCapsuleMockMvc.perform(get("/api/capsules/{id}", capsule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capsule.getId().intValue()))
            .andExpect(jsonPath("$.capsuleType").value(DEFAULT_CAPSULE_TYPE.toString()))
            .andExpect(jsonPath("$.capsuleNumber").value(DEFAULT_CAPSULE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCapsule() throws Exception {
        // Get the capsule
        restCapsuleMockMvc.perform(get("/api/capsules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapsule() throws Exception {
        // Initialize the database
        capsuleRepository.saveAndFlush(capsule);

        int databaseSizeBeforeUpdate = capsuleRepository.findAll().size();

        // Update the capsule
        Capsule updatedCapsule = capsuleRepository.findById(capsule.getId()).get();
        // Disconnect from session so that the updates on updatedCapsule are not directly saved in db
        em.detach(updatedCapsule);
        updatedCapsule
            .capsuleType(UPDATED_CAPSULE_TYPE)
            .capsuleNumber(UPDATED_CAPSULE_NUMBER);
        CapsuleDTO capsuleDTO = capsuleMapper.toDto(updatedCapsule);

        restCapsuleMockMvc.perform(put("/api/capsules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleDTO)))
            .andExpect(status().isOk());

        // Validate the Capsule in the database
        List<Capsule> capsuleList = capsuleRepository.findAll();
        assertThat(capsuleList).hasSize(databaseSizeBeforeUpdate);
        Capsule testCapsule = capsuleList.get(capsuleList.size() - 1);
        assertThat(testCapsule.getCapsuleType()).isEqualTo(UPDATED_CAPSULE_TYPE);
        assertThat(testCapsule.getCapsuleNumber()).isEqualTo(UPDATED_CAPSULE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCapsule() throws Exception {
        int databaseSizeBeforeUpdate = capsuleRepository.findAll().size();

        // Create the Capsule
        CapsuleDTO capsuleDTO = capsuleMapper.toDto(capsule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapsuleMockMvc.perform(put("/api/capsules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Capsule in the database
        List<Capsule> capsuleList = capsuleRepository.findAll();
        assertThat(capsuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCapsule() throws Exception {
        // Initialize the database
        capsuleRepository.saveAndFlush(capsule);

        int databaseSizeBeforeDelete = capsuleRepository.findAll().size();

        // Delete the capsule
        restCapsuleMockMvc.perform(delete("/api/capsules/{id}", capsule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Capsule> capsuleList = capsuleRepository.findAll();
        assertThat(capsuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capsule.class);
        Capsule capsule1 = new Capsule();
        capsule1.setId(1L);
        Capsule capsule2 = new Capsule();
        capsule2.setId(capsule1.getId());
        assertThat(capsule1).isEqualTo(capsule2);
        capsule2.setId(2L);
        assertThat(capsule1).isNotEqualTo(capsule2);
        capsule1.setId(null);
        assertThat(capsule1).isNotEqualTo(capsule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapsuleDTO.class);
        CapsuleDTO capsuleDTO1 = new CapsuleDTO();
        capsuleDTO1.setId(1L);
        CapsuleDTO capsuleDTO2 = new CapsuleDTO();
        assertThat(capsuleDTO1).isNotEqualTo(capsuleDTO2);
        capsuleDTO2.setId(capsuleDTO1.getId());
        assertThat(capsuleDTO1).isEqualTo(capsuleDTO2);
        capsuleDTO2.setId(2L);
        assertThat(capsuleDTO1).isNotEqualTo(capsuleDTO2);
        capsuleDTO1.setId(null);
        assertThat(capsuleDTO1).isNotEqualTo(capsuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(capsuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(capsuleMapper.fromId(null)).isNull();
    }
}
