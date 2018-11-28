package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.Hospitalisation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Hospitalisation.
 */
public interface HospitalisationService {

    /**
     * Save a hospitalisation.
     *
     * @param hospitalisation the entity to save
     * @return the persisted entity
     */
    Hospitalisation save(Hospitalisation hospitalisation);

    /**
     * Get all the hospitalisations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Hospitalisation> findAll(Pageable pageable);


    /**
     * Get the "id" hospitalisation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Hospitalisation> findOne(Long id);

    /**
     * Delete the "id" hospitalisation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
