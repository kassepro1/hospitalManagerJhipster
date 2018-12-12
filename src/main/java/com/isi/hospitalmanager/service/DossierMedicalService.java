package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.DossierMedical;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DossierMedical.
 */
public interface DossierMedicalService {

    /**
     * Save a dossierMedical.
     *
     * @param dossierMedical the entity to save
     * @return the persisted entity
     */
    DossierMedical save(DossierMedical dossierMedical);

    /**
     * Get all the dossierMedicals.
     *
     * @return the list of entities
     */
    List<DossierMedical> findAll();


    /**
     * Get the "id" dossierMedical.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DossierMedical> findOne(Long id);

    /**
     * Delete the "id" dossierMedical.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
