package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.DossierMedical;
import com.isi.hospitalmanager.service.DossierMedicalService;
import com.isi.hospitalmanager.web.rest.errors.BadRequestAlertException;
import com.isi.hospitalmanager.web.rest.util.HeaderUtil;
import com.isi.hospitalmanager.service.dto.DossierMedicalCriteria;
import com.isi.hospitalmanager.service.DossierMedicalQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DossierMedical.
 */
@RestController
@RequestMapping("/api")
public class DossierMedicalResource {

    private final Logger log = LoggerFactory.getLogger(DossierMedicalResource.class);

    private static final String ENTITY_NAME = "dossierMedical";

    private final DossierMedicalService dossierMedicalService;

    private final DossierMedicalQueryService dossierMedicalQueryService;

    public DossierMedicalResource(DossierMedicalService dossierMedicalService, DossierMedicalQueryService dossierMedicalQueryService) {
        this.dossierMedicalService = dossierMedicalService;
        this.dossierMedicalQueryService = dossierMedicalQueryService;
    }

    /**
     * POST  /dossier-medicals : Create a new dossierMedical.
     *
     * @param dossierMedical the dossierMedical to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dossierMedical, or with status 400 (Bad Request) if the dossierMedical has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dossier-medicals")
    @Timed
    public ResponseEntity<DossierMedical> createDossierMedical(@Valid @RequestBody DossierMedical dossierMedical) throws URISyntaxException {
        log.debug("REST request to save DossierMedical : {}", dossierMedical);
        if (dossierMedical.getId() != null) {
            throw new BadRequestAlertException("A new dossierMedical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DossierMedical result = dossierMedicalService.save(dossierMedical);
        return ResponseEntity.created(new URI("/api/dossier-medicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dossier-medicals : Updates an existing dossierMedical.
     *
     * @param dossierMedical the dossierMedical to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dossierMedical,
     * or with status 400 (Bad Request) if the dossierMedical is not valid,
     * or with status 500 (Internal Server Error) if the dossierMedical couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dossier-medicals")
    @Timed
    public ResponseEntity<DossierMedical> updateDossierMedical(@Valid @RequestBody DossierMedical dossierMedical) throws URISyntaxException {
        log.debug("REST request to update DossierMedical : {}", dossierMedical);
        if (dossierMedical.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DossierMedical result = dossierMedicalService.save(dossierMedical);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dossierMedical.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dossier-medicals : get all the dossierMedicals.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dossierMedicals in body
     */
    @GetMapping("/dossier-medicals")
    @Timed
    public ResponseEntity<List<DossierMedical>> getAllDossierMedicals(DossierMedicalCriteria criteria) {
        log.debug("REST request to get DossierMedicals by criteria: {}", criteria);
        List<DossierMedical> entityList = dossierMedicalQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /dossier-medicals/count : count all the dossierMedicals.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/dossier-medicals/count")
    @Timed
    public ResponseEntity<Long> countDossierMedicals(DossierMedicalCriteria criteria) {
        log.debug("REST request to count DossierMedicals by criteria: {}", criteria);
        return ResponseEntity.ok().body(dossierMedicalQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /dossier-medicals/:id : get the "id" dossierMedical.
     *
     * @param id the id of the dossierMedical to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dossierMedical, or with status 404 (Not Found)
     */
    @GetMapping("/dossier-medicals/{id}")
    @Timed
    public ResponseEntity<DossierMedical> getDossierMedical(@PathVariable Long id) {
        log.debug("REST request to get DossierMedical : {}", id);
        Optional<DossierMedical> dossierMedical = dossierMedicalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dossierMedical);
    }

    /**
     * DELETE  /dossier-medicals/:id : delete the "id" dossierMedical.
     *
     * @param id the id of the dossierMedical to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dossier-medicals/{id}")
    @Timed
    public ResponseEntity<Void> deleteDossierMedical(@PathVariable Long id) {
        log.debug("REST request to delete DossierMedical : {}", id);
        dossierMedicalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
