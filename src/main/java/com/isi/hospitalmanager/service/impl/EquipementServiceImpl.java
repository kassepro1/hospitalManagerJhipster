package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.EquipementService;
import com.isi.hospitalmanager.domain.Equipement;
import com.isi.hospitalmanager.repository.EquipementRepository;
import com.isi.hospitalmanager.service.dto.EquipementDTO;
import com.isi.hospitalmanager.service.mapper.EquipementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Equipement.
 */
@Service
@Transactional
public class EquipementServiceImpl implements EquipementService {

    private final Logger log = LoggerFactory.getLogger(EquipementServiceImpl.class);

    private final EquipementRepository equipementRepository;

    private final EquipementMapper equipementMapper;

    public EquipementServiceImpl(EquipementRepository equipementRepository, EquipementMapper equipementMapper) {
        this.equipementRepository = equipementRepository;
        this.equipementMapper = equipementMapper;
    }

    /**
     * Save a equipement.
     *
     * @param equipementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipementDTO save(EquipementDTO equipementDTO) {
        log.debug("Request to save Equipement : {}", equipementDTO);

        Equipement equipement = equipementMapper.toEntity(equipementDTO);
        equipement = equipementRepository.save(equipement);
        return equipementMapper.toDto(equipement);
    }

    /**
     * Get all the equipements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EquipementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipements");
        return equipementRepository.findAll(pageable)
            .map(equipementMapper::toDto);
    }


    /**
     * Get one equipement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EquipementDTO> findOne(Long id) {
        log.debug("Request to get Equipement : {}", id);
        return equipementRepository.findById(id)
            .map(equipementMapper::toDto);
    }

    /**
     * Delete the equipement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipement : {}", id);
        equipementRepository.deleteById(id);
    }
}
