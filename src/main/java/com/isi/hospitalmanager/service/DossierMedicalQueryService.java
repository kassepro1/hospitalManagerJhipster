package com.isi.hospitalmanager.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isi.hospitalmanager.domain.DossierMedical;
import com.isi.hospitalmanager.domain.*; // for static metamodels
import com.isi.hospitalmanager.repository.DossierMedicalRepository;
import com.isi.hospitalmanager.service.dto.DossierMedicalCriteria;

/**
 * Service for executing complex queries for DossierMedical entities in the database.
 * The main input is a {@link DossierMedicalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DossierMedical} or a {@link Page} of {@link DossierMedical} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DossierMedicalQueryService extends QueryService<DossierMedical> {

    private final Logger log = LoggerFactory.getLogger(DossierMedicalQueryService.class);

    private final DossierMedicalRepository dossierMedicalRepository;

    public DossierMedicalQueryService(DossierMedicalRepository dossierMedicalRepository) {
        this.dossierMedicalRepository = dossierMedicalRepository;
    }

    /**
     * Return a {@link List} of {@link DossierMedical} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DossierMedical> findByCriteria(DossierMedicalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DossierMedical> specification = createSpecification(criteria);
        return dossierMedicalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DossierMedical} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DossierMedical> findByCriteria(DossierMedicalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DossierMedical> specification = createSpecification(criteria);
        return dossierMedicalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DossierMedicalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DossierMedical> specification = createSpecification(criteria);
        return dossierMedicalRepository.count(specification);
    }

    /**
     * Function to convert DossierMedicalCriteria to a {@link Specification}
     */
    private Specification<DossierMedical> createSpecification(DossierMedicalCriteria criteria) {
        Specification<DossierMedical> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DossierMedical_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), DossierMedical_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), DossierMedical_.prenom));
            }
            if (criteria.getNumFiche() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumFiche(), DossierMedical_.numFiche));
            }
            if (criteria.getTaille() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaille(), DossierMedical_.taille));
            }
            if (criteria.getPoids() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoids(), DossierMedical_.poids));
            }
            if (criteria.getTension() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTension(), DossierMedical_.tension));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemperature(), DossierMedical_.temperature));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), DossierMedical_.photo));
            }
            if (criteria.getResultat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResultat(), DossierMedical_.resultat));
            }
        }
        return specification;
    }
}
