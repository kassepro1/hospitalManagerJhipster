package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.domain.TypeTraitement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TypeTraitement.
 */
public interface TypeTraitementService {

    /**
     * Save a typeTraitement.
     *
     * @param typeTraitement the entity to save
     * @return the persisted entity
     */
    TypeTraitement save(TypeTraitement typeTraitement);

    /**
     * Get all the typeTraitements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypeTraitement> findAll(Pageable pageable);


    /**
     * Get the "id" typeTraitement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeTraitement> findOne(Long id);

    /**
     * Delete the "id" typeTraitement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
