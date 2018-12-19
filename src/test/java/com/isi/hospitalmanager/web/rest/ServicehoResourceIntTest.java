package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.Serviceho;
import com.isi.hospitalmanager.repository.ServicehoRepository;
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
 * Test class for the ServicehoResource REST controller.
 *
 * @see ServicehoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class ServicehoResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ServicehoRepository servicehoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServicehoMockMvc;

    private Serviceho serviceho;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServicehoResource servicehoResource = new ServicehoResource(servicehoRepository);
        this.restServicehoMockMvc = MockMvcBuilders.standaloneSetup(servicehoResource)
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
    public static Serviceho createEntity(EntityManager em) {
        Serviceho serviceho = new Serviceho()
            .nom(DEFAULT_NOM);
        return serviceho;
    }

    @Before
    public void initTest() {
        serviceho = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceho() throws Exception {
        int databaseSizeBeforeCreate = servicehoRepository.findAll().size();

        // Create the Serviceho
        restServicehoMockMvc.perform(post("/api/servicehos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceho)))
            .andExpect(status().isCreated());

        // Validate the Serviceho in the database
        List<Serviceho> servicehoList = servicehoRepository.findAll();
        assertThat(servicehoList).hasSize(databaseSizeBeforeCreate + 1);
        Serviceho testServiceho = servicehoList.get(servicehoList.size() - 1);
        assertThat(testServiceho.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createServicehoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicehoRepository.findAll().size();

        // Create the Serviceho with an existing ID
        serviceho.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicehoMockMvc.perform(post("/api/servicehos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceho)))
            .andExpect(status().isBadRequest());

        // Validate the Serviceho in the database
        List<Serviceho> servicehoList = servicehoRepository.findAll();
        assertThat(servicehoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicehoRepository.findAll().size();
        // set the field null
        serviceho.setNom(null);

        // Create the Serviceho, which fails.

        restServicehoMockMvc.perform(post("/api/servicehos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceho)))
            .andExpect(status().isBadRequest());

        List<Serviceho> servicehoList = servicehoRepository.findAll();
        assertThat(servicehoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicehos() throws Exception {
        // Initialize the database
        servicehoRepository.saveAndFlush(serviceho);

        // Get all the servicehoList
        restServicehoMockMvc.perform(get("/api/servicehos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceho.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceho() throws Exception {
        // Initialize the database
        servicehoRepository.saveAndFlush(serviceho);

        // Get the serviceho
        restServicehoMockMvc.perform(get("/api/servicehos/{id}", serviceho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceho.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceho() throws Exception {
        // Get the serviceho
        restServicehoMockMvc.perform(get("/api/servicehos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceho() throws Exception {
        // Initialize the database
        servicehoRepository.saveAndFlush(serviceho);

        int databaseSizeBeforeUpdate = servicehoRepository.findAll().size();

        // Update the serviceho
        Serviceho updatedServiceho = servicehoRepository.findById(serviceho.getId()).get();
        // Disconnect from session so that the updates on updatedServiceho are not directly saved in db
        em.detach(updatedServiceho);
        updatedServiceho
            .nom(UPDATED_NOM);

        restServicehoMockMvc.perform(put("/api/servicehos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceho)))
            .andExpect(status().isOk());

        // Validate the Serviceho in the database
        List<Serviceho> servicehoList = servicehoRepository.findAll();
        assertThat(servicehoList).hasSize(databaseSizeBeforeUpdate);
        Serviceho testServiceho = servicehoList.get(servicehoList.size() - 1);
        assertThat(testServiceho.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceho() throws Exception {
        int databaseSizeBeforeUpdate = servicehoRepository.findAll().size();

        // Create the Serviceho

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicehoMockMvc.perform(put("/api/servicehos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceho)))
            .andExpect(status().isBadRequest());

        // Validate the Serviceho in the database
        List<Serviceho> servicehoList = servicehoRepository.findAll();
        assertThat(servicehoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceho() throws Exception {
        // Initialize the database
        servicehoRepository.saveAndFlush(serviceho);

        int databaseSizeBeforeDelete = servicehoRepository.findAll().size();

        // Get the serviceho
        restServicehoMockMvc.perform(delete("/api/servicehos/{id}", serviceho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Serviceho> servicehoList = servicehoRepository.findAll();
        assertThat(servicehoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Serviceho.class);
        Serviceho serviceho1 = new Serviceho();
        serviceho1.setId(1L);
        Serviceho serviceho2 = new Serviceho();
        serviceho2.setId(serviceho1.getId());
        assertThat(serviceho1).isEqualTo(serviceho2);
        serviceho2.setId(2L);
        assertThat(serviceho1).isNotEqualTo(serviceho2);
        serviceho1.setId(null);
        assertThat(serviceho1).isNotEqualTo(serviceho2);
    }
}
