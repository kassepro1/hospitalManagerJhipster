package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.DossierMedical;
import com.isi.hospitalmanager.repository.DossierMedicalRepository;
import com.isi.hospitalmanager.service.DossierMedicalService;
import com.isi.hospitalmanager.web.rest.errors.ExceptionTranslator;
import com.isi.hospitalmanager.service.dto.DossierMedicalCriteria;
import com.isi.hospitalmanager.service.DossierMedicalQueryService;

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


import static com.isi.hospitalmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DossierMedicalResource REST controller.
 *
 * @see DossierMedicalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class DossierMedicalResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_FICHE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_FICHE = "BBBBBBBBBB";

    private static final Double DEFAULT_TAILLE = 1D;
    private static final Double UPDATED_TAILLE = 2D;

    private static final Double DEFAULT_POIDS = 1D;
    private static final Double UPDATED_POIDS = 2D;

    private static final String DEFAULT_TENSION = "AAAAAAAAAA";
    private static final String UPDATED_TENSION = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT = "BBBBBBBBBB";

    @Autowired
    private DossierMedicalRepository dossierMedicalRepository;

    @Autowired
    private DossierMedicalService dossierMedicalService;

    @Autowired
    private DossierMedicalQueryService dossierMedicalQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDossierMedicalMockMvc;

    private DossierMedical dossierMedical;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DossierMedicalResource dossierMedicalResource = new DossierMedicalResource(dossierMedicalService, dossierMedicalQueryService);
        this.restDossierMedicalMockMvc = MockMvcBuilders.standaloneSetup(dossierMedicalResource)
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
    public static DossierMedical createEntity(EntityManager em) {
        DossierMedical dossierMedical = new DossierMedical()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numFiche(DEFAULT_NUM_FICHE)
            .taille(DEFAULT_TAILLE)
            .poids(DEFAULT_POIDS)
            .tension(DEFAULT_TENSION)
            .temperature(DEFAULT_TEMPERATURE)
            .photo(DEFAULT_PHOTO)
            .resultat(DEFAULT_RESULTAT);
        return dossierMedical;
    }

    @Before
    public void initTest() {
        dossierMedical = createEntity(em);
    }

    @Test
    @Transactional
    public void createDossierMedical() throws Exception {
        int databaseSizeBeforeCreate = dossierMedicalRepository.findAll().size();

        // Create the DossierMedical
        restDossierMedicalMockMvc.perform(post("/api/dossier-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossierMedical)))
            .andExpect(status().isCreated());

        // Validate the DossierMedical in the database
        List<DossierMedical> dossierMedicalList = dossierMedicalRepository.findAll();
        assertThat(dossierMedicalList).hasSize(databaseSizeBeforeCreate + 1);
        DossierMedical testDossierMedical = dossierMedicalList.get(dossierMedicalList.size() - 1);
        assertThat(testDossierMedical.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDossierMedical.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDossierMedical.getNumFiche()).isEqualTo(DEFAULT_NUM_FICHE);
        assertThat(testDossierMedical.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testDossierMedical.getPoids()).isEqualTo(DEFAULT_POIDS);
        assertThat(testDossierMedical.getTension()).isEqualTo(DEFAULT_TENSION);
        assertThat(testDossierMedical.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testDossierMedical.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testDossierMedical.getResultat()).isEqualTo(DEFAULT_RESULTAT);
    }

    @Test
    @Transactional
    public void createDossierMedicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dossierMedicalRepository.findAll().size();

        // Create the DossierMedical with an existing ID
        dossierMedical.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMedicalMockMvc.perform(post("/api/dossier-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossierMedical)))
            .andExpect(status().isBadRequest());

        // Validate the DossierMedical in the database
        List<DossierMedical> dossierMedicalList = dossierMedicalRepository.findAll();
        assertThat(dossierMedicalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossierMedicalRepository.findAll().size();
        // set the field null
        dossierMedical.setPrenom(null);

        // Create the DossierMedical, which fails.

        restDossierMedicalMockMvc.perform(post("/api/dossier-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossierMedical)))
            .andExpect(status().isBadRequest());

        List<DossierMedical> dossierMedicalList = dossierMedicalRepository.findAll();
        assertThat(dossierMedicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDossierMedicals() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossierMedical.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numFiche").value(hasItem(DEFAULT_NUM_FICHE.toString())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.doubleValue())))
            .andExpect(jsonPath("$.[*].poids").value(hasItem(DEFAULT_POIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].tension").value(hasItem(DEFAULT_TENSION.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())));
    }
    
    @Test
    @Transactional
    public void getDossierMedical() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get the dossierMedical
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals/{id}", dossierMedical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dossierMedical.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.numFiche").value(DEFAULT_NUM_FICHE.toString()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE.doubleValue()))
            .andExpect(jsonPath("$.poids").value(DEFAULT_POIDS.doubleValue()))
            .andExpect(jsonPath("$.tension").value(DEFAULT_TENSION.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT.toString()));
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where nom equals to DEFAULT_NOM
        defaultDossierMedicalShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the dossierMedicalList where nom equals to UPDATED_NOM
        defaultDossierMedicalShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultDossierMedicalShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the dossierMedicalList where nom equals to UPDATED_NOM
        defaultDossierMedicalShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where nom is not null
        defaultDossierMedicalShouldBeFound("nom.specified=true");

        // Get all the dossierMedicalList where nom is null
        defaultDossierMedicalShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where prenom equals to DEFAULT_PRENOM
        defaultDossierMedicalShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the dossierMedicalList where prenom equals to UPDATED_PRENOM
        defaultDossierMedicalShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultDossierMedicalShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the dossierMedicalList where prenom equals to UPDATED_PRENOM
        defaultDossierMedicalShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where prenom is not null
        defaultDossierMedicalShouldBeFound("prenom.specified=true");

        // Get all the dossierMedicalList where prenom is null
        defaultDossierMedicalShouldNotBeFound("prenom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByNumFicheIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where numFiche equals to DEFAULT_NUM_FICHE
        defaultDossierMedicalShouldBeFound("numFiche.equals=" + DEFAULT_NUM_FICHE);

        // Get all the dossierMedicalList where numFiche equals to UPDATED_NUM_FICHE
        defaultDossierMedicalShouldNotBeFound("numFiche.equals=" + UPDATED_NUM_FICHE);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByNumFicheIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where numFiche in DEFAULT_NUM_FICHE or UPDATED_NUM_FICHE
        defaultDossierMedicalShouldBeFound("numFiche.in=" + DEFAULT_NUM_FICHE + "," + UPDATED_NUM_FICHE);

        // Get all the dossierMedicalList where numFiche equals to UPDATED_NUM_FICHE
        defaultDossierMedicalShouldNotBeFound("numFiche.in=" + UPDATED_NUM_FICHE);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByNumFicheIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where numFiche is not null
        defaultDossierMedicalShouldBeFound("numFiche.specified=true");

        // Get all the dossierMedicalList where numFiche is null
        defaultDossierMedicalShouldNotBeFound("numFiche.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTailleIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where taille equals to DEFAULT_TAILLE
        defaultDossierMedicalShouldBeFound("taille.equals=" + DEFAULT_TAILLE);

        // Get all the dossierMedicalList where taille equals to UPDATED_TAILLE
        defaultDossierMedicalShouldNotBeFound("taille.equals=" + UPDATED_TAILLE);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTailleIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where taille in DEFAULT_TAILLE or UPDATED_TAILLE
        defaultDossierMedicalShouldBeFound("taille.in=" + DEFAULT_TAILLE + "," + UPDATED_TAILLE);

        // Get all the dossierMedicalList where taille equals to UPDATED_TAILLE
        defaultDossierMedicalShouldNotBeFound("taille.in=" + UPDATED_TAILLE);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTailleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where taille is not null
        defaultDossierMedicalShouldBeFound("taille.specified=true");

        // Get all the dossierMedicalList where taille is null
        defaultDossierMedicalShouldNotBeFound("taille.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPoidsIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where poids equals to DEFAULT_POIDS
        defaultDossierMedicalShouldBeFound("poids.equals=" + DEFAULT_POIDS);

        // Get all the dossierMedicalList where poids equals to UPDATED_POIDS
        defaultDossierMedicalShouldNotBeFound("poids.equals=" + UPDATED_POIDS);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPoidsIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where poids in DEFAULT_POIDS or UPDATED_POIDS
        defaultDossierMedicalShouldBeFound("poids.in=" + DEFAULT_POIDS + "," + UPDATED_POIDS);

        // Get all the dossierMedicalList where poids equals to UPDATED_POIDS
        defaultDossierMedicalShouldNotBeFound("poids.in=" + UPDATED_POIDS);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPoidsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where poids is not null
        defaultDossierMedicalShouldBeFound("poids.specified=true");

        // Get all the dossierMedicalList where poids is null
        defaultDossierMedicalShouldNotBeFound("poids.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTensionIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where tension equals to DEFAULT_TENSION
        defaultDossierMedicalShouldBeFound("tension.equals=" + DEFAULT_TENSION);

        // Get all the dossierMedicalList where tension equals to UPDATED_TENSION
        defaultDossierMedicalShouldNotBeFound("tension.equals=" + UPDATED_TENSION);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTensionIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where tension in DEFAULT_TENSION or UPDATED_TENSION
        defaultDossierMedicalShouldBeFound("tension.in=" + DEFAULT_TENSION + "," + UPDATED_TENSION);

        // Get all the dossierMedicalList where tension equals to UPDATED_TENSION
        defaultDossierMedicalShouldNotBeFound("tension.in=" + UPDATED_TENSION);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where tension is not null
        defaultDossierMedicalShouldBeFound("tension.specified=true");

        // Get all the dossierMedicalList where tension is null
        defaultDossierMedicalShouldNotBeFound("tension.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where temperature equals to DEFAULT_TEMPERATURE
        defaultDossierMedicalShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the dossierMedicalList where temperature equals to UPDATED_TEMPERATURE
        defaultDossierMedicalShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultDossierMedicalShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the dossierMedicalList where temperature equals to UPDATED_TEMPERATURE
        defaultDossierMedicalShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where temperature is not null
        defaultDossierMedicalShouldBeFound("temperature.specified=true");

        // Get all the dossierMedicalList where temperature is null
        defaultDossierMedicalShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where photo equals to DEFAULT_PHOTO
        defaultDossierMedicalShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the dossierMedicalList where photo equals to UPDATED_PHOTO
        defaultDossierMedicalShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultDossierMedicalShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the dossierMedicalList where photo equals to UPDATED_PHOTO
        defaultDossierMedicalShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where photo is not null
        defaultDossierMedicalShouldBeFound("photo.specified=true");

        // Get all the dossierMedicalList where photo is null
        defaultDossierMedicalShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where resultat equals to DEFAULT_RESULTAT
        defaultDossierMedicalShouldBeFound("resultat.equals=" + DEFAULT_RESULTAT);

        // Get all the dossierMedicalList where resultat equals to UPDATED_RESULTAT
        defaultDossierMedicalShouldNotBeFound("resultat.equals=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByResultatIsInShouldWork() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where resultat in DEFAULT_RESULTAT or UPDATED_RESULTAT
        defaultDossierMedicalShouldBeFound("resultat.in=" + DEFAULT_RESULTAT + "," + UPDATED_RESULTAT);

        // Get all the dossierMedicalList where resultat equals to UPDATED_RESULTAT
        defaultDossierMedicalShouldNotBeFound("resultat.in=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    public void getAllDossierMedicalsByResultatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierMedicalRepository.saveAndFlush(dossierMedical);

        // Get all the dossierMedicalList where resultat is not null
        defaultDossierMedicalShouldBeFound("resultat.specified=true");

        // Get all the dossierMedicalList where resultat is null
        defaultDossierMedicalShouldNotBeFound("resultat.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDossierMedicalShouldBeFound(String filter) throws Exception {
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossierMedical.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numFiche").value(hasItem(DEFAULT_NUM_FICHE.toString())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.doubleValue())))
            .andExpect(jsonPath("$.[*].poids").value(hasItem(DEFAULT_POIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].tension").value(hasItem(DEFAULT_TENSION.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())));

        // Check, that the count call also returns 1
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDossierMedicalShouldNotBeFound(String filter) throws Exception {
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDossierMedical() throws Exception {
        // Get the dossierMedical
        restDossierMedicalMockMvc.perform(get("/api/dossier-medicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDossierMedical() throws Exception {
        // Initialize the database
        dossierMedicalService.save(dossierMedical);

        int databaseSizeBeforeUpdate = dossierMedicalRepository.findAll().size();

        // Update the dossierMedical
        DossierMedical updatedDossierMedical = dossierMedicalRepository.findById(dossierMedical.getId()).get();
        // Disconnect from session so that the updates on updatedDossierMedical are not directly saved in db
        em.detach(updatedDossierMedical);
        updatedDossierMedical
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numFiche(UPDATED_NUM_FICHE)
            .taille(UPDATED_TAILLE)
            .poids(UPDATED_POIDS)
            .tension(UPDATED_TENSION)
            .temperature(UPDATED_TEMPERATURE)
            .photo(UPDATED_PHOTO)
            .resultat(UPDATED_RESULTAT);

        restDossierMedicalMockMvc.perform(put("/api/dossier-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDossierMedical)))
            .andExpect(status().isOk());

        // Validate the DossierMedical in the database
        List<DossierMedical> dossierMedicalList = dossierMedicalRepository.findAll();
        assertThat(dossierMedicalList).hasSize(databaseSizeBeforeUpdate);
        DossierMedical testDossierMedical = dossierMedicalList.get(dossierMedicalList.size() - 1);
        assertThat(testDossierMedical.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDossierMedical.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierMedical.getNumFiche()).isEqualTo(UPDATED_NUM_FICHE);
        assertThat(testDossierMedical.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testDossierMedical.getPoids()).isEqualTo(UPDATED_POIDS);
        assertThat(testDossierMedical.getTension()).isEqualTo(UPDATED_TENSION);
        assertThat(testDossierMedical.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testDossierMedical.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDossierMedical.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    public void updateNonExistingDossierMedical() throws Exception {
        int databaseSizeBeforeUpdate = dossierMedicalRepository.findAll().size();

        // Create the DossierMedical

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMedicalMockMvc.perform(put("/api/dossier-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossierMedical)))
            .andExpect(status().isBadRequest());

        // Validate the DossierMedical in the database
        List<DossierMedical> dossierMedicalList = dossierMedicalRepository.findAll();
        assertThat(dossierMedicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDossierMedical() throws Exception {
        // Initialize the database
        dossierMedicalService.save(dossierMedical);

        int databaseSizeBeforeDelete = dossierMedicalRepository.findAll().size();

        // Get the dossierMedical
        restDossierMedicalMockMvc.perform(delete("/api/dossier-medicals/{id}", dossierMedical.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DossierMedical> dossierMedicalList = dossierMedicalRepository.findAll();
        assertThat(dossierMedicalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DossierMedical.class);
        DossierMedical dossierMedical1 = new DossierMedical();
        dossierMedical1.setId(1L);
        DossierMedical dossierMedical2 = new DossierMedical();
        dossierMedical2.setId(dossierMedical1.getId());
        assertThat(dossierMedical1).isEqualTo(dossierMedical2);
        dossierMedical2.setId(2L);
        assertThat(dossierMedical1).isNotEqualTo(dossierMedical2);
        dossierMedical1.setId(null);
        assertThat(dossierMedical1).isNotEqualTo(dossierMedical2);
    }
}
