package com.capsule.web.rest;

import com.capsule.CapsuleApp;

import com.capsule.domain.ExaminationReading;
import com.capsule.repository.ExaminationReadingRepository;
import com.capsule.service.ExaminationReadingService;
import com.capsule.service.dto.ExaminationReadingDTO;
import com.capsule.service.mapper.ExaminationReadingMapper;
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
 * Test class for the ExaminationReadingResource REST controller.
 *
 * @see ExaminationReadingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapsuleApp.class)
public class ExaminationReadingResourceIntTest {

    private static final Long DEFAULT_EXAMINATION_ID = 1L;
    private static final Long UPDATED_EXAMINATION_ID = 2L;

    private static final Long DEFAULT_EXPERT_ID = 1L;
    private static final Long UPDATED_EXPERT_ID = 2L;

    private static final Long DEFAULT_APPLICANT_ID = 1L;
    private static final Long UPDATED_APPLICANT_ID = 2L;

    private static final String DEFAULT_APPLICANT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_ROLE = "BBBBBBBBBB";

    @Autowired
    private ExaminationReadingRepository examinationReadingRepository;

    @Autowired
    private ExaminationReadingMapper examinationReadingMapper;

    @Autowired
    private ExaminationReadingService examinationReadingService;

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

    private MockMvc restExaminationReadingMockMvc;

    private ExaminationReading examinationReading;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExaminationReadingResource examinationReadingResource = new ExaminationReadingResource(examinationReadingService);
        this.restExaminationReadingMockMvc = MockMvcBuilders.standaloneSetup(examinationReadingResource)
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
    public static ExaminationReading createEntity(EntityManager em) {
        ExaminationReading examinationReading = new ExaminationReading()
            .examinationId(DEFAULT_EXAMINATION_ID)
            .expertId(DEFAULT_EXPERT_ID)
            .applicantId(DEFAULT_APPLICANT_ID)
            .applicantRole(DEFAULT_APPLICANT_ROLE);
        return examinationReading;
    }

    @Before
    public void initTest() {
        examinationReading = createEntity(em);
    }

