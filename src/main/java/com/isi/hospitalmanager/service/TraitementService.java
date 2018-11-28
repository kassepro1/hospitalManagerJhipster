package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.Traitement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Traitement.
 */
public interface TraitementService {

    /**
     * Save a traitement.
     *
     * @param traitement the entity to save
     * @return the persisted entity
     */
    Traitement save(Traitement traitement);

    /**
     * Get all the traitements.
     *
     * @return the list of entities
     */
    List<Traitement> findAll();


    /**
     * Get the "id" traitement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Traitement> findOne(Long id);

    /**
     * Delete the "id" traitement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
