package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.TypeTraitementService;
import com.isi.hospitalmanager.domain.TypeTraitement;
import com.isi.hospitalmanager.repository.TypeTraitementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeTraitement.
 */
@Service
@Transactional
public class TypeTraitementServiceImpl implements TypeTraitementService {

    private final Logger log = LoggerFactory.getLogger(TypeTraitementServiceImpl.class);

    private final TypeTraitementRepository typeTraitementRepository;

    public TypeTraitementServiceImpl(TypeTraitementRepository typeTraitementRepository) {
        this.typeTraitementRepository = typeTraitementRepository;
    }

    /**
     * Save a typeTraitement.
     *
     * @param typeTraitement the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeTraitement save(TypeTraitement typeTraitement) {
        log.debug("Request to save TypeTraitement : {}", typeTraitement);
        return typeTraitementRepository.save(typeTraitement);
    }

    /**
     * Get all the typeTraitements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeTraitement> findAll(Pageable pageable) {
        log.debug("Request to get all TypeTraitements");
        return typeTraitementRepository.findAll(pageable);
    }


    /**
     * Get one typeTraitement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeTraitement> findOne(Long id) {
        log.debug("Request to get TypeTraitement : {}", id);
        return typeTraitementRepository.findById(id);
    }

    /**
     * Delete the typeTraitement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeTraitement : {}", id);
        typeTraitementRepository.deleteById(id);
    }
}
