package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.TypeHospitalisation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TypeHospitalisation.
 */
public interface TypeHospitalisationService {

    /**
     * Save a typeHospitalisation.
     *
     * @param typeHospitalisation the entity to save
     * @return the persisted entity
     */
    TypeHospitalisation save(TypeHospitalisation typeHospitalisation);

    /**
     * Get all the typeHospitalisations.
     *
     * @return the list of entities
     */
    List<TypeHospitalisation> findAll();


    /**
     * Get the "id" typeHospitalisation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeHospitalisation> findOne(Long id);

    /**
     * Delete the "id" typeHospitalisation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
