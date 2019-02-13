package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.Chambre;
import com.isi.hospitalmanager.repository.ChambreRepository;
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
 * Test class for the ChambreResource REST controller.
 *
 * @see ChambreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class ChambreResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_SURFACE = "AAAAAAAAAA";
    private static final String UPDATED_SURFACE = "BBBBBBBBBB";

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChambreMockMvc;

    private Chambre chambre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChambreResource chambreResource = new ChambreResource(chambreRepository);
        this.restChambreMockMvc = MockMvcBuilders.standaloneSetup(chambreResource)
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
    public static Chambre createEntity(EntityManager em) {
        Chambre chambre = new Chambre()
            .libelle(DEFAULT_LIBELLE)
            .statut(DEFAULT_STATUT)
            .surface(DEFAULT_SURFACE);
        return chambre;
    }

    @Before
    public void initTest() {
        chambre = createEntity(em);
    }

    @Test
    @Transactional
    public void createChambre() throws Exception {
        int databaseSizeBeforeCreate = chambreRepository.findAll().size();

        // Create the Chambre
        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isCreated());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeCreate + 1);
        Chambre testChambre = chambreList.get(chambreList.size() - 1);
        assertThat(testChambre.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testChambre.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testChambre.getSurface()).isEqualTo(DEFAULT_SURFACE);
    }

    @Test
    @Transactional
    public void createChambreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chambreRepository.findAll().size();

        // Create the Chambre with an existing ID
        chambre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = chambreRepository.findAll().size();
        // set the field null
        chambre.setLibelle(null);

        // Create the Chambre, which fails.

        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = chambreRepository.findAll().size();
        // set the field null
        chambre.setStatut(null);

        // Create the Chambre, which fails.

        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChambres() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        // Get all the chambreList
        restChambreMockMvc.perform(get("/api/chambres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chambre.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.toString())));
    }
    
    @Test
    @Transactional
    public void getChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        // Get the chambre
        restChambreMockMvc.perform(get("/api/chambres/{id}", chambre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chambre.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChambre() throws Exception {
        // Get the chambre
        restChambreMockMvc.perform(get("/api/chambres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        int databaseSizeBeforeUpdate = chambreRepository.findAll().size();

        // Update the chambre
        Chambre updatedChambre = chambreRepository.findById(chambre.getId()).get();
        // Disconnect from session so that the updates on updatedChambre are not directly saved in db
        em.detach(updatedChambre);
        updatedChambre
            .libelle(UPDATED_LIBELLE)
            .statut(UPDATED_STATUT)
            .surface(UPDATED_SURFACE);

        restChambreMockMvc.perform(put("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChambre)))
            .andExpect(status().isOk());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeUpdate);
        Chambre testChambre = chambreList.get(chambreList.size() - 1);
        assertThat(testChambre.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testChambre.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testChambre.getSurface()).isEqualTo(UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void updateNonExistingChambre() throws Exception {
        int databaseSizeBeforeUpdate = chambreRepository.findAll().size();

        // Create the Chambre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChambreMockMvc.perform(put("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        int databaseSizeBeforeDelete = chambreRepository.findAll().size();

        // Get the chambre
        restChambreMockMvc.perform(delete("/api/chambres/{id}", chambre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chambre.class);
        Chambre chambre1 = new Chambre();
        chambre1.setId(1L);
        Chambre chambre2 = new Chambre();
        chambre2.setId(chambre1.getId());
        assertThat(chambre1).isEqualTo(chambre2);
        chambre2.setId(2L);
        assertThat(chambre1).isNotEqualTo(chambre2);
        chambre1.setId(null);
        assertThat(chambre1).isNotEqualTo(chambre2);
    }
}
