package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Batiment;
import com.isi.hospitalmanager.repository.BatimentRepository;
import com.isi.hospitalmanager.web.rest.errors.BadRequestAlertException;
import com.isi.hospitalmanager.web.rest.util.HeaderUtil;
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
 * REST controller for managing Batiment.
 */
@RestController
@RequestMapping("/api")
public class BatimentResource {

    private final Logger log = LoggerFactory.getLogger(BatimentResource.class);

    private static final String ENTITY_NAME = "batiment";

    private final BatimentRepository batimentRepository;

    public BatimentResource(BatimentRepository batimentRepository) {
        this.batimentRepository = batimentRepository;
    }

    /**
     * POST  /batiments : Create a new batiment.
     *
     * @param batiment the batiment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new batiment, or with status 400 (Bad Request) if the batiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/batiments")
    @Timed
    public ResponseEntity<Batiment> createBatiment(@Valid @RequestBody Batiment batiment) throws URISyntaxException {
        log.debug("REST request to save Batiment : {}", batiment);
        if (batiment.getId() != null) {
            throw new BadRequestAlertException("A new batiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Batiment result = batimentRepository.save(batiment);
        return ResponseEntity.created(new URI("/api/batiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /batiments : Updates an existing batiment.
     *
     * @param batiment the batiment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated batiment,
     * or with status 400 (Bad Request) if the batiment is not valid,
     * or with status 500 (Internal Server Error) if the batiment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/batiments")
    @Timed
    public ResponseEntity<Batiment> updateBatiment(@Valid @RequestBody Batiment batiment) throws URISyntaxException {
        log.debug("REST request to update Batiment : {}", batiment);
        if (batiment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Batiment result = batimentRepository.save(batiment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, batiment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /batiments : get all the batiments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of batiments in body
     */
    @GetMapping("/batiments")
    @Timed
    public List<Batiment> getAllBatiments() {
        log.debug("REST request to get all Batiments");
        return batimentRepository.findAll();
    }

    /**
     * GET  /batiments/:id : get the "id" batiment.
     *
     * @param id the id of the batiment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the batiment, or with status 404 (Not Found)
     */
    @GetMapping("/batiments/{id}")
    @Timed
    public ResponseEntity<Batiment> getBatiment(@PathVariable Long id) {
        log.debug("REST request to get Batiment : {}", id);
        Optional<Batiment> batiment = batimentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(batiment);
    }

    /**
     * DELETE  /batiments/:id : delete the "id" batiment.
     *
     * @param id the id of the batiment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/batiments/{id}")
    @Timed
    public ResponseEntity<Void> deleteBatiment(@PathVariable Long id) {
        log.debug("REST request to delete Batiment : {}", id);

        batimentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
