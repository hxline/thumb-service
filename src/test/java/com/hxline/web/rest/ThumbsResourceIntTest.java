package com.hxline.web.rest;

import com.hxline.Service1App;

import com.hxline.domain.Thumbs;
import com.hxline.repository.ThumbsRepository;
import com.hxline.service.ThumbsService;
import com.hxline.service.dto.ThumbsDTO;
import com.hxline.service.mapper.ThumbsMapper;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ThumbsResource REST controller.
 *
 * @see ThumbsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Service1App.class)
public class ThumbsResourceIntTest {

    private static final String DEFAULT_THREAD_ID = "AAAAAAAAAA";
    private static final String UPDATED_THREAD_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_THUMB_UP = 1;
    private static final Integer UPDATED_THUMB_UP = 2;

    private static final Integer DEFAULT_THUMB_DOWN = 1;
    private static final Integer UPDATED_THUMB_DOWN = 2;

    @Autowired
    private ThumbsRepository thumbsRepository;

    @Autowired
    private ThumbsMapper thumbsMapper;

    @Autowired
    private ThumbsService thumbsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restThumbsMockMvc;

    private Thumbs thumbs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThumbsResource thumbsResource = new ThumbsResource(thumbsService);
        this.restThumbsMockMvc = MockMvcBuilders.standaloneSetup(thumbsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thumbs createEntity(EntityManager em) {
        Thumbs thumbs = new Thumbs()
                .thread_id(DEFAULT_THREAD_ID)
                .thumb_up(DEFAULT_THUMB_UP)
                .thumb_down(DEFAULT_THUMB_DOWN);
        return thumbs;
    }

    @Before
    public void initTest() {
        thumbs = createEntity(em);
    }

    @Test
    @Transactional
    public void createThumbs() throws Exception {
        int databaseSizeBeforeCreate = thumbsRepository.findAll().size();

        // Create the Thumbs
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(thumbs);

        restThumbsMockMvc.perform(post("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thumbsDTO)))
            .andExpect(status().isCreated());

        // Validate the Thumbs in the database
        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeCreate + 1);
        Thumbs testThumbs = thumbsList.get(thumbsList.size() - 1);
        assertThat(testThumbs.getThread_id()).isEqualTo(DEFAULT_THREAD_ID);
        assertThat(testThumbs.getThumb_up()).isEqualTo(DEFAULT_THUMB_UP);
        assertThat(testThumbs.getThumb_down()).isEqualTo(DEFAULT_THUMB_DOWN);
    }

    @Test
    @Transactional
    public void createThumbsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thumbsRepository.findAll().size();

        // Create the Thumbs with an existing ID
        Thumbs existingThumbs = new Thumbs();
        existingThumbs.setId(1L);
        ThumbsDTO existingThumbsDTO = thumbsMapper.thumbsToThumbsDTO(existingThumbs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThumbsMockMvc.perform(post("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingThumbsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkThread_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbsRepository.findAll().size();
        // set the field null
        thumbs.setThread_id(null);

        // Create the Thumbs, which fails.
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(thumbs);

        restThumbsMockMvc.perform(post("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thumbsDTO)))
            .andExpect(status().isBadRequest());

        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThumb_upIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbsRepository.findAll().size();
        // set the field null
        thumbs.setThumb_up(null);

        // Create the Thumbs, which fails.
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(thumbs);

        restThumbsMockMvc.perform(post("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thumbsDTO)))
            .andExpect(status().isBadRequest());

        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThumb_downIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbsRepository.findAll().size();
        // set the field null
        thumbs.setThumb_down(null);

        // Create the Thumbs, which fails.
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(thumbs);

        restThumbsMockMvc.perform(post("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thumbsDTO)))
            .andExpect(status().isBadRequest());

        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThumbs() throws Exception {
        // Initialize the database
        thumbsRepository.saveAndFlush(thumbs);

        // Get all the thumbsList
        restThumbsMockMvc.perform(get("/api/thumbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thumbs.getId().intValue())))
            .andExpect(jsonPath("$.[*].thread_id").value(hasItem(DEFAULT_THREAD_ID.toString())))
            .andExpect(jsonPath("$.[*].thumb_up").value(hasItem(DEFAULT_THUMB_UP)))
            .andExpect(jsonPath("$.[*].thumb_down").value(hasItem(DEFAULT_THUMB_DOWN)));
    }

    @Test
    @Transactional
    public void getThumbs() throws Exception {
        // Initialize the database
        thumbsRepository.saveAndFlush(thumbs);

        // Get the thumbs
        restThumbsMockMvc.perform(get("/api/thumbs/{id}", thumbs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thumbs.getId().intValue()))
            .andExpect(jsonPath("$.thread_id").value(DEFAULT_THREAD_ID.toString()))
            .andExpect(jsonPath("$.thumb_up").value(DEFAULT_THUMB_UP))
            .andExpect(jsonPath("$.thumb_down").value(DEFAULT_THUMB_DOWN));
    }

    @Test
    @Transactional
    public void getNonExistingThumbs() throws Exception {
        // Get the thumbs
        restThumbsMockMvc.perform(get("/api/thumbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThumbs() throws Exception {
        // Initialize the database
        thumbsRepository.saveAndFlush(thumbs);
        int databaseSizeBeforeUpdate = thumbsRepository.findAll().size();

        // Update the thumbs
        Thumbs updatedThumbs = thumbsRepository.findOne(thumbs.getId());
        updatedThumbs
                .thread_id(UPDATED_THREAD_ID)
                .thumb_up(UPDATED_THUMB_UP)
                .thumb_down(UPDATED_THUMB_DOWN);
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(updatedThumbs);

        restThumbsMockMvc.perform(put("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thumbsDTO)))
            .andExpect(status().isOk());

        // Validate the Thumbs in the database
        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeUpdate);
        Thumbs testThumbs = thumbsList.get(thumbsList.size() - 1);
        assertThat(testThumbs.getThread_id()).isEqualTo(UPDATED_THREAD_ID);
        assertThat(testThumbs.getThumb_up()).isEqualTo(UPDATED_THUMB_UP);
        assertThat(testThumbs.getThumb_down()).isEqualTo(UPDATED_THUMB_DOWN);
    }

    @Test
    @Transactional
    public void updateNonExistingThumbs() throws Exception {
        int databaseSizeBeforeUpdate = thumbsRepository.findAll().size();

        // Create the Thumbs
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(thumbs);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThumbsMockMvc.perform(put("/api/thumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thumbsDTO)))
            .andExpect(status().isCreated());

        // Validate the Thumbs in the database
        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThumbs() throws Exception {
        // Initialize the database
        thumbsRepository.saveAndFlush(thumbs);
        int databaseSizeBeforeDelete = thumbsRepository.findAll().size();

        // Get the thumbs
        restThumbsMockMvc.perform(delete("/api/thumbs/{id}", thumbs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Thumbs> thumbsList = thumbsRepository.findAll();
        assertThat(thumbsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thumbs.class);
    }
}
