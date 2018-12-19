package com.isi.hospitalmanager.service.impl;

import com.isi.hospitalmanager.service.FeuilleSurveillanceService;
import com.isi.hospitalmanager.domain.FeuilleSurveillance;
import com.isi.hospitalmanager.repository.FeuilleSurveillanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing FeuilleSurveillance.
 */
@Service
@Transactional
public class FeuilleSurveillanceServiceImpl implements FeuilleSurveillanceService {

    private final Logger log = LoggerFactory.getLogger(FeuilleSurveillanceServiceImpl.class);

    private final FeuilleSurveillanceRepository feuilleSurveillanceRepository;

    public FeuilleSurveillanceServiceImpl(FeuilleSurveillanceRepository feuilleSurveillanceRepository) {
        this.feuilleSurveillanceRepository = feuilleSurveillanceRepository;
    }

    /**
     * Save a feuilleSurveillance.
     *
     * @param feuilleSurveillance the entity to save
     * @return the persisted entity
     */
    @Override
    public FeuilleSurveillance save(FeuilleSurveillance feuilleSurveillance) {
        log.debug("Request to save FeuilleSurveillance : {}", feuilleSurveillance);
        return feuilleSurveillanceRepository.save(feuilleSurveillance);
    }

    /**
     * Get all the feuilleSurveillances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FeuilleSurveillance> findAll() {
        log.debug("Request to get all FeuilleSurveillances");
        return feuilleSurveillanceRepository.findAll();
    }


    /**
     * Get one feuilleSurveillance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FeuilleSurveillance> findOne(Long id) {
        log.debug("Request to get FeuilleSurveillance : {}", id);
        return feuilleSurveillanceRepository.findById(id);
    }

    /**
     * Delete the feuilleSurveillance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeuilleSurveillance : {}", id);
        feuilleSurveillanceRepository.deleteById(id);
    }
}
