package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.FeuilleSurveillance;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing FeuilleSurveillance.
 */
public interface FeuilleSurveillanceService {

    /**
     * Save a feuilleSurveillance.
     *
     * @param feuilleSurveillance the entity to save
     * @return the persisted entity
     */
    FeuilleSurveillance save(FeuilleSurveillance feuilleSurveillance);

    /**
     * Get all the feuilleSurveillances.
     *
     * @return the list of entities
     */
    List<FeuilleSurveillance> findAll();


    /**
     * Get the "id" feuilleSurveillance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FeuilleSurveillance> findOne(Long id);

    /**
     * Delete the "id" feuilleSurveillance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
