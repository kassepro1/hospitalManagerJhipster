package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Docteur;
import com.isi.hospitalmanager.repository.DocteurRepository;
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
 * REST controller for managing Docteur.
 */
@RestController
@RequestMapping("/api")
public class DocteurResource {

    private final Logger log = LoggerFactory.getLogger(DocteurResource.class);

    private static final String ENTITY_NAME = "docteur";

    private final DocteurRepository docteurRepository;

    public DocteurResource(DocteurRepository docteurRepository) {
        this.docteurRepository = docteurRepository;
    }

    /**
     * POST  /docteurs : Create a new docteur.
     *
     * @param docteur the docteur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new docteur, or with status 400 (Bad Request) if the docteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/docteurs")
    @Timed
    public ResponseEntity<Docteur> createDocteur(@Valid @RequestBody Docteur docteur) throws URISyntaxException {
        log.debug("REST request to save Docteur : {}", docteur);
        if (docteur.getId() != null) {
            throw new BadRequestAlertException("A new docteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Docteur result = docteurRepository.save(docteur);
        return ResponseEntity.created(new URI("/api/docteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /docteurs : Updates an existing docteur.
     *
     * @param docteur the docteur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated docteur,
     * or with status 400 (Bad Request) if the docteur is not valid,
     * or with status 500 (Internal Server Error) if the docteur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/docteurs")
    @Timed
    public ResponseEntity<Docteur> updateDocteur(@Valid @RequestBody Docteur docteur) throws URISyntaxException {
        log.debug("REST request to update Docteur : {}", docteur);
        if (docteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Docteur result = docteurRepository.save(docteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, docteur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /docteurs : get all the docteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of docteurs in body
     */
    @GetMapping("/docteurs")
    @Timed
    public List<Docteur> getAllDocteurs() {
        log.debug("REST request to get all Docteurs");
        return docteurRepository.findAll();
    }

    /**
     * GET  /docteurs/:id : get the "id" docteur.
     *
     * @param id the id of the docteur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the docteur, or with status 404 (Not Found)
     */
    @GetMapping("/docteurs/{id}")
    @Timed
    public ResponseEntity<Docteur> getDocteur(@PathVariable Long id) {
        log.debug("REST request to get Docteur : {}", id);
        Optional<Docteur> docteur = docteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(docteur);
    }

    /**
     * DELETE  /docteurs/:id : delete the "id" docteur.
     *
     * @param id the id of the docteur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/docteurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocteur(@PathVariable Long id) {
        log.debug("REST request to delete Docteur : {}", id);

        docteurRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
