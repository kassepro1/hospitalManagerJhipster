package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.FeuilleSurveillance;
import com.isi.hospitalmanager.service.FeuilleSurveillanceService;
import com.isi.hospitalmanager.web.rest.errors.BadRequestAlertException;
import com.isi.hospitalmanager.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FeuilleSurveillance.
 */
@RestController
@RequestMapping("/api")
public class FeuilleSurveillanceResource {

    private final Logger log = LoggerFactory.getLogger(FeuilleSurveillanceResource.class);

    private static final String ENTITY_NAME = "feuilleSurveillance";

    private final FeuilleSurveillanceService feuilleSurveillanceService;

    public FeuilleSurveillanceResource(FeuilleSurveillanceService feuilleSurveillanceService) {
        this.feuilleSurveillanceService = feuilleSurveillanceService;
    }

    /**
     * POST  /feuille-surveillances : Create a new feuilleSurveillance.
     *
     * @param feuilleSurveillance the feuilleSurveillance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feuilleSurveillance, or with status 400 (Bad Request) if the feuilleSurveillance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feuille-surveillances")
    @Timed
    public ResponseEntity<FeuilleSurveillance> createFeuilleSurveillance(@RequestBody FeuilleSurveillance feuilleSurveillance) throws URISyntaxException {
        log.debug("REST request to save FeuilleSurveillance : {}", feuilleSurveillance);
        if (feuilleSurveillance.getId() != null) {
            throw new BadRequestAlertException("A new feuilleSurveillance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeuilleSurveillance result = feuilleSurveillanceService.save(feuilleSurveillance);
        return ResponseEntity.created(new URI("/api/feuille-surveillances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feuille-surveillances : Updates an existing feuilleSurveillance.
     *
     * @param feuilleSurveillance the feuilleSurveillance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feuilleSurveillance,
     * or with status 400 (Bad Request) if the feuilleSurveillance is not valid,
     * or with status 500 (Internal Server Error) if the feuilleSurveillance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feuille-surveillances")
    @Timed
    public ResponseEntity<FeuilleSurveillance> updateFeuilleSurveillance(@RequestBody FeuilleSurveillance feuilleSurveillance) throws URISyntaxException {
        log.debug("REST request to update FeuilleSurveillance : {}", feuilleSurveillance);
        if (feuilleSurveillance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeuilleSurveillance result = feuilleSurveillanceService.save(feuilleSurveillance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feuilleSurveillance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feuille-surveillances : get all the feuilleSurveillances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feuilleSurveillances in body
     */
    @GetMapping("/feuille-surveillances")
    @Timed
    public List<FeuilleSurveillance> getAllFeuilleSurveillances() {
        log.debug("REST request to get all FeuilleSurveillances");
        return feuilleSurveillanceService.findAll();
    }

    /**
     * GET  /feuille-surveillances/:id : get the "id" feuilleSurveillance.
     *
     * @param id the id of the feuilleSurveillance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feuilleSurveillance, or with status 404 (Not Found)
     */
    @GetMapping("/feuille-surveillances/{id}")
    @Timed
    public ResponseEntity<FeuilleSurveillance> getFeuilleSurveillance(@PathVariable Long id) {
        log.debug("REST request to get FeuilleSurveillance : {}", id);
        Optional<FeuilleSurveillance> feuilleSurveillance = feuilleSurveillanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feuilleSurveillance);
    }

    /**
     * DELETE  /feuille-surveillances/:id : delete the "id" feuilleSurveillance.
     *
     * @param id the id of the feuilleSurveillance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feuille-surveillances/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeuilleSurveillance(@PathVariable Long id) {
        log.debug("REST request to delete FeuilleSurveillance : {}", id);
        feuilleSurveillanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
