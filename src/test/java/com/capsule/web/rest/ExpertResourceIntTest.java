package com.capsule.web.rest;

import com.capsule.CapsuleApp;

import com.capsule.domain.Expert;
import com.capsule.repository.ExpertRepository;
import com.capsule.service.ExpertService;
import com.capsule.service.dto.ExpertDTO;
import com.capsule.service.mapper.ExpertMapper;
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
 * Test class for the ExpertResource REST controller.
 *
 * @see ExpertResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapsuleApp.class)
public class ExpertResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private ExpertService expertService;

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

    private MockMvc restExpertMockMvc;

    private Expert expert;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpertResource expertResource = new ExpertResource(expertService);
        this.restExpertMockMvc = MockMvcBuilders.standaloneSetup(expertResource)
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
    public static Expert createEntity(EntityManager em) {
        Expert expert = new Expert()
            .userId(DEFAULT_USER_ID);
        return expert;
    }

    @Before
    public void initTest() {
        expert = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpert() throws Exception {
        int databaseSizeBeforeCreate = expertRepository.findAll().size();

        // Create the Expert
        ExpertDTO expertDTO = expertMapper.toDto(expert);
        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expertDTO)))
            .andExpect(status().isCreated());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeCreate + 1);
        Expert testExpert = expertList.get(expertList.size() - 1);
        assertThat(testExpert.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createExpertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expertRepository.findAll().size();

        // Create the Expert with an existing ID
        expert.setId(1L);
        ExpertDTO expertDTO = expertMapper.toDto(expert);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExperts() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList
        restExpertMockMvc.perform(get("/api/experts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expert.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get the expert
        restExpertMockMvc.perform(get("/api/experts/{id}", expert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expert.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExpert() throws Exception {
        // Get the expert
        restExpertMockMvc.perform(get("/api/experts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        int databaseSizeBeforeUpdate = expertRepository.findAll().size();

        // Update the expert
        Expert updatedExpert = expertRepository.findById(expert.getId()).get();
        // Disconnect from session so that the updates on updatedExpert are not directly saved in db
        em.detach(updatedExpert);
        updatedExpert
            .userId(UPDATED_USER_ID);
        ExpertDTO expertDTO = expertMapper.toDto(updatedExpert);

        restExpertMockMvc.perform(put("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expertDTO)))
            .andExpect(status().isOk());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeUpdate);
        Expert testExpert = expertList.get(expertList.size() - 1);
        assertThat(testExpert.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingExpert() throws Exception {
        int databaseSizeBeforeUpdate = expertRepository.findAll().size();

        // Create the Expert
        ExpertDTO expertDTO = expertMapper.toDto(expert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpertMockMvc.perform(put("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        int databaseSizeBeforeDelete = expertRepository.findAll().size();

        // Delete the expert
        restExpertMockMvc.perform(delete("/api/experts/{id}", expert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expert.class);
        Expert expert1 = new Expert();
        expert1.setId(1L);
        Expert expert2 = new Expert();
        expert2.setId(expert1.getId());
        assertThat(expert1).isEqualTo(expert2);
        expert2.setId(2L);
        assertThat(expert1).isNotEqualTo(expert2);
        expert1.setId(null);
        assertThat(expert1).isNotEqualTo(expert2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpertDTO.class);
        ExpertDTO expertDTO1 = new ExpertDTO();
        expertDTO1.setId(1L);
        ExpertDTO expertDTO2 = new ExpertDTO();
        assertThat(expertDTO1).isNotEqualTo(expertDTO2);
        expertDTO2.setId(expertDTO1.getId());
        assertThat(expertDTO1).isEqualTo(expertDTO2);
        expertDTO2.setId(2L);
        assertThat(expertDTO1).isNotEqualTo(expertDTO2);
        expertDTO1.setId(null);
        assertThat(expertDTO1).isNotEqualTo(expertDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(expertMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(expertMapper.fromId(null)).isNull();
    }
}
