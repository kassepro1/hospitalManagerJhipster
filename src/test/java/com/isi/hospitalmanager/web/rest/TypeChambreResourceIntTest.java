package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.TypeChambre;
import com.isi.hospitalmanager.repository.TypeChambreRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.isi.hospitalmanager.web.rest.TestUtil.sameInstant;
import static com.isi.hospitalmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TypeChambreResource REST controller.
 *
 * @see TypeChambreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class TypeChambreResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_INSCRIPTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_INSCRIPTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TypeChambreRepository typeChambreRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeChambreMockMvc;

    private TypeChambre typeChambre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeChambreResource typeChambreResource = new TypeChambreResource(typeChambreRepository);
        this.restTypeChambreMockMvc = MockMvcBuilders.standaloneSetup(typeChambreResource)
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
    public static TypeChambre createEntity(EntityManager em) {
        TypeChambre typeChambre = new TypeChambre()
            .dateInscription(DEFAULT_DATE_INSCRIPTION);
        return typeChambre;
    }

    @Before
    public void initTest() {
        typeChambre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeChambre() throws Exception {
        int databaseSizeBeforeCreate = typeChambreRepository.findAll().size();

        // Create the TypeChambre
        restTypeChambreMockMvc.perform(post("/api/type-chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isCreated());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeCreate + 1);
        TypeChambre testTypeChambre = typeChambreList.get(typeChambreList.size() - 1);
        assertThat(testTypeChambre.getDateInscription()).isEqualTo(DEFAULT_DATE_INSCRIPTION);
    }

    @Test
    @Transactional
    public void createTypeChambreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeChambreRepository.findAll().size();

        // Create the TypeChambre with an existing ID
        typeChambre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeChambreMockMvc.perform(post("/api/type-chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateInscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeChambreRepository.findAll().size();
        // set the field null
        typeChambre.setDateInscription(null);

        // Create the TypeChambre, which fails.

        restTypeChambreMockMvc.perform(post("/api/type-chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isBadRequest());

        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeChambres() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        // Get all the typeChambreList
        restTypeChambreMockMvc.perform(get("/api/type-chambres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeChambre.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateInscription").value(hasItem(sameInstant(DEFAULT_DATE_INSCRIPTION))));
    }
    
    @Test
    @Transactional
    public void getTypeChambre() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        // Get the typeChambre
        restTypeChambreMockMvc.perform(get("/api/type-chambres/{id}", typeChambre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeChambre.getId().intValue()))
            .andExpect(jsonPath("$.dateInscription").value(sameInstant(DEFAULT_DATE_INSCRIPTION)));
    }

    @Test
    @Transactional
    public void getNonExistingTypeChambre() throws Exception {
        // Get the typeChambre
        restTypeChambreMockMvc.perform(get("/api/type-chambres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeChambre() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        int databaseSizeBeforeUpdate = typeChambreRepository.findAll().size();

        // Update the typeChambre
        TypeChambre updatedTypeChambre = typeChambreRepository.findById(typeChambre.getId()).get();
        // Disconnect from session so that the updates on updatedTypeChambre are not directly saved in db
        em.detach(updatedTypeChambre);
        updatedTypeChambre
            .dateInscription(UPDATED_DATE_INSCRIPTION);

        restTypeChambreMockMvc.perform(put("/api/type-chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeChambre)))
            .andExpect(status().isOk());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeUpdate);
        TypeChambre testTypeChambre = typeChambreList.get(typeChambreList.size() - 1);
        assertThat(testTypeChambre.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeChambre() throws Exception {
        int databaseSizeBeforeUpdate = typeChambreRepository.findAll().size();

        // Create the TypeChambre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeChambreMockMvc.perform(put("/api/type-chambres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeChambre() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        int databaseSizeBeforeDelete = typeChambreRepository.findAll().size();

        // Get the typeChambre
        restTypeChambreMockMvc.perform(delete("/api/type-chambres/{id}", typeChambre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeChambre.class);
        TypeChambre typeChambre1 = new TypeChambre();
        typeChambre1.setId(1L);
        TypeChambre typeChambre2 = new TypeChambre();
        typeChambre2.setId(typeChambre1.getId());
        assertThat(typeChambre1).isEqualTo(typeChambre2);
        typeChambre2.setId(2L);
        assertThat(typeChambre1).isNotEqualTo(typeChambre2);
        typeChambre1.setId(null);
        assertThat(typeChambre1).isNotEqualTo(typeChambre2);
    }
}
