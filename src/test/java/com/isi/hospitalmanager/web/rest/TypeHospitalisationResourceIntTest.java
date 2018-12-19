package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.TypeHospitalisation;
import com.isi.hospitalmanager.repository.TypeHospitalisationRepository;
import com.isi.hospitalmanager.service.TypeHospitalisationService;
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
 * Test class for the TypeHospitalisationResource REST controller.
 *
 * @see TypeHospitalisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class TypeHospitalisationResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeHospitalisationRepository typeHospitalisationRepository;

    @Autowired
    private TypeHospitalisationService typeHospitalisationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeHospitalisationMockMvc;

    private TypeHospitalisation typeHospitalisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeHospitalisationResource typeHospitalisationResource = new TypeHospitalisationResource(typeHospitalisationService);
        this.restTypeHospitalisationMockMvc = MockMvcBuilders.standaloneSetup(typeHospitalisationResource)
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
    public static TypeHospitalisation createEntity(EntityManager em) {
        TypeHospitalisation typeHospitalisation = new TypeHospitalisation()
            .libelle(DEFAULT_LIBELLE);
        return typeHospitalisation;
    }

    @Before
    public void initTest() {
        typeHospitalisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeHospitalisation() throws Exception {
        int databaseSizeBeforeCreate = typeHospitalisationRepository.findAll().size();

        // Create the TypeHospitalisation
        restTypeHospitalisationMockMvc.perform(post("/api/type-hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeHospitalisation)))
            .andExpect(status().isCreated());

        // Validate the TypeHospitalisation in the database
        List<TypeHospitalisation> typeHospitalisationList = typeHospitalisationRepository.findAll();
        assertThat(typeHospitalisationList).hasSize(databaseSizeBeforeCreate + 1);
        TypeHospitalisation testTypeHospitalisation = typeHospitalisationList.get(typeHospitalisationList.size() - 1);
        assertThat(testTypeHospitalisation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeHospitalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeHospitalisationRepository.findAll().size();

        // Create the TypeHospitalisation with an existing ID
        typeHospitalisation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeHospitalisationMockMvc.perform(post("/api/type-hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeHospitalisation)))
            .andExpect(status().isBadRequest());

        // Validate the TypeHospitalisation in the database
        List<TypeHospitalisation> typeHospitalisationList = typeHospitalisationRepository.findAll();
        assertThat(typeHospitalisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeHospitalisations() throws Exception {
        // Initialize the database
        typeHospitalisationRepository.saveAndFlush(typeHospitalisation);

        // Get all the typeHospitalisationList
        restTypeHospitalisationMockMvc.perform(get("/api/type-hospitalisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeHospitalisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeHospitalisation() throws Exception {
        // Initialize the database
        typeHospitalisationRepository.saveAndFlush(typeHospitalisation);

        // Get the typeHospitalisation
        restTypeHospitalisationMockMvc.perform(get("/api/type-hospitalisations/{id}", typeHospitalisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeHospitalisation.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeHospitalisation() throws Exception {
        // Get the typeHospitalisation
        restTypeHospitalisationMockMvc.perform(get("/api/type-hospitalisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeHospitalisation() throws Exception {
        // Initialize the database
        typeHospitalisationService.save(typeHospitalisation);

        int databaseSizeBeforeUpdate = typeHospitalisationRepository.findAll().size();

        // Update the typeHospitalisation
        TypeHospitalisation updatedTypeHospitalisation = typeHospitalisationRepository.findById(typeHospitalisation.getId()).get();
        // Disconnect from session so that the updates on updatedTypeHospitalisation are not directly saved in db
        em.detach(updatedTypeHospitalisation);
        updatedTypeHospitalisation
            .libelle(UPDATED_LIBELLE);

        restTypeHospitalisationMockMvc.perform(put("/api/type-hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeHospitalisation)))
            .andExpect(status().isOk());

        // Validate the TypeHospitalisation in the database
        List<TypeHospitalisation> typeHospitalisationList = typeHospitalisationRepository.findAll();
        assertThat(typeHospitalisationList).hasSize(databaseSizeBeforeUpdate);
        TypeHospitalisation testTypeHospitalisation = typeHospitalisationList.get(typeHospitalisationList.size() - 1);
        assertThat(testTypeHospitalisation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeHospitalisation() throws Exception {
        int databaseSizeBeforeUpdate = typeHospitalisationRepository.findAll().size();

        // Create the TypeHospitalisation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeHospitalisationMockMvc.perform(put("/api/type-hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeHospitalisation)))
            .andExpect(status().isBadRequest());

        // Validate the TypeHospitalisation in the database
        List<TypeHospitalisation> typeHospitalisationList = typeHospitalisationRepository.findAll();
        assertThat(typeHospitalisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeHospitalisation() throws Exception {
        // Initialize the database
        typeHospitalisationService.save(typeHospitalisation);

        int databaseSizeBeforeDelete = typeHospitalisationRepository.findAll().size();

        // Get the typeHospitalisation
        restTypeHospitalisationMockMvc.perform(delete("/api/type-hospitalisations/{id}", typeHospitalisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeHospitalisation> typeHospitalisationList = typeHospitalisationRepository.findAll();
        assertThat(typeHospitalisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeHospitalisation.class);
        TypeHospitalisation typeHospitalisation1 = new TypeHospitalisation();
        typeHospitalisation1.setId(1L);
        TypeHospitalisation typeHospitalisation2 = new TypeHospitalisation();
        typeHospitalisation2.setId(typeHospitalisation1.getId());
        assertThat(typeHospitalisation1).isEqualTo(typeHospitalisation2);
        typeHospitalisation2.setId(2L);
        assertThat(typeHospitalisation1).isNotEqualTo(typeHospitalisation2);
        typeHospitalisation1.setId(null);
        assertThat(typeHospitalisation1).isNotEqualTo(typeHospitalisation2);
    }
}
