package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.Docteur;
import com.isi.hospitalmanager.repository.DocteurRepository;
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
import java.util.List;


import static com.isi.hospitalmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocteurResource REST controller.
 *
 * @see DocteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class DocteurResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private DocteurRepository docteurRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocteurMockMvc;

    private Docteur docteur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocteurResource docteurResource = new DocteurResource(docteurRepository);
        this.restDocteurMockMvc = MockMvcBuilders.standaloneSetup(docteurResource)
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
    public static Docteur createEntity(EntityManager em) {
        Docteur docteur = new Docteur()
            .code(DEFAULT_CODE)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .tel(DEFAULT_TEL)
            .email(DEFAULT_EMAIL);
        return docteur;
    }

    @Before
    public void initTest() {
        docteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocteur() throws Exception {
        int databaseSizeBeforeCreate = docteurRepository.findAll().size();

        // Create the Docteur
        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isCreated());

        // Validate the Docteur in the database
        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeCreate + 1);
        Docteur testDocteur = docteurList.get(docteurList.size() - 1);
        assertThat(testDocteur.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDocteur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDocteur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDocteur.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testDocteur.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createDocteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docteurRepository.findAll().size();

        // Create the Docteur with an existing ID
        docteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        // Validate the Docteur in the database
        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docteurRepository.findAll().size();
        // set the field null
        docteur.setCode(null);

        // Create the Docteur, which fails.

        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = docteurRepository.findAll().size();
        // set the field null
        docteur.setPrenom(null);

        // Create the Docteur, which fails.

        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = docteurRepository.findAll().size();
        // set the field null
        docteur.setNom(null);

        // Create the Docteur, which fails.

        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelIsRequired() throws Exception {
        int databaseSizeBeforeTest = docteurRepository.findAll().size();
        // set the field null
        docteur.setTel(null);

        // Create the Docteur, which fails.

        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = docteurRepository.findAll().size();
        // set the field null
        docteur.setEmail(null);

        // Create the Docteur, which fails.

        restDocteurMockMvc.perform(post("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocteurs() throws Exception {
        // Initialize the database
        docteurRepository.saveAndFlush(docteur);

        // Get all the docteurList
        restDocteurMockMvc.perform(get("/api/docteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getDocteur() throws Exception {
        // Initialize the database
        docteurRepository.saveAndFlush(docteur);

        // Get the docteur
        restDocteurMockMvc.perform(get("/api/docteurs/{id}", docteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docteur.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocteur() throws Exception {
        // Get the docteur
        restDocteurMockMvc.perform(get("/api/docteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocteur() throws Exception {
        // Initialize the database
        docteurRepository.saveAndFlush(docteur);

        int databaseSizeBeforeUpdate = docteurRepository.findAll().size();

        // Update the docteur
        Docteur updatedDocteur = docteurRepository.findById(docteur.getId()).get();
        // Disconnect from session so that the updates on updatedDocteur are not directly saved in db
        em.detach(updatedDocteur);
        updatedDocteur
            .code(UPDATED_CODE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL);

        restDocteurMockMvc.perform(put("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocteur)))
            .andExpect(status().isOk());

        // Validate the Docteur in the database
        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeUpdate);
        Docteur testDocteur = docteurList.get(docteurList.size() - 1);
        assertThat(testDocteur.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDocteur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDocteur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDocteur.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testDocteur.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingDocteur() throws Exception {
        int databaseSizeBeforeUpdate = docteurRepository.findAll().size();

        // Create the Docteur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocteurMockMvc.perform(put("/api/docteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docteur)))
            .andExpect(status().isBadRequest());

        // Validate the Docteur in the database
        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocteur() throws Exception {
        // Initialize the database
        docteurRepository.saveAndFlush(docteur);

        int databaseSizeBeforeDelete = docteurRepository.findAll().size();

        // Get the docteur
        restDocteurMockMvc.perform(delete("/api/docteurs/{id}", docteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Docteur> docteurList = docteurRepository.findAll();
        assertThat(docteurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Docteur.class);
        Docteur docteur1 = new Docteur();
        docteur1.setId(1L);
        Docteur docteur2 = new Docteur();
        docteur2.setId(docteur1.getId());
        assertThat(docteur1).isEqualTo(docteur2);
        docteur2.setId(2L);
        assertThat(docteur1).isNotEqualTo(docteur2);
        docteur1.setId(null);
        assertThat(docteur1).isNotEqualTo(docteur2);
    }
}
