package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.DossierMedicalService;
import com.isi.hospitalmanager.domain.DossierMedical;
import com.isi.hospitalmanager.repository.DossierMedicalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DossierMedical.
 */
@Service
@Transactional
public class DossierMedicalServiceImpl implements DossierMedicalService {

    private final Logger log = LoggerFactory.getLogger(DossierMedicalServiceImpl.class);

    private final DossierMedicalRepository dossierMedicalRepository;

    public DossierMedicalServiceImpl(DossierMedicalRepository dossierMedicalRepository) {
        this.dossierMedicalRepository = dossierMedicalRepository;
    }

    /**
     * Save a dossierMedical.
     *
     * @param dossierMedical the entity to save
     * @return the persisted entity
     */
    @Override
    public DossierMedical save(DossierMedical dossierMedical) {
        log.debug("Request to save DossierMedical : {}", dossierMedical);
        return dossierMedicalRepository.save(dossierMedical);
    }

    /**
     * Get all the dossierMedicals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DossierMedical> findAll() {
        log.debug("Request to get all DossierMedicals");
        return dossierMedicalRepository.findAll();
    }


    /**
     * Get one dossierMedical by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DossierMedical> findOne(Long id) {
        log.debug("Request to get DossierMedical : {}", id);
        return dossierMedicalRepository.findById(id);
    }

    /**
     * Delete the dossierMedical by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DossierMedical : {}", id);
        dossierMedicalRepository.deleteById(id);
    }
}
