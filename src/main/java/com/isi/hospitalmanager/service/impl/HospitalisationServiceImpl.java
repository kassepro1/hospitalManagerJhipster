package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.HospitalisationService;
import com.isi.hospitalmanager.domain.Hospitalisation;
import com.isi.hospitalmanager.repository.HospitalisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Hospitalisation.
 */
@Service
@Transactional
public class HospitalisationServiceImpl implements HospitalisationService {

    private final Logger log = LoggerFactory.getLogger(HospitalisationServiceImpl.class);

    private final HospitalisationRepository hospitalisationRepository;

    public HospitalisationServiceImpl(HospitalisationRepository hospitalisationRepository) {
        this.hospitalisationRepository = hospitalisationRepository;
    }

    /**
     * Save a hospitalisation.
     *
     * @param hospitalisation the entity to save
     * @return the persisted entity
     */
    @Override
    public Hospitalisation save(Hospitalisation hospitalisation) {
        log.debug("Request to save Hospitalisation : {}", hospitalisation);
        return hospitalisationRepository.save(hospitalisation);
    }

    /**
     * Get all the hospitalisations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Hospitalisation> findAll(Pageable pageable) {
        log.debug("Request to get all Hospitalisations");
        return hospitalisationRepository.findAll(pageable);
    }


    /**
     * Get one hospitalisation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Hospitalisation> findOne(Long id) {
        log.debug("Request to get Hospitalisation : {}", id);
        return hospitalisationRepository.findById(id);
    }

    /**
     * Delete the hospitalisation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hospitalisation : {}", id);
        hospitalisationRepository.deleteById(id);
    }
}
