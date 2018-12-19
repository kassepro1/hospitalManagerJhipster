package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.TypeTraitement;
import com.isi.hospitalmanager.repository.TypeTraitementRepository;
import com.isi.hospitalmanager.service.TypeTraitementService;
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
 * Test class for the TypeTraitementResource REST controller.
 *
 * @see TypeTraitementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class TypeTraitementResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeTraitementRepository typeTraitementRepository;

    @Autowired
    private TypeTraitementService typeTraitementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeTraitementMockMvc;

    private TypeTraitement typeTraitement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTraitementResource typeTraitementResource = new TypeTraitementResource(typeTraitementService);
        this.restTypeTraitementMockMvc = MockMvcBuilders.standaloneSetup(typeTraitementResource)
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
    public static TypeTraitement createEntity(EntityManager em) {
        TypeTraitement typeTraitement = new TypeTraitement()
            .libelle(DEFAULT_LIBELLE);
        return typeTraitement;
    }

    @Before
    public void initTest() {
        typeTraitement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTraitement() throws Exception {
        int databaseSizeBeforeCreate = typeTraitementRepository.findAll().size();

        // Create the TypeTraitement
        restTypeTraitementMockMvc.perform(post("/api/type-traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTraitement)))
            .andExpect(status().isCreated());

        // Validate the TypeTraitement in the database
        List<TypeTraitement> typeTraitementList = typeTraitementRepository.findAll();
        assertThat(typeTraitementList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTraitement testTypeTraitement = typeTraitementList.get(typeTraitementList.size() - 1);
        assertThat(testTypeTraitement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeTraitementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTraitementRepository.findAll().size();

        // Create the TypeTraitement with an existing ID
        typeTraitement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTraitementMockMvc.perform(post("/api/type-traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTraitement)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTraitement in the database
        List<TypeTraitement> typeTraitementList = typeTraitementRepository.findAll();
        assertThat(typeTraitementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeTraitements() throws Exception {
        // Initialize the database
        typeTraitementRepository.saveAndFlush(typeTraitement);

        // Get all the typeTraitementList
        restTypeTraitementMockMvc.perform(get("/api/type-traitements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTraitement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeTraitement() throws Exception {
        // Initialize the database
        typeTraitementRepository.saveAndFlush(typeTraitement);

        // Get the typeTraitement
        restTypeTraitementMockMvc.perform(get("/api/type-traitements/{id}", typeTraitement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeTraitement.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeTraitement() throws Exception {
        // Get the typeTraitement
        restTypeTraitementMockMvc.perform(get("/api/type-traitements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTraitement() throws Exception {
        // Initialize the database
        typeTraitementService.save(typeTraitement);

        int databaseSizeBeforeUpdate = typeTraitementRepository.findAll().size();

        // Update the typeTraitement
        TypeTraitement updatedTypeTraitement = typeTraitementRepository.findById(typeTraitement.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTraitement are not directly saved in db
        em.detach(updatedTypeTraitement);
        updatedTypeTraitement
            .libelle(UPDATED_LIBELLE);

        restTypeTraitementMockMvc.perform(put("/api/type-traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeTraitement)))
            .andExpect(status().isOk());

        // Validate the TypeTraitement in the database
        List<TypeTraitement> typeTraitementList = typeTraitementRepository.findAll();
        assertThat(typeTraitementList).hasSize(databaseSizeBeforeUpdate);
        TypeTraitement testTypeTraitement = typeTraitementList.get(typeTraitementList.size() - 1);
        assertThat(testTypeTraitement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTraitement() throws Exception {
        int databaseSizeBeforeUpdate = typeTraitementRepository.findAll().size();

        // Create the TypeTraitement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTraitementMockMvc.perform(put("/api/type-traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTraitement)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTraitement in the database
        List<TypeTraitement> typeTraitementList = typeTraitementRepository.findAll();
        assertThat(typeTraitementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeTraitement() throws Exception {
        // Initialize the database
        typeTraitementService.save(typeTraitement);

        int databaseSizeBeforeDelete = typeTraitementRepository.findAll().size();

        // Get the typeTraitement
        restTypeTraitementMockMvc.perform(delete("/api/type-traitements/{id}", typeTraitement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeTraitement> typeTraitementList = typeTraitementRepository.findAll();
        assertThat(typeTraitementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTraitement.class);
        TypeTraitement typeTraitement1 = new TypeTraitement();
        typeTraitement1.setId(1L);
        TypeTraitement typeTraitement2 = new TypeTraitement();
        typeTraitement2.setId(typeTraitement1.getId());
        assertThat(typeTraitement1).isEqualTo(typeTraitement2);
        typeTraitement2.setId(2L);
        assertThat(typeTraitement1).isNotEqualTo(typeTraitement2);
        typeTraitement1.setId(null);
        assertThat(typeTraitement1).isNotEqualTo(typeTraitement2);
    }
}
