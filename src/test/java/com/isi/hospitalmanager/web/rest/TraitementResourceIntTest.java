package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.Traitement;
import com.isi.hospitalmanager.repository.TraitementRepository;
import com.isi.hospitalmanager.service.TraitementService;
import com.isi.hospitalmanager.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.isi.hospitalmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TraitementResource REST controller.
 *
 * @see TraitementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class TraitementResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIS_TRAITEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DETAIS_TRAITEMENT = "BBBBBBBBBB";

    @Autowired
    private TraitementRepository traitementRepository;

    @Autowired
    private TraitementService traitementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraitementMockMvc;

    private Traitement traitement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TraitementResource traitementResource = new TraitementResource(traitementService);
        this.restTraitementMockMvc = MockMvcBuilders.standaloneSetup(traitementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traitement createEntity(EntityManager em) {
        Traitement traitement = new Traitement()
            .date(DEFAULT_DATE)
            .observation(DEFAULT_OBSERVATION)
            .resultat(DEFAULT_RESULTAT)
            .detaisTraitement(DEFAULT_DETAIS_TRAITEMENT);
        return traitement;
    }

    @Before
    public void initTest() {
        traitement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraitement() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isCreated());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate + 1);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTraitement.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testTraitement.getResultat()).isEqualTo(DEFAULT_RESULTAT);
        assertThat(testTraitement.getDetaisTraitement()).isEqualTo(DEFAULT_DETAIS_TRAITEMENT);
    }

    @Test
    @Transactional
    public void createTraitementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement with an existing ID
        traitement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isBadRequest());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraitements() throws Exception {
        // Initialize the database
        traitementRepository.saveAndFlush(traitement);

        // Get all the traitementList
        restTraitementMockMvc.perform(get("/api/traitements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traitement.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())))
            .andExpect(jsonPath("$.[*].detaisTraitement").value(hasItem(DEFAULT_DETAIS_TRAITEMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getTraitement() throws Exception {
        // Initialize the database
        traitementRepository.saveAndFlush(traitement);

        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", traitement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traitement.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT.toString()))
            .andExpect(jsonPath("$.detaisTraitement").value(DEFAULT_DETAIS_TRAITEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraitement() throws Exception {
        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraitement() throws Exception {
        // Initialize the database
        traitementService.save(traitement);

        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Update the traitement
        Traitement updatedTraitement = traitementRepository.findById(traitement.getId()).get();
        // Disconnect from session so that the updates on updatedTraitement are not directly saved in db
        em.detach(updatedTraitement);
        updatedTraitement
            .date(UPDATED_DATE)
            .observation(UPDATED_OBSERVATION)
            .resultat(UPDATED_RESULTAT)
            .detaisTraitement(UPDATED_DETAIS_TRAITEMENT);

        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraitement)))
            .andExpect(status().isOk());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTraitement.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testTraitement.getResultat()).isEqualTo(UPDATED_RESULTAT);
        assertThat(testTraitement.getDetaisTraitement()).isEqualTo(UPDATED_DETAIS_TRAITEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTraitement() throws Exception {
        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Create the Traitement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isBadRequest());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTraitement() throws Exception {
        // Initialize the database
        traitementService.save(traitement);

        int databaseSizeBeforeDelete = traitementRepository.findAll().size();

        // Get the traitement
        restTraitementMockMvc.perform(delete("/api/traitements/{id}", traitement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traitement.class);
        Traitement traitement1 = new Traitement();
        traitement1.setId(1L);
        Traitement traitement2 = new Traitement();
        traitement2.setId(traitement1.getId());
        assertThat(traitement1).isEqualTo(traitement2);
        traitement2.setId(2L);
        assertThat(traitement1).isNotEqualTo(traitement2);
        traitement1.setId(null);
        assertThat(traitement1).isNotEqualTo(traitement2);
    }
}