    @Test
    @Transactional
    public void createExaminationReading() throws Exception {
        int databaseSizeBeforeCreate = examinationReadingRepository.findAll().size();

        // Create the ExaminationReading
        ExaminationReadingDTO examinationReadingDTO = examinationReadingMapper.toDto(examinationReading);
        restExaminationReadingMockMvc.perform(post("/api/examination-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationReadingDTO)))
            .andExpect(status().isCreated());

        // Validate the ExaminationReading in the database
        List<ExaminationReading> examinationReadingList = examinationReadingRepository.findAll();
        assertThat(examinationReadingList).hasSize(databaseSizeBeforeCreate + 1);
        ExaminationReading testExaminationReading = examinationReadingList.get(examinationReadingList.size() - 1);
        assertThat(testExaminationReading.getExaminationId()).isEqualTo(DEFAULT_EXAMINATION_ID);
        assertThat(testExaminationReading.getExpertId()).isEqualTo(DEFAULT_EXPERT_ID);
        assertThat(testExaminationReading.getApplicantId()).isEqualTo(DEFAULT_APPLICANT_ID);
        assertThat(testExaminationReading.getApplicantRole()).isEqualTo(DEFAULT_APPLICANT_ROLE);
    }

    @Test
    @Transactional
    public void createExaminationReadingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examinationReadingRepository.findAll().size();

        // Create the ExaminationReading with an existing ID
        examinationReading.setId(1L);
        ExaminationReadingDTO examinationReadingDTO = examinationReadingMapper.toDto(examinationReading);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExaminationReadingMockMvc.perform(post("/api/examination-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationReadingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExaminationReading in the database
        List<ExaminationReading> examinationReadingList = examinationReadingRepository.findAll();
        assertThat(examinationReadingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExaminationReadings() throws Exception {
        // Initialize the database
        examinationReadingRepository.saveAndFlush(examinationReading);

        // Get all the examinationReadingList
        restExaminationReadingMockMvc.perform(get("/api/examination-readings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examinationReading.getId().intValue())))
            .andExpect(jsonPath("$.[*].examinationId").value(hasItem(DEFAULT_EXAMINATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].expertId").value(hasItem(DEFAULT_EXPERT_ID.intValue())))
            .andExpect(jsonPath("$.[*].applicantId").value(hasItem(DEFAULT_APPLICANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].applicantRole").value(hasItem(DEFAULT_APPLICANT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getExaminationReading() throws Exception {
        // Initialize the database
        examinationReadingRepository.saveAndFlush(examinationReading);

        // Get the examinationReading
        restExaminationReadingMockMvc.perform(get("/api/examination-readings/{id}", examinationReading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examinationReading.getId().intValue()))
            .andExpect(jsonPath("$.examinationId").value(DEFAULT_EXAMINATION_ID.intValue()))
            .andExpect(jsonPath("$.expertId").value(DEFAULT_EXPERT_ID.intValue()))
            .andExpect(jsonPath("$.applicantId").value(DEFAULT_APPLICANT_ID.intValue()))
            .andExpect(jsonPath("$.applicantRole").value(DEFAULT_APPLICANT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExaminationReading() throws Exception {
        // Get the examinationReading
        restExaminationReadingMockMvc.perform(get("/api/examination-readings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExaminationReading() throws Exception {
        // Initialize the database
        examinationReadingRepository.saveAndFlush(examinationReading);

        int databaseSizeBeforeUpdate = examinationReadingRepository.findAll().size();

        // Update the examinationReading
        ExaminationReading updatedExaminationReading = examinationReadingRepository.findById(examinationReading.getId()).get();
        // Disconnect from session so that the updates on updatedExaminationReading are not directly saved in db
        em.detach(updatedExaminationReading);
        updatedExaminationReading
            .examinationId(UPDATED_EXAMINATION_ID)
            .expertId(UPDATED_EXPERT_ID)
            .applicantId(UPDATED_APPLICANT_ID)
            .applicantRole(UPDATED_APPLICANT_ROLE);
        ExaminationReadingDTO examinationReadingDTO = examinationReadingMapper.toDto(updatedExaminationReading);

        restExaminationReadingMockMvc.perform(put("/api/examination-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationReadingDTO)))
            .andExpect(status().isOk());

        // Validate the ExaminationReading in the database
        List<ExaminationReading> examinationReadingList = examinationReadingRepository.findAll();
        assertThat(examinationReadingList).hasSize(databaseSizeBeforeUpdate);
        ExaminationReading testExaminationReading = examinationReadingList.get(examinationReadingList.size() - 1);
        assertThat(testExaminationReading.getExaminationId()).isEqualTo(UPDATED_EXAMINATION_ID);
        assertThat(testExaminationReading.getExpertId()).isEqualTo(UPDATED_EXPERT_ID);
        assertThat(testExaminationReading.getApplicantId()).isEqualTo(UPDATED_APPLICANT_ID);
        assertThat(testExaminationReading.getApplicantRole()).isEqualTo(UPDATED_APPLICANT_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingExaminationReading() throws Exception {
        int databaseSizeBeforeUpdate = examinationReadingRepository.findAll().size();

        // Create the ExaminationReading
        ExaminationReadingDTO examinationReadingDTO = examinationReadingMapper.toDto(examinationReading);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExaminationReadingMockMvc.perform(put("/api/examination-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationReadingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExaminationReading in the database
        List<ExaminationReading> examinationReadingList = examinationReadingRepository.findAll();
        assertThat(examinationReadingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExaminationReading() throws Exception {
        // Initialize the database
        examinationReadingRepository.saveAndFlush(examinationReading);

        int databaseSizeBeforeDelete = examinationReadingRepository.findAll().size();

        // Delete the examinationReading
        restExaminationReadingMockMvc.perform(delete("/api/examination-readings/{id}", examinationReading.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExaminationReading> examinationReadingList = examinationReadingRepository.findAll();
        assertThat(examinationReadingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExaminationReading.class);
        ExaminationReading examinationReading1 = new ExaminationReading();
        examinationReading1.setId(1L);
        ExaminationReading examinationReading2 = new ExaminationReading();
        examinationReading2.setId(examinationReading1.getId());
        assertThat(examinationReading1).isEqualTo(examinationReading2);
        examinationReading2.setId(2L);
        assertThat(examinationReading1).isNotEqualTo(examinationReading2);
        examinationReading1.setId(null);
        assertThat(examinationReading1).isNotEqualTo(examinationReading2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExaminationReadingDTO.class);
        ExaminationReadingDTO examinationReadingDTO1 = new ExaminationReadingDTO();
        examinationReadingDTO1.setId(1L);
        ExaminationReadingDTO examinationReadingDTO2 = new ExaminationReadingDTO();
        assertThat(examinationReadingDTO1).isNotEqualTo(examinationReadingDTO2);
        examinationReadingDTO2.setId(examinationReadingDTO1.getId());
        assertThat(examinationReadingDTO1).isEqualTo(examinationReadingDTO2);
        examinationReadingDTO2.setId(2L);
        assertThat(examinationReadingDTO1).isNotEqualTo(examinationReadingDTO2);
        examinationReadingDTO1.setId(null);
        assertThat(examinationReadingDTO1).isNotEqualTo(examinationReadingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examinationReadingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examinationReadingMapper.fromId(null)).isNull();
    }
}
