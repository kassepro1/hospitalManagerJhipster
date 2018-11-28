package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.TypeHospitalisation;
import com.isi.hospitalmanager.service.TypeHospitalisationService;
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
 * REST controller for managing TypeHospitalisation.
 */
@RestController
@RequestMapping("/api")
public class TypeHospitalisationResource {

    private final Logger log = LoggerFactory.getLogger(TypeHospitalisationResource.class);

    private static final String ENTITY_NAME = "typeHospitalisation";

    private final TypeHospitalisationService typeHospitalisationService;

    public TypeHospitalisationResource(TypeHospitalisationService typeHospitalisationService) {
        this.typeHospitalisationService = typeHospitalisationService;
    }

    /**
     * POST  /type-hospitalisations : Create a new typeHospitalisation.
     *
     * @param typeHospitalisation the typeHospitalisation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeHospitalisation, or with status 400 (Bad Request) if the typeHospitalisation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-hospitalisations")
    @Timed
    public ResponseEntity<TypeHospitalisation> createTypeHospitalisation(@RequestBody TypeHospitalisation typeHospitalisation) throws URISyntaxException {
        log.debug("REST request to save TypeHospitalisation : {}", typeHospitalisation);
        if (typeHospitalisation.getId() != null) {
            throw new BadRequestAlertException("A new typeHospitalisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeHospitalisation result = typeHospitalisationService.save(typeHospitalisation);
        return ResponseEntity.created(new URI("/api/type-hospitalisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-hospitalisations : Updates an existing typeHospitalisation.
     *
     * @param typeHospitalisation the typeHospitalisation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeHospitalisation,
     * or with status 400 (Bad Request) if the typeHospitalisation is not valid,
     * or with status 500 (Internal Server Error) if the typeHospitalisation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-hospitalisations")
    @Timed
    public ResponseEntity<TypeHospitalisation> updateTypeHospitalisation(@RequestBody TypeHospitalisation typeHospitalisation) throws URISyntaxException {
        log.debug("REST request to update TypeHospitalisation : {}", typeHospitalisation);
        if (typeHospitalisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeHospitalisation result = typeHospitalisationService.save(typeHospitalisation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeHospitalisation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-hospitalisations : get all the typeHospitalisations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeHospitalisations in body
     */
    @GetMapping("/type-hospitalisations")
    @Timed
    public List<TypeHospitalisation> getAllTypeHospitalisations() {
        log.debug("REST request to get all TypeHospitalisations");
        return typeHospitalisationService.findAll();
    }

    /**
     * GET  /type-hospitalisations/:id : get the "id" typeHospitalisation.
     *
     * @param id the id of the typeHospitalisation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeHospitalisation, or with status 404 (Not Found)
     */
    @GetMapping("/type-hospitalisations/{id}")
    @Timed
    public ResponseEntity<TypeHospitalisation> getTypeHospitalisation(@PathVariable Long id) {
        log.debug("REST request to get TypeHospitalisation : {}", id);
        Optional<TypeHospitalisation> typeHospitalisation = typeHospitalisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeHospitalisation);
    }

    /**
     * DELETE  /type-hospitalisations/:id : delete the "id" typeHospitalisation.
     *
     * @param id the id of the typeHospitalisation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-hospitalisations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeHospitalisation(@PathVariable Long id) {
        log.debug("REST request to delete TypeHospitalisation : {}", id);
        typeHospitalisationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
