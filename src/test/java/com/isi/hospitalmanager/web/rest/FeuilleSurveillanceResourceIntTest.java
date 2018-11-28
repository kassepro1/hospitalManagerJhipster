package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.FeuilleSurveillance;
import com.isi.hospitalmanager.repository.FeuilleSurveillanceRepository;
import com.isi.hospitalmanager.service.FeuilleSurveillanceService;
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
 * Test class for the FeuilleSurveillanceResource REST controller.
 *
 * @see FeuilleSurveillanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class FeuilleSurveillanceResourceIntTest {

    private static final LocalDate DEFAULT_HEURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HEURE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_POULS = "AAAAAAAAAA";
    private static final String UPDATED_POULS = "BBBBBBBBBB";

    private static final String DEFAULT_PA = "AAAAAAAAAA";
    private static final String UPDATED_PA = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    private static final String DEFAULT_FREQ_RESPI = "AAAAAAAAAA";
    private static final String UPDATED_FREQ_RESPI = "BBBBBBBBBB";

    private static final String DEFAULT_DIURESE = "AAAAAAAAAA";
    private static final String UPDATED_DIURESE = "BBBBBBBBBB";

    private static final String DEFAULT_GLOBE_UTERIN = "AAAAAAAAAA";
    private static final String UPDATED_GLOBE_UTERIN = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private FeuilleSurveillanceRepository feuilleSurveillanceRepository;

    @Autowired
    private FeuilleSurveillanceService feuilleSurveillanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeuilleSurveillanceMockMvc;

    private FeuilleSurveillance feuilleSurveillance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeuilleSurveillanceResource feuilleSurveillanceResource = new FeuilleSurveillanceResource(feuilleSurveillanceService);
        this.restFeuilleSurveillanceMockMvc = MockMvcBuilders.standaloneSetup(feuilleSurveillanceResource)
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
    public static FeuilleSurveillance createEntity(EntityManager em) {
        FeuilleSurveillance feuilleSurveillance = new FeuilleSurveillance()
            .heure(DEFAULT_HEURE)
            .pouls(DEFAULT_POULS)
            .pa(DEFAULT_PA)
            .temperature(DEFAULT_TEMPERATURE)
            .freqRespi(DEFAULT_FREQ_RESPI)
            .diurese(DEFAULT_DIURESE)
            .globeUterin(DEFAULT_GLOBE_UTERIN)
            .observation(DEFAULT_OBSERVATION);
        return feuilleSurveillance;
    }

    @Before
    public void initTest() {
        feuilleSurveillance = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeuilleSurveillance() throws Exception {
        int databaseSizeBeforeCreate = feuilleSurveillanceRepository.findAll().size();

        // Create the FeuilleSurveillance
        restFeuilleSurveillanceMockMvc.perform(post("/api/feuille-surveillances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSurveillance)))
            .andExpect(status().isCreated());

        // Validate the FeuilleSurveillance in the database
        List<FeuilleSurveillance> feuilleSurveillanceList = feuilleSurveillanceRepository.findAll();
        assertThat(feuilleSurveillanceList).hasSize(databaseSizeBeforeCreate + 1);
        FeuilleSurveillance testFeuilleSurveillance = feuilleSurveillanceList.get(feuilleSurveillanceList.size() - 1);
        assertThat(testFeuilleSurveillance.getHeure()).isEqualTo(DEFAULT_HEURE);
        assertThat(testFeuilleSurveillance.getPouls()).isEqualTo(DEFAULT_POULS);
        assertThat(testFeuilleSurveillance.getPa()).isEqualTo(DEFAULT_PA);
        assertThat(testFeuilleSurveillance.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testFeuilleSurveillance.getFreqRespi()).isEqualTo(DEFAULT_FREQ_RESPI);
        assertThat(testFeuilleSurveillance.getDiurese()).isEqualTo(DEFAULT_DIURESE);
        assertThat(testFeuilleSurveillance.getGlobeUterin()).isEqualTo(DEFAULT_GLOBE_UTERIN);
        assertThat(testFeuilleSurveillance.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    public void createFeuilleSurveillanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feuilleSurveillanceRepository.findAll().size();

        // Create the FeuilleSurveillance with an existing ID
        feuilleSurveillance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeuilleSurveillanceMockMvc.perform(post("/api/feuille-surveillances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSurveillance)))
            .andExpect(status().isBadRequest());

        // Validate the FeuilleSurveillance in the database
        List<FeuilleSurveillance> feuilleSurveillanceList = feuilleSurveillanceRepository.findAll();
        assertThat(feuilleSurveillanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFeuilleSurveillances() throws Exception {
        // Initialize the database
        feuilleSurveillanceRepository.saveAndFlush(feuilleSurveillance);

        // Get all the feuilleSurveillanceList
        restFeuilleSurveillanceMockMvc.perform(get("/api/feuille-surveillances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feuilleSurveillance.getId().intValue())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())))
            .andExpect(jsonPath("$.[*].pouls").value(hasItem(DEFAULT_POULS.toString())))
            .andExpect(jsonPath("$.[*].pa").value(hasItem(DEFAULT_PA.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].freqRespi").value(hasItem(DEFAULT_FREQ_RESPI.toString())))
            .andExpect(jsonPath("$.[*].diurese").value(hasItem(DEFAULT_DIURESE.toString())))
            .andExpect(jsonPath("$.[*].globeUterin").value(hasItem(DEFAULT_GLOBE_UTERIN.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }
    
    @Test
    @Transactional
    public void getFeuilleSurveillance() throws Exception {
        // Initialize the database
        feuilleSurveillanceRepository.saveAndFlush(feuilleSurveillance);

        // Get the feuilleSurveillance
        restFeuilleSurveillanceMockMvc.perform(get("/api/feuille-surveillances/{id}", feuilleSurveillance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feuilleSurveillance.getId().intValue()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE.toString()))
            .andExpect(jsonPath("$.pouls").value(DEFAULT_POULS.toString()))
            .andExpect(jsonPath("$.pa").value(DEFAULT_PA.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.toString()))
            .andExpect(jsonPath("$.freqRespi").value(DEFAULT_FREQ_RESPI.toString()))
            .andExpect(jsonPath("$.diurese").value(DEFAULT_DIURESE.toString()))
            .andExpect(jsonPath("$.globeUterin").value(DEFAULT_GLOBE_UTERIN.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeuilleSurveillance() throws Exception {
        // Get the feuilleSurveillance
        restFeuilleSurveillanceMockMvc.perform(get("/api/feuille-surveillances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeuilleSurveillance() throws Exception {
        // Initialize the database
        feuilleSurveillanceService.save(feuilleSurveillance);

        int databaseSizeBeforeUpdate = feuilleSurveillanceRepository.findAll().size();

        // Update the feuilleSurveillance
        FeuilleSurveillance updatedFeuilleSurveillance = feuilleSurveillanceRepository.findById(feuilleSurveillance.getId()).get();
        // Disconnect from session so that the updates on updatedFeuilleSurveillance are not directly saved in db
        em.detach(updatedFeuilleSurveillance);
        updatedFeuilleSurveillance
            .heure(UPDATED_HEURE)
            .pouls(UPDATED_POULS)
            .pa(UPDATED_PA)
            .temperature(UPDATED_TEMPERATURE)
            .freqRespi(UPDATED_FREQ_RESPI)
            .diurese(UPDATED_DIURESE)
            .globeUterin(UPDATED_GLOBE_UTERIN)
            .observation(UPDATED_OBSERVATION);

        restFeuilleSurveillanceMockMvc.perform(put("/api/feuille-surveillances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeuilleSurveillance)))
            .andExpect(status().isOk());

        // Validate the FeuilleSurveillance in the database
        List<FeuilleSurveillance> feuilleSurveillanceList = feuilleSurveillanceRepository.findAll();
        assertThat(feuilleSurveillanceList).hasSize(databaseSizeBeforeUpdate);
        FeuilleSurveillance testFeuilleSurveillance = feuilleSurveillanceList.get(feuilleSurveillanceList.size() - 1);
        assertThat(testFeuilleSurveillance.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testFeuilleSurveillance.getPouls()).isEqualTo(UPDATED_POULS);
        assertThat(testFeuilleSurveillance.getPa()).isEqualTo(UPDATED_PA);
        assertThat(testFeuilleSurveillance.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testFeuilleSurveillance.getFreqRespi()).isEqualTo(UPDATED_FREQ_RESPI);
        assertThat(testFeuilleSurveillance.getDiurese()).isEqualTo(UPDATED_DIURESE);
        assertThat(testFeuilleSurveillance.getGlobeUterin()).isEqualTo(UPDATED_GLOBE_UTERIN);
        assertThat(testFeuilleSurveillance.getObservation()).isEqualTo(UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingFeuilleSurveillance() throws Exception {
        int databaseSizeBeforeUpdate = feuilleSurveillanceRepository.findAll().size();

        // Create the FeuilleSurveillance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeuilleSurveillanceMockMvc.perform(put("/api/feuille-surveillances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSurveillance)))
            .andExpect(status().isBadRequest());

        // Validate the FeuilleSurveillance in the database
        List<FeuilleSurveillance> feuilleSurveillanceList = feuilleSurveillanceRepository.findAll();
        assertThat(feuilleSurveillanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeuilleSurveillance() throws Exception {
        // Initialize the database
        feuilleSurveillanceService.save(feuilleSurveillance);

        int databaseSizeBeforeDelete = feuilleSurveillanceRepository.findAll().size();

        // Get the feuilleSurveillance
        restFeuilleSurveillanceMockMvc.perform(delete("/api/feuille-surveillances/{id}", feuilleSurveillance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeuilleSurveillance> feuilleSurveillanceList = feuilleSurveillanceRepository.findAll();
        assertThat(feuilleSurveillanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeuilleSurveillance.class);
        FeuilleSurveillance feuilleSurveillance1 = new FeuilleSurveillance();
        feuilleSurveillance1.setId(1L);
        FeuilleSurveillance feuilleSurveillance2 = new FeuilleSurveillance();
        feuilleSurveillance2.setId(feuilleSurveillance1.getId());
        assertThat(feuilleSurveillance1).isEqualTo(feuilleSurveillance2);
        feuilleSurveillance2.setId(2L);
        assertThat(feuilleSurveillance1).isNotEqualTo(feuilleSurveillance2);
        feuilleSurveillance1.setId(null);
        assertThat(feuilleSurveillance1).isNotEqualTo(feuilleSurveillance2);
    }
}
