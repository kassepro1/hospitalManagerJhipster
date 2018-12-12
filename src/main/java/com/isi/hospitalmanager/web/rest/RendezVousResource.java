package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.RendezVous;
import com.isi.hospitalmanager.repository.RendezVousRepository;
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
 * REST controller for managing RendezVous.
 */
@RestController
@RequestMapping("/api")
public class RendezVousResource {

    private final Logger log = LoggerFactory.getLogger(RendezVousResource.class);

    private static final String ENTITY_NAME = "rendezVous";

    private final RendezVousRepository rendezVousRepository;

    public RendezVousResource(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    /**
     * POST  /rendez-vous : Create a new rendezVous.
     *
     * @param rendezVous the rendezVous to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rendezVous, or with status 400 (Bad Request) if the rendezVous has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rendez-vous")
    @Timed
    public ResponseEntity<RendezVous> createRendezVous(@Valid @RequestBody RendezVous rendezVous) throws URISyntaxException {
        log.debug("REST request to save RendezVous : {}", rendezVous);
        if (rendezVous.getId() != null) {
            throw new BadRequestAlertException("A new rendezVous cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RendezVous result = rendezVousRepository.save(rendezVous);
        return ResponseEntity.created(new URI("/api/rendez-vous/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rendez-vous : Updates an existing rendezVous.
     *
     * @param rendezVous the rendezVous to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rendezVous,
     * or with status 400 (Bad Request) if the rendezVous is not valid,
     * or with status 500 (Internal Server Error) if the rendezVous couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rendez-vous")
    @Timed
    public ResponseEntity<RendezVous> updateRendezVous(@Valid @RequestBody RendezVous rendezVous) throws URISyntaxException {
        log.debug("REST request to update RendezVous : {}", rendezVous);
        if (rendezVous.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RendezVous result = rendezVousRepository.save(rendezVous);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rendezVous.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rendez-vous : get all the rendezVous.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rendezVous in body
     */
    @GetMapping("/rendez-vous")
    @Timed
    public List<RendezVous> getAllRendezVous() {
        log.debug("REST request to get all RendezVous");
        return rendezVousRepository.findAll();
    }

    /**
     * GET  /rendez-vous/:id : get the "id" rendezVous.
     *
     * @param id the id of the rendezVous to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rendezVous, or with status 404 (Not Found)
     */
    @GetMapping("/rendez-vous/{id}")
    @Timed
    public ResponseEntity<RendezVous> getRendezVous(@PathVariable Long id) {
        log.debug("REST request to get RendezVous : {}", id);
        Optional<RendezVous> rendezVous = rendezVousRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rendezVous);
    }

    /**
     * DELETE  /rendez-vous/:id : delete the "id" rendezVous.
     *
     * @param id the id of the rendezVous to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rendez-vous/{id}")
    @Timed
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        log.debug("REST request to delete RendezVous : {}", id);

        rendezVousRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
