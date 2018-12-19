package com.isi.hospitalmanager.web.rest;

import com.isi.hospitalmanager.HospitalmanagerApp;

import com.isi.hospitalmanager.domain.Hospitalisation;
import com.isi.hospitalmanager.repository.HospitalisationRepository;
import com.isi.hospitalmanager.service.HospitalisationService;
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
 * Test class for the HospitalisationResource REST controller.
 *
 * @see HospitalisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalmanagerApp.class)
public class HospitalisationResourceIntTest {

    private static final LocalDate DEFAULT_DATE_ENTREE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENTREE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIF_ENTREE = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_ENTREE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SORTIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIF_SORTIE = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_SORTIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_TRANSFERT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_TRANSFERT = "BBBBBBBBBB";

    private static final String DEFAULT_DOSSIER_MEDICAL = "AAAAAAAAAA";
    private static final String UPDATED_DOSSIER_MEDICAL = "BBBBBBBBBB";

    @Autowired
    private HospitalisationRepository hospitalisationRepository;

    @Autowired
    private HospitalisationService hospitalisationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHospitalisationMockMvc;

    private Hospitalisation hospitalisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HospitalisationResource hospitalisationResource = new HospitalisationResource(hospitalisationService);
        this.restHospitalisationMockMvc = MockMvcBuilders.standaloneSetup(hospitalisationResource)
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
    public static Hospitalisation createEntity(EntityManager em) {
        Hospitalisation hospitalisation = new Hospitalisation()
            .dateEntree(DEFAULT_DATE_ENTREE)
            .motifEntree(DEFAULT_MOTIF_ENTREE)
            .dateSortie(DEFAULT_DATE_SORTIE)
            .motifSortie(DEFAULT_MOTIF_SORTIE)
            .dateTransfert(DEFAULT_DATE_TRANSFERT)
            .dossierMedical(DEFAULT_DOSSIER_MEDICAL);
        return hospitalisation;
    }

