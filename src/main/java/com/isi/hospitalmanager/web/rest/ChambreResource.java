package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.Chambre;
import com.isi.hospitalmanager.repository.ChambreRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Chambre.
 */
@RestController
@RequestMapping("/api")
public class ChambreResource {

    private final Logger log = LoggerFactory.getLogger(ChambreResource.class);

    private static final String ENTITY_NAME = "chambre";

    private final ChambreRepository chambreRepository;

    public ChambreResource(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    /**
     * POST  /chambres : Create a new chambre.
     *
     * @param chambre the chambre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chambre, or with status 400 (Bad Request) if the chambre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chambres")
    @Timed
    public ResponseEntity<Chambre> createChambre(@Valid @RequestBody Chambre chambre) throws URISyntaxException {
        log.debug("REST request to save Chambre : {}", chambre);
        if (chambre.getId() != null) {
            throw new BadRequestAlertException("A new chambre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chambre result = chambreRepository.save(chambre);
        return ResponseEntity.created(new URI("/api/chambres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chambres : Updates an existing chambre.
     *
     * @param chambre the chambre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chambre,
     * or with status 400 (Bad Request) if the chambre is not valid,
     * or with status 500 (Internal Server Error) if the chambre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chambres")
    @Timed
    public ResponseEntity<Chambre> updateChambre(@Valid @RequestBody Chambre chambre) throws URISyntaxException {
        log.debug("REST request to update Chambre : {}", chambre);
        if (chambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chambre result = chambreRepository.save(chambre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chambre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chambres : get all the chambres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chambres in body
     */
    @GetMapping("/chambres")
    @Timed
    public List<Chambre> getAllChambres() {
        log.debug("REST request to get all Chambres");
        return chambreRepository.findAll();
    }

    /**
     * GET  /chambres/:id : get the "id" chambre.
     *
     * @param id the id of the chambre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chambre, or with status 404 (Not Found)
     */
    @GetMapping("/chambres/{id}")
    @Timed
    public ResponseEntity<Chambre> getChambre(@PathVariable Long id) {
        log.debug("REST request to get Chambre : {}", id);
        Optional<Chambre> chambre = chambreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chambre);
    }

    /**
     * DELETE  /chambres/:id : delete the "id" chambre.
     *
     * @param id the id of the chambre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chambres/{id}")
    @Timed
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        log.debug("REST request to delete Chambre : {}", id);

        chambreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/chambres/disponible")
    @Timed
    public List<Chambre> getChambreDisponible(){

        List<Chambre> chambreList = new ArrayList<>();
        for (Chambre c :
            chambreRepository.findAll()) {
            if(c.getStatut().equals("disponible")){
                chambreList.add(c);
            }
        }
        return chambreList;
    }

    @GetMapping("/chambres/non-disponible")
    @Timed
    public List<Chambre> getChambreOccupee(){

        List<Chambre> chambreList = new ArrayList<>();
        for (Chambre c :
            chambreRepository.findAll()) {
            if(c.getStatut().equals("occupee")){
                chambreList.add(c);
            }
        }
        return chambreList;
    }

}
