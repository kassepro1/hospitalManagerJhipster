package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Lit;
import com.isi.hospitalmanager.service.LitService;
import com.isi.hospitalmanager.web.rest.errors.BadRequestAlertException;
import com.isi.hospitalmanager.web.rest.util.HeaderUtil;
import com.isi.hospitalmanager.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lit.
 */
@RestController
@RequestMapping("/api")
public class LitResource {

    private final Logger log = LoggerFactory.getLogger(LitResource.class);

    private static final String ENTITY_NAME = "lit";

    private final LitService litService;

    public LitResource(LitService litService) {
        this.litService = litService;
    }

    /**
     * POST  /lits : Create a new lit.
     *
     * @param lit the lit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lit, or with status 400 (Bad Request) if the lit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lits")
    @Timed
    public ResponseEntity<Lit> createLit(@RequestBody Lit lit) throws URISyntaxException {
        log.debug("REST request to save Lit : {}", lit);
        if (lit.getId() != null) {
            throw new BadRequestAlertException("A new lit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lit result = litService.save(lit);
        return ResponseEntity.created(new URI("/api/lits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lits : Updates an existing lit.
     *
     * @param lit the lit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lit,
     * or with status 400 (Bad Request) if the lit is not valid,
     * or with status 500 (Internal Server Error) if the lit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lits")
    @Timed
    public ResponseEntity<Lit> updateLit(@RequestBody Lit lit) throws URISyntaxException {
        log.debug("REST request to update Lit : {}", lit);
        if (lit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lit result = litService.save(lit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lits : get all the lits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lits in body
     */
    @GetMapping("/lits")
    @Timed
    public ResponseEntity<List<Lit>> getAllLits(Pageable pageable) {
        log.debug("REST request to get a page of Lits");
        Page<Lit> page = litService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lits");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lits/:id : get the "id" lit.
     *
     * @param id the id of the lit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lit, or with status 404 (Not Found)
     */
    @GetMapping("/lits/{id}")
    @Timed
    public ResponseEntity<Lit> getLit(@PathVariable Long id) {
        log.debug("REST request to get Lit : {}", id);
        Optional<Lit> lit = litService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lit);
    }

    /**
     * DELETE  /lits/:id : delete the "id" lit.
     *
     * @param id the id of the lit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lits/{id}")
    @Timed
    public ResponseEntity<Void> deleteLit(@PathVariable Long id) {
        log.debug("REST request to delete Lit : {}", id);
        litService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
