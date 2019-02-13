package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.TypeService;
import com.isi.hospitalmanager.domain.Type;
import com.isi.hospitalmanager.repository.TypeRepository;
import com.isi.hospitalmanager.service.dto.TypeDTO;
import com.isi.hospitalmanager.service.mapper.TypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Type.
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private final Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);

    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    /**
     * Save a type.
     *
     * @param typeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeDTO save(TypeDTO typeDTO) {
        log.debug("Request to save Type : {}", typeDTO);

        Type type = typeMapper.toEntity(typeDTO);
        type = typeRepository.save(type);
        return typeMapper.toDto(type);
    }

    /**
     * Get all the types.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Types");
        return typeRepository.findAll(pageable)
            .map(typeMapper::toDto);
    }


    /**
     * Get one type by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDTO> findOne(Long id) {
        log.debug("Request to get Type : {}", id);
        return typeRepository.findById(id)
            .map(typeMapper::toDto);
    }

    /**
     * Delete the type by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Type : {}", id);
        typeRepository.deleteById(id);
    }
}