    @Before
    public void initTest() {
        hospitalisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospitalisation() throws Exception {
        int databaseSizeBeforeCreate = hospitalisationRepository.findAll().size();

        // Create the Hospitalisation
        restHospitalisationMockMvc.perform(post("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospitalisation)))
            .andExpect(status().isCreated());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeCreate + 1);
        Hospitalisation testHospitalisation = hospitalisationList.get(hospitalisationList.size() - 1);
        assertThat(testHospitalisation.getDateEntree()).isEqualTo(DEFAULT_DATE_ENTREE);
        assertThat(testHospitalisation.getMotifEntree()).isEqualTo(DEFAULT_MOTIF_ENTREE);
        assertThat(testHospitalisation.getDateSortie()).isEqualTo(DEFAULT_DATE_SORTIE);
        assertThat(testHospitalisation.getMotifSortie()).isEqualTo(DEFAULT_MOTIF_SORTIE);
        assertThat(testHospitalisation.getDateTransfert()).isEqualTo(DEFAULT_DATE_TRANSFERT);
        assertThat(testHospitalisation.getDossierMedical()).isEqualTo(DEFAULT_DOSSIER_MEDICAL);
    }

    @Test
    @Transactional
    public void createHospitalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalisationRepository.findAll().size();

        // Create the Hospitalisation with an existing ID
        hospitalisation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalisationMockMvc.perform(post("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospitalisation)))
            .andExpect(status().isBadRequest());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHospitalisations() throws Exception {
        // Initialize the database
        hospitalisationRepository.saveAndFlush(hospitalisation);

        // Get all the hospitalisationList
        restHospitalisationMockMvc.perform(get("/api/hospitalisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospitalisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEntree").value(hasItem(DEFAULT_DATE_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].motifEntree").value(hasItem(DEFAULT_MOTIF_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].dateSortie").value(hasItem(DEFAULT_DATE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].motifSortie").value(hasItem(DEFAULT_MOTIF_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].dateTransfert").value(hasItem(DEFAULT_DATE_TRANSFERT.toString())))
            .andExpect(jsonPath("$.[*].dossierMedical").value(hasItem(DEFAULT_DOSSIER_MEDICAL.toString())));
    }
    
    @Test
    @Transactional
    public void getHospitalisation() throws Exception {
        // Initialize the database
        hospitalisationRepository.saveAndFlush(hospitalisation);

        // Get the hospitalisation
        restHospitalisationMockMvc.perform(get("/api/hospitalisations/{id}", hospitalisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hospitalisation.getId().intValue()))
            .andExpect(jsonPath("$.dateEntree").value(DEFAULT_DATE_ENTREE.toString()))
            .andExpect(jsonPath("$.motifEntree").value(DEFAULT_MOTIF_ENTREE.toString()))
            .andExpect(jsonPath("$.dateSortie").value(DEFAULT_DATE_SORTIE.toString()))
            .andExpect(jsonPath("$.motifSortie").value(DEFAULT_MOTIF_SORTIE.toString()))
            .andExpect(jsonPath("$.dateTransfert").value(DEFAULT_DATE_TRANSFERT.toString()))
            .andExpect(jsonPath("$.dossierMedical").value(DEFAULT_DOSSIER_MEDICAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHospitalisation() throws Exception {
        // Get the hospitalisation
        restHospitalisationMockMvc.perform(get("/api/hospitalisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospitalisation() throws Exception {
        // Initialize the database
        hospitalisationService.save(hospitalisation);

        int databaseSizeBeforeUpdate = hospitalisationRepository.findAll().size();

        // Update the hospitalisation
        Hospitalisation updatedHospitalisation = hospitalisationRepository.findById(hospitalisation.getId()).get();
        // Disconnect from session so that the updates on updatedHospitalisation are not directly saved in db
        em.detach(updatedHospitalisation);
        updatedHospitalisation
            .dateEntree(UPDATED_DATE_ENTREE)
            .motifEntree(UPDATED_MOTIF_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .motifSortie(UPDATED_MOTIF_SORTIE)
            .dateTransfert(UPDATED_DATE_TRANSFERT)
            .dossierMedical(UPDATED_DOSSIER_MEDICAL);

        restHospitalisationMockMvc.perform(put("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHospitalisation)))
            .andExpect(status().isOk());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeUpdate);
        Hospitalisation testHospitalisation = hospitalisationList.get(hospitalisationList.size() - 1);
        assertThat(testHospitalisation.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testHospitalisation.getMotifEntree()).isEqualTo(UPDATED_MOTIF_ENTREE);
        assertThat(testHospitalisation.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
        assertThat(testHospitalisation.getMotifSortie()).isEqualTo(UPDATED_MOTIF_SORTIE);
        assertThat(testHospitalisation.getDateTransfert()).isEqualTo(UPDATED_DATE_TRANSFERT);
        assertThat(testHospitalisation.getDossierMedical()).isEqualTo(UPDATED_DOSSIER_MEDICAL);
    }

    @Test
    @Transactional
    public void updateNonExistingHospitalisation() throws Exception {
        int databaseSizeBeforeUpdate = hospitalisationRepository.findAll().size();

        // Create the Hospitalisation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalisationMockMvc.perform(put("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospitalisation)))
            .andExpect(status().isBadRequest());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospitalisation() throws Exception {
        // Initialize the database
        hospitalisationService.save(hospitalisation);

        int databaseSizeBeforeDelete = hospitalisationRepository.findAll().size();

        // Get the hospitalisation
        restHospitalisationMockMvc.perform(delete("/api/hospitalisations/{id}", hospitalisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hospitalisation.class);
        Hospitalisation hospitalisation1 = new Hospitalisation();
        hospitalisation1.setId(1L);
        Hospitalisation hospitalisation2 = new Hospitalisation();
        hospitalisation2.setId(hospitalisation1.getId());
        assertThat(hospitalisation1).isEqualTo(hospitalisation2);
        hospitalisation2.setId(2L);
        assertThat(hospitalisation1).isNotEqualTo(hospitalisation2);
        hospitalisation1.setId(null);
        assertThat(hospitalisation1).isNotEqualTo(hospitalisation2);
    }
}
