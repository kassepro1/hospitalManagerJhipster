package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.TypeChambre;
import com.isi.hospitalmanager.repository.TypeChambreRepository;
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
 * REST controller for managing TypeChambre.
 */
@RestController
@RequestMapping("/api")
public class TypeChambreResource {

    private final Logger log = LoggerFactory.getLogger(TypeChambreResource.class);

    private static final String ENTITY_NAME = "typeChambre";

    private final TypeChambreRepository typeChambreRepository;

    public TypeChambreResource(TypeChambreRepository typeChambreRepository) {
        this.typeChambreRepository = typeChambreRepository;
    }

    /**
     * POST  /type-chambres : Create a new typeChambre.
     *
     * @param typeChambre the typeChambre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeChambre, or with status 400 (Bad Request) if the typeChambre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-chambres")
    @Timed
    public ResponseEntity<TypeChambre> createTypeChambre(@Valid @RequestBody TypeChambre typeChambre) throws URISyntaxException {
        log.debug("REST request to save TypeChambre : {}", typeChambre);
        if (typeChambre.getId() != null) {
            throw new BadRequestAlertException("A new typeChambre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeChambre result = typeChambreRepository.save(typeChambre);
        return ResponseEntity.created(new URI("/api/type-chambres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-chambres : Updates an existing typeChambre.
     *
     * @param typeChambre the typeChambre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeChambre,
     * or with status 400 (Bad Request) if the typeChambre is not valid,
     * or with status 500 (Internal Server Error) if the typeChambre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-chambres")
    @Timed
    public ResponseEntity<TypeChambre> updateTypeChambre(@Valid @RequestBody TypeChambre typeChambre) throws URISyntaxException {
        log.debug("REST request to update TypeChambre : {}", typeChambre);
        if (typeChambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeChambre result = typeChambreRepository.save(typeChambre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeChambre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-chambres : get all the typeChambres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeChambres in body
     */
    @GetMapping("/type-chambres")
    @Timed
    public List<TypeChambre> getAllTypeChambres() {
        log.debug("REST request to get all TypeChambres");
        return typeChambreRepository.findAll();
    }

    /**
     * GET  /type-chambres/:id : get the "id" typeChambre.
     *
     * @param id the id of the typeChambre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeChambre, or with status 404 (Not Found)
     */
    @GetMapping("/type-chambres/{id}")
    @Timed
    public ResponseEntity<TypeChambre> getTypeChambre(@PathVariable Long id) {
        log.debug("REST request to get TypeChambre : {}", id);
        Optional<TypeChambre> typeChambre = typeChambreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeChambre);
    }

    /**
     * DELETE  /type-chambres/:id : delete the "id" typeChambre.
     *
     * @param id the id of the typeChambre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-chambres/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeChambre(@PathVariable Long id) {
        log.debug("REST request to delete TypeChambre : {}", id);

        typeChambreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
