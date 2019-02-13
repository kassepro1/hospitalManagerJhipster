package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Serviceho;
import com.isi.hospitalmanager.repository.ServicehoRepository;
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
 * REST controller for managing Serviceho.
 */
@RestController
@RequestMapping("/api")
public class ServicehoResource {

    private final Logger log = LoggerFactory.getLogger(ServicehoResource.class);

    private static final String ENTITY_NAME = "serviceho";

    private final ServicehoRepository servicehoRepository;

    public ServicehoResource(ServicehoRepository servicehoRepository) {
        this.servicehoRepository = servicehoRepository;
    }

    /**
     * POST  /servicehos : Create a new serviceho.
     *
     * @param serviceho the serviceho to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceho, or with status 400 (Bad Request) if the serviceho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servicehos")
    @Timed
    public ResponseEntity<Serviceho> createServiceho(@Valid @RequestBody Serviceho serviceho) throws URISyntaxException {
        log.debug("REST request to save Serviceho : {}", serviceho);
        if (serviceho.getId() != null) {
            throw new BadRequestAlertException("A new serviceho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Serviceho result = servicehoRepository.save(serviceho);
        return ResponseEntity.created(new URI("/api/servicehos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicehos : Updates an existing serviceho.
     *
     * @param serviceho the serviceho to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceho,
     * or with status 400 (Bad Request) if the serviceho is not valid,
     * or with status 500 (Internal Server Error) if the serviceho couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servicehos")
    @Timed
    public ResponseEntity<Serviceho> updateServiceho(@Valid @RequestBody Serviceho serviceho) throws URISyntaxException {
        log.debug("REST request to update Serviceho : {}", serviceho);
        if (serviceho.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Serviceho result = servicehoRepository.save(serviceho);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceho.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicehos : get all the servicehos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servicehos in body
     */
    @GetMapping("/servicehos")
    @Timed
    public List<Serviceho> getAllServicehos() {
        log.debug("REST request to get all Servicehos");
        return servicehoRepository.findAll();
    }

    /**
     * GET  /servicehos/:id : get the "id" serviceho.
     *
     * @param id the id of the serviceho to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceho, or with status 404 (Not Found)
     */
    @GetMapping("/servicehos/{id}")
    @Timed
    public ResponseEntity<Serviceho> getServiceho(@PathVariable Long id) {
        log.debug("REST request to get Serviceho : {}", id);
        Optional<Serviceho> serviceho = servicehoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceho);
    }

    /**
     * DELETE  /servicehos/:id : delete the "id" serviceho.
     *
     * @param id the id of the serviceho to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servicehos/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceho(@PathVariable Long id) {
        log.debug("REST request to delete Serviceho : {}", id);

        servicehoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
