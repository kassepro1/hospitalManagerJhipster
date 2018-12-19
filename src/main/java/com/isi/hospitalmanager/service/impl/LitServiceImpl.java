package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.LitService;
import com.isi.hospitalmanager.domain.Lit;
import com.isi.hospitalmanager.repository.LitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Lit.
 */
@Service
@Transactional
public class LitServiceImpl implements LitService {

    private final Logger log = LoggerFactory.getLogger(LitServiceImpl.class);

    private final LitRepository litRepository;

    public LitServiceImpl(LitRepository litRepository) {
        this.litRepository = litRepository;
    }

    /**
     * Save a lit.
     *
     * @param lit the entity to save
     * @return the persisted entity
     */
    @Override
    public Lit save(Lit lit) {
        log.debug("Request to save Lit : {}", lit);
        return litRepository.save(lit);
    }

    /**
     * Get all the lits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Lit> findAll(Pageable pageable) {
        log.debug("Request to get all Lits");
        return litRepository.findAll(pageable);
    }


    /**
     * Get one lit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Lit> findOne(Long id) {
        log.debug("Request to get Lit : {}", id);
        return litRepository.findById(id);
    }

    /**
     * Delete the lit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lit : {}", id);
        litRepository.deleteById(id);
    }
}
