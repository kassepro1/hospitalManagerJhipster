package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.TraitementService;
import com.isi.hospitalmanager.domain.Traitement;
import com.isi.hospitalmanager.repository.TraitementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Traitement.
 */
@Service
@Transactional
public class TraitementServiceImpl implements TraitementService {

    private final Logger log = LoggerFactory.getLogger(TraitementServiceImpl.class);

    private final TraitementRepository traitementRepository;

    public TraitementServiceImpl(TraitementRepository traitementRepository) {
        this.traitementRepository = traitementRepository;
    }

    /**
     * Save a traitement.
     *
     * @param traitement the entity to save
     * @return the persisted entity
     */
    @Override
    public Traitement save(Traitement traitement) {
        log.debug("Request to save Traitement : {}", traitement);
        return traitementRepository.save(traitement);
    }

    /**
     * Get all the traitements.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Traitement> findAll() {
        log.debug("Request to get all Traitements");
        return traitementRepository.findAll();
    }


    /**
     * Get one traitement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Traitement> findOne(Long id) {
        log.debug("Request to get Traitement : {}", id);
        return traitementRepository.findById(id);
    }

    /**
     * Delete the traitement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Traitement : {}", id);
        traitementRepository.deleteById(id);
    }
}
