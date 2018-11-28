package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.Lit;
import com.isi.hospitalmanager.repository.LitRepository;
import com.isi.hospitalmanager.service.LitService;
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
 * Test class for the LitResource REST controller.
 *
 * @see LitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class LitResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    @Autowired
    private LitRepository litRepository;

    @Autowired
    private LitService litService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLitMockMvc;

    private Lit lit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LitResource litResource = new LitResource(litService);
        this.restLitMockMvc = MockMvcBuilders.standaloneSetup(litResource)
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
    public static Lit createEntity(EntityManager em) {
        Lit lit = new Lit()
            .numero(DEFAULT_NUMERO)
            .etat(DEFAULT_ETAT);
        return lit;
    }

    @Before
    public void initTest() {
        lit = createEntity(em);
    }

    @Test
    @Transactional
    public void createLit() throws Exception {
        int databaseSizeBeforeCreate = litRepository.findAll().size();

        // Create the Lit
        restLitMockMvc.perform(post("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lit)))
            .andExpect(status().isCreated());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeCreate + 1);
        Lit testLit = litList.get(litList.size() - 1);
        assertThat(testLit.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLit.isEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createLitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = litRepository.findAll().size();

        // Create the Lit with an existing ID
        lit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLitMockMvc.perform(post("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lit)))
            .andExpect(status().isBadRequest());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLits() throws Exception {
        // Initialize the database
        litRepository.saveAndFlush(lit);

        // Get all the litList
        restLitMockMvc.perform(get("/api/lits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lit.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLit() throws Exception {
        // Initialize the database
        litRepository.saveAndFlush(lit);

        // Get the lit
        restLitMockMvc.perform(get("/api/lits/{id}", lit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lit.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLit() throws Exception {
        // Get the lit
        restLitMockMvc.perform(get("/api/lits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLit() throws Exception {
        // Initialize the database
        litService.save(lit);

        int databaseSizeBeforeUpdate = litRepository.findAll().size();

        // Update the lit
        Lit updatedLit = litRepository.findById(lit.getId()).get();
        // Disconnect from session so that the updates on updatedLit are not directly saved in db
        em.detach(updatedLit);
        updatedLit
            .numero(UPDATED_NUMERO)
            .etat(UPDATED_ETAT);

        restLitMockMvc.perform(put("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLit)))
            .andExpect(status().isOk());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeUpdate);
        Lit testLit = litList.get(litList.size() - 1);
        assertThat(testLit.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLit.isEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingLit() throws Exception {
        int databaseSizeBeforeUpdate = litRepository.findAll().size();

        // Create the Lit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLitMockMvc.perform(put("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lit)))
            .andExpect(status().isBadRequest());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLit() throws Exception {
        // Initialize the database
        litService.save(lit);

        int databaseSizeBeforeDelete = litRepository.findAll().size();

        // Get the lit
        restLitMockMvc.perform(delete("/api/lits/{id}", lit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lit.class);
        Lit lit1 = new Lit();
        lit1.setId(1L);
        Lit lit2 = new Lit();
        lit2.setId(lit1.getId());
        assertThat(lit1).isEqualTo(lit2);
        lit2.setId(2L);
        assertThat(lit1).isNotEqualTo(lit2);
        lit1.setId(null);
        assertThat(lit1).isNotEqualTo(lit2);
    }
}
