package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Departement;
import com.isi.hospitalmanager.repository.DepartementRepository;
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
 * REST controller for managing Departement.
 */
@RestController
@RequestMapping("/api")
public class DepartementResource {

    private final Logger log = LoggerFactory.getLogger(DepartementResource.class);

    private static final String ENTITY_NAME = "departement";

    private final DepartementRepository departementRepository;

    public DepartementResource(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /**
     * POST  /departements : Create a new departement.
     *
     * @param departement the departement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departement, or with status 400 (Bad Request) if the departement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departements")
    @Timed
    public ResponseEntity<Departement> createDepartement(@Valid @RequestBody Departement departement) throws URISyntaxException {
        log.debug("REST request to save Departement : {}", departement);
        if (departement.getId() != null) {
            throw new BadRequestAlertException("A new departement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departement result = departementRepository.save(departement);
        return ResponseEntity.created(new URI("/api/departements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departements : Updates an existing departement.
     *
     * @param departement the departement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departement,
     * or with status 400 (Bad Request) if the departement is not valid,
     * or with status 500 (Internal Server Error) if the departement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departements")
    @Timed
    public ResponseEntity<Departement> updateDepartement(@Valid @RequestBody Departement departement) throws URISyntaxException {
        log.debug("REST request to update Departement : {}", departement);
        if (departement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Departement result = departementRepository.save(departement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departements : get all the departements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of departements in body
     */
    @GetMapping("/departements")
    @Timed
    public List<Departement> getAllDepartements() {
        log.debug("REST request to get all Departements");
        return departementRepository.findAll();
    }

    /**
     * GET  /departements/:id : get the "id" departement.
     *
     * @param id the id of the departement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departement, or with status 404 (Not Found)
     */
    @GetMapping("/departements/{id}")
    @Timed
    public ResponseEntity<Departement> getDepartement(@PathVariable Long id) {
        log.debug("REST request to get Departement : {}", id);
        Optional<Departement> departement = departementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(departement);
    }

    /**
     * DELETE  /departements/:id : delete the "id" departement.
     *
     * @param id the id of the departement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departements/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        log.debug("REST request to delete Departement : {}", id);

        departementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
