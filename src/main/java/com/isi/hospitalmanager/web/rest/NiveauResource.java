package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Niveau;
import com.isi.hospitalmanager.repository.NiveauRepository;
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
 * REST controller for managing Niveau.
 */
@RestController
@RequestMapping("/api")
public class NiveauResource {

    private final Logger log = LoggerFactory.getLogger(NiveauResource.class);

    private static final String ENTITY_NAME = "niveau";

    private final NiveauRepository niveauRepository;

    public NiveauResource(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    /**
     * POST  /niveaus : Create a new niveau.
     *
     * @param niveau the niveau to create
     * @return the ResponseEntity with status 201 (Created) and with body the new niveau, or with status 400 (Bad Request) if the niveau has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/niveaus")
    @Timed
    public ResponseEntity<Niveau> createNiveau(@RequestBody Niveau niveau) throws URISyntaxException {
        log.debug("REST request to save Niveau : {}", niveau);
        if (niveau.getId() != null) {
            throw new BadRequestAlertException("A new niveau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Niveau result = niveauRepository.save(niveau);
        return ResponseEntity.created(new URI("/api/niveaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /niveaus : Updates an existing niveau.
     *
     * @param niveau the niveau to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated niveau,
     * or with status 400 (Bad Request) if the niveau is not valid,
     * or with status 500 (Internal Server Error) if the niveau couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/niveaus")
    @Timed
    public ResponseEntity<Niveau> updateNiveau(@RequestBody Niveau niveau) throws URISyntaxException {
        log.debug("REST request to update Niveau : {}", niveau);
        if (niveau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Niveau result = niveauRepository.save(niveau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, niveau.getId().toString()))
            .body(result);
    }

    /**
     * GET  /niveaus : get all the niveaus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of niveaus in body
     */
    @GetMapping("/niveaus")
    @Timed
    public List<Niveau> getAllNiveaus() {
        log.debug("REST request to get all Niveaus");
        return niveauRepository.findAll();
    }

    /**
     * GET  /niveaus/:id : get the "id" niveau.
     *
     * @param id the id of the niveau to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the niveau, or with status 404 (Not Found)
     */
    @GetMapping("/niveaus/{id}")
    @Timed
    public ResponseEntity<Niveau> getNiveau(@PathVariable Long id) {
        log.debug("REST request to get Niveau : {}", id);
        Optional<Niveau> niveau = niveauRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(niveau);
    }

    /**
     * DELETE  /niveaus/:id : delete the "id" niveau.
     *
     * @param id the id of the niveau to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/niveaus/{id}")
    @Timed
    public ResponseEntity<Void> deleteNiveau(@PathVariable Long id) {
        log.debug("REST request to delete Niveau : {}", id);

        niveauRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
