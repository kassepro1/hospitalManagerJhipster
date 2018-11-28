package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.TypeHospitalisationService;
import com.isi.hospitalmanager.domain.TypeHospitalisation;
import com.isi.hospitalmanager.repository.TypeHospitalisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing TypeHospitalisation.
 */
@Service
@Transactional
public class TypeHospitalisationServiceImpl implements TypeHospitalisationService {

    private final Logger log = LoggerFactory.getLogger(TypeHospitalisationServiceImpl.class);

    private final TypeHospitalisationRepository typeHospitalisationRepository;

    public TypeHospitalisationServiceImpl(TypeHospitalisationRepository typeHospitalisationRepository) {
        this.typeHospitalisationRepository = typeHospitalisationRepository;
    }

    /**
     * Save a typeHospitalisation.
     *
     * @param typeHospitalisation the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeHospitalisation save(TypeHospitalisation typeHospitalisation) {
        log.debug("Request to save TypeHospitalisation : {}", typeHospitalisation);
        return typeHospitalisationRepository.save(typeHospitalisation);
    }

    /**
     * Get all the typeHospitalisations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeHospitalisation> findAll() {
        log.debug("Request to get all TypeHospitalisations");
        return typeHospitalisationRepository.findAll();
    }


    /**
     * Get one typeHospitalisation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeHospitalisation> findOne(Long id) {
        log.debug("Request to get TypeHospitalisation : {}", id);
        return typeHospitalisationRepository.findById(id);
    }

    /**
     * Delete the typeHospitalisation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeHospitalisation : {}", id);
        typeHospitalisationRepository.deleteById(id);
    }
}
