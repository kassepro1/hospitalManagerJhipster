package com.isi.hospitalmanager.service;

import com.isi.hospitalmanager.service.dto.EquipementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Equipement.
 */
public interface EquipementService {

    /**
     * Save a equipement.
     *
     * @param equipementDTO the entity to save
     * @return the persisted entity
     */
    EquipementDTO save(EquipementDTO equipementDTO);

    /**
     * Get all the equipements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EquipementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" equipement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EquipementDTO> findOne(Long id);

    /**
     * Delete the "id" equipement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
