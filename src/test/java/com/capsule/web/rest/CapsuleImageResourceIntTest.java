package com.capsule.web.rest;

import com.capsule.CapsuleApp;

import com.capsule.domain.CapsuleImage;
import com.capsule.repository.CapsuleImageRepository;
import com.capsule.service.CapsuleImageService;
import com.capsule.service.dto.CapsuleImageDTO;
import com.capsule.service.mapper.CapsuleImageMapper;
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
 * Test class for the CapsuleImageResource REST controller.
 *
 * @see CapsuleImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapsuleApp.class)
public class CapsuleImageResourceIntTest {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private CapsuleImageRepository capsuleImageRepository;

    @Autowired
    private CapsuleImageMapper capsuleImageMapper;

    @Autowired
    private CapsuleImageService capsuleImageService;

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

    private MockMvc restCapsuleImageMockMvc;

    private CapsuleImage capsuleImage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapsuleImageResource capsuleImageResource = new CapsuleImageResource(capsuleImageService);
        this.restCapsuleImageMockMvc = MockMvcBuilders.standaloneSetup(capsuleImageResource)
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
    public static CapsuleImage createEntity(EntityManager em) {
        CapsuleImage capsuleImage = new CapsuleImage()
            .imageUrl(DEFAULT_IMAGE_URL);
        return capsuleImage;
    }

    @Before
    public void initTest() {
        capsuleImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapsuleImage() throws Exception {
        int databaseSizeBeforeCreate = capsuleImageRepository.findAll().size();

        // Create the CapsuleImage
        CapsuleImageDTO capsuleImageDTO = capsuleImageMapper.toDto(capsuleImage);
        restCapsuleImageMockMvc.perform(post("/api/capsule-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleImageDTO)))
            .andExpect(status().isCreated());

        // Validate the CapsuleImage in the database
        List<CapsuleImage> capsuleImageList = capsuleImageRepository.findAll();
        assertThat(capsuleImageList).hasSize(databaseSizeBeforeCreate + 1);
        CapsuleImage testCapsuleImage = capsuleImageList.get(capsuleImageList.size() - 1);
        assertThat(testCapsuleImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createCapsuleImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capsuleImageRepository.findAll().size();

        // Create the CapsuleImage with an existing ID
        capsuleImage.setId(1L);
        CapsuleImageDTO capsuleImageDTO = capsuleImageMapper.toDto(capsuleImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapsuleImageMockMvc.perform(post("/api/capsule-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CapsuleImage in the database
        List<CapsuleImage> capsuleImageList = capsuleImageRepository.findAll();
        assertThat(capsuleImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCapsuleImages() throws Exception {
        // Initialize the database
        capsuleImageRepository.saveAndFlush(capsuleImage);

        // Get all the capsuleImageList
        restCapsuleImageMockMvc.perform(get("/api/capsule-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capsuleImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getCapsuleImage() throws Exception {
        // Initialize the database
        capsuleImageRepository.saveAndFlush(capsuleImage);

        // Get the capsuleImage
        restCapsuleImageMockMvc.perform(get("/api/capsule-images/{id}", capsuleImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capsuleImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCapsuleImage() throws Exception {
        // Get the capsuleImage
        restCapsuleImageMockMvc.perform(get("/api/capsule-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapsuleImage() throws Exception {
        // Initialize the database
        capsuleImageRepository.saveAndFlush(capsuleImage);

        int databaseSizeBeforeUpdate = capsuleImageRepository.findAll().size();

        // Update the capsuleImage
        CapsuleImage updatedCapsuleImage = capsuleImageRepository.findById(capsuleImage.getId()).get();
        // Disconnect from session so that the updates on updatedCapsuleImage are not directly saved in db
        em.detach(updatedCapsuleImage);
        updatedCapsuleImage
            .imageUrl(UPDATED_IMAGE_URL);
        CapsuleImageDTO capsuleImageDTO = capsuleImageMapper.toDto(updatedCapsuleImage);

        restCapsuleImageMockMvc.perform(put("/api/capsule-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleImageDTO)))
            .andExpect(status().isOk());

        // Validate the CapsuleImage in the database
        List<CapsuleImage> capsuleImageList = capsuleImageRepository.findAll();
        assertThat(capsuleImageList).hasSize(databaseSizeBeforeUpdate);
        CapsuleImage testCapsuleImage = capsuleImageList.get(capsuleImageList.size() - 1);
        assertThat(testCapsuleImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingCapsuleImage() throws Exception {
        int databaseSizeBeforeUpdate = capsuleImageRepository.findAll().size();

        // Create the CapsuleImage
        CapsuleImageDTO capsuleImageDTO = capsuleImageMapper.toDto(capsuleImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapsuleImageMockMvc.perform(put("/api/capsule-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capsuleImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CapsuleImage in the database
        List<CapsuleImage> capsuleImageList = capsuleImageRepository.findAll();
        assertThat(capsuleImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCapsuleImage() throws Exception {
        // Initialize the database
        capsuleImageRepository.saveAndFlush(capsuleImage);

        int databaseSizeBeforeDelete = capsuleImageRepository.findAll().size();

        // Delete the capsuleImage
        restCapsuleImageMockMvc.perform(delete("/api/capsule-images/{id}", capsuleImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CapsuleImage> capsuleImageList = capsuleImageRepository.findAll();
        assertThat(capsuleImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapsuleImage.class);
        CapsuleImage capsuleImage1 = new CapsuleImage();
        capsuleImage1.setId(1L);
        CapsuleImage capsuleImage2 = new CapsuleImage();
        capsuleImage2.setId(capsuleImage1.getId());
        assertThat(capsuleImage1).isEqualTo(capsuleImage2);
        capsuleImage2.setId(2L);
        assertThat(capsuleImage1).isNotEqualTo(capsuleImage2);
        capsuleImage1.setId(null);
        assertThat(capsuleImage1).isNotEqualTo(capsuleImage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapsuleImageDTO.class);
        CapsuleImageDTO capsuleImageDTO1 = new CapsuleImageDTO();
        capsuleImageDTO1.setId(1L);
        CapsuleImageDTO capsuleImageDTO2 = new CapsuleImageDTO();
        assertThat(capsuleImageDTO1).isNotEqualTo(capsuleImageDTO2);
        capsuleImageDTO2.setId(capsuleImageDTO1.getId());
        assertThat(capsuleImageDTO1).isEqualTo(capsuleImageDTO2);
        capsuleImageDTO2.setId(2L);
        assertThat(capsuleImageDTO1).isNotEqualTo(capsuleImageDTO2);
        capsuleImageDTO1.setId(null);
        assertThat(capsuleImageDTO1).isNotEqualTo(capsuleImageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(capsuleImageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(capsuleImageMapper.fromId(null)).isNull();
    }
}
