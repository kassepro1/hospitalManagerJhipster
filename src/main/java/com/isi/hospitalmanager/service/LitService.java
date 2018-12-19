package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.Lit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Lit.
 */
public interface LitService {

    /**
     * Save a lit.
     *
     * @param lit the entity to save
     * @return the persisted entity
     */
    Lit save(Lit lit);

    /**
     * Get all the lits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Lit> findAll(Pageable pageable);


    /**
     * Get the "id" lit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Lit> findOne(Long id);

    /**
     * Delete the "id" lit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
