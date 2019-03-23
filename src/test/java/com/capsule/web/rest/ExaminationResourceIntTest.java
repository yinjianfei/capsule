package com.capsule.web.rest;

import com.capsule.CapsuleApp;

import com.capsule.domain.Examination;
import com.capsule.repository.ExaminationRepository;
import com.capsule.service.ExaminationService;
import com.capsule.service.dto.ExaminationDTO;
import com.capsule.service.mapper.ExaminationMapper;
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
 * Test class for the ExaminationResource REST controller.
 *
 * @see ExaminationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapsuleApp.class)
public class ExaminationResourceIntTest {

    private static final Long DEFAULT_PATIENT_ID = 1L;
    private static final Long UPDATED_PATIENT_ID = 2L;

    private static final Long DEFAULT_DOCTOR_ID = 1L;
    private static final Long UPDATED_DOCTOR_ID = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_USE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_USE_DATE = "BBBBBBBBBB";

    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private ExaminationMapper examinationMapper;

    @Autowired
    private ExaminationService examinationService;

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

    private MockMvc restExaminationMockMvc;

    private Examination examination;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExaminationResource examinationResource = new ExaminationResource(examinationService);
        this.restExaminationMockMvc = MockMvcBuilders.standaloneSetup(examinationResource)
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
    public static Examination createEntity(EntityManager em) {
        Examination examination = new Examination()
            .patientId(DEFAULT_PATIENT_ID)
            .doctorId(DEFAULT_DOCTOR_ID)
            .status(DEFAULT_STATUS)
            .useDate(DEFAULT_USE_DATE);
        return examination;
    }

    @Before
    public void initTest() {
        examination = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamination() throws Exception {
        int databaseSizeBeforeCreate = examinationRepository.findAll().size();

        // Create the Examination
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);
        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isCreated());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeCreate + 1);
        Examination testExamination = examinationList.get(examinationList.size() - 1);
        assertThat(testExamination.getPatientId()).isEqualTo(DEFAULT_PATIENT_ID);
        assertThat(testExamination.getDoctorId()).isEqualTo(DEFAULT_DOCTOR_ID);
        assertThat(testExamination.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExamination.getUseDate()).isEqualTo(DEFAULT_USE_DATE);
    }

    @Test
    @Transactional
    public void createExaminationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examinationRepository.findAll().size();

        // Create the Examination with an existing ID
        examination.setId(1L);
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExaminations() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        // Get all the examinationList
        restExaminationMockMvc.perform(get("/api/examinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examination.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientId").value(hasItem(DEFAULT_PATIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].doctorId").value(hasItem(DEFAULT_DOCTOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].useDate").value(hasItem(DEFAULT_USE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getExamination() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        // Get the examination
        restExaminationMockMvc.perform(get("/api/examinations/{id}", examination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examination.getId().intValue()))
            .andExpect(jsonPath("$.patientId").value(DEFAULT_PATIENT_ID.intValue()))
            .andExpect(jsonPath("$.doctorId").value(DEFAULT_DOCTOR_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.useDate").value(DEFAULT_USE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamination() throws Exception {
        // Get the examination
        restExaminationMockMvc.perform(get("/api/examinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamination() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        int databaseSizeBeforeUpdate = examinationRepository.findAll().size();

        // Update the examination
        Examination updatedExamination = examinationRepository.findById(examination.getId()).get();
        // Disconnect from session so that the updates on updatedExamination are not directly saved in db
        em.detach(updatedExamination);
        updatedExamination
            .patientId(UPDATED_PATIENT_ID)
            .doctorId(UPDATED_DOCTOR_ID)
            .status(UPDATED_STATUS)
            .useDate(UPDATED_USE_DATE);
        ExaminationDTO examinationDTO = examinationMapper.toDto(updatedExamination);

        restExaminationMockMvc.perform(put("/api/examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isOk());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeUpdate);
        Examination testExamination = examinationList.get(examinationList.size() - 1);
        assertThat(testExamination.getPatientId()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testExamination.getDoctorId()).isEqualTo(UPDATED_DOCTOR_ID);
        assertThat(testExamination.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExamination.getUseDate()).isEqualTo(UPDATED_USE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamination() throws Exception {
        int databaseSizeBeforeUpdate = examinationRepository.findAll().size();

        // Create the Examination
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExaminationMockMvc.perform(put("/api/examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamination() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        int databaseSizeBeforeDelete = examinationRepository.findAll().size();

        // Delete the examination
        restExaminationMockMvc.perform(delete("/api/examinations/{id}", examination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examination.class);
        Examination examination1 = new Examination();
        examination1.setId(1L);
        Examination examination2 = new Examination();
        examination2.setId(examination1.getId());
        assertThat(examination1).isEqualTo(examination2);
        examination2.setId(2L);
        assertThat(examination1).isNotEqualTo(examination2);
        examination1.setId(null);
        assertThat(examination1).isNotEqualTo(examination2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExaminationDTO.class);
        ExaminationDTO examinationDTO1 = new ExaminationDTO();
        examinationDTO1.setId(1L);
        ExaminationDTO examinationDTO2 = new ExaminationDTO();
        assertThat(examinationDTO1).isNotEqualTo(examinationDTO2);
        examinationDTO2.setId(examinationDTO1.getId());
        assertThat(examinationDTO1).isEqualTo(examinationDTO2);
        examinationDTO2.setId(2L);
        assertThat(examinationDTO1).isNotEqualTo(examinationDTO2);
        examinationDTO1.setId(null);
        assertThat(examinationDTO1).isNotEqualTo(examinationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examinationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examinationMapper.fromId(null)).isNull();
    }
}
