package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.domain.TypeTraitement;
import com.isi.hospitalmanager.service.TypeTraitementService;
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
 * REST controller for managing TypeTraitement.
 */
@RestController
@RequestMapping("/api")
public class TypeTraitementResource {

    private final Logger log = LoggerFactory.getLogger(TypeTraitementResource.class);

    private static final String ENTITY_NAME = "typeTraitement";

    private final TypeTraitementService typeTraitementService;

    public TypeTraitementResource(TypeTraitementService typeTraitementService) {
        this.typeTraitementService = typeTraitementService;
    }

    /**
     * POST  /type-traitements : Create a new typeTraitement.
     *
     * @param typeTraitement the typeTraitement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeTraitement, or with status 400 (Bad Request) if the typeTraitement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-traitements")
    @Timed
    public ResponseEntity<TypeTraitement> createTypeTraitement(@RequestBody TypeTraitement typeTraitement) throws URISyntaxException {
        log.debug("REST request to save TypeTraitement : {}", typeTraitement);
        if (typeTraitement.getId() != null) {
            throw new BadRequestAlertException("A new typeTraitement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTraitement result = typeTraitementService.save(typeTraitement);
        return ResponseEntity.created(new URI("/api/type-traitements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-traitements : Updates an existing typeTraitement.
     *
     * @param typeTraitement the typeTraitement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeTraitement,
     * or with status 400 (Bad Request) if the typeTraitement is not valid,
     * or with status 500 (Internal Server Error) if the typeTraitement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-traitements")
    @Timed
    public ResponseEntity<TypeTraitement> updateTypeTraitement(@RequestBody TypeTraitement typeTraitement) throws URISyntaxException {
        log.debug("REST request to update TypeTraitement : {}", typeTraitement);
        if (typeTraitement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTraitement result = typeTraitementService.save(typeTraitement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeTraitement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-traitements : get all the typeTraitements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeTraitements in body
     */
    @GetMapping("/type-traitements")
    @Timed
    public ResponseEntity<List<TypeTraitement>> getAllTypeTraitements(Pageable pageable) {
        log.debug("REST request to get a page of TypeTraitements");
        Page<TypeTraitement> page = typeTraitementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-traitements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-traitements/:id : get the "id" typeTraitement.
     *
     * @param id the id of the typeTraitement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeTraitement, or with status 404 (Not Found)
     */
    @GetMapping("/type-traitements/{id}")
    @Timed
    public ResponseEntity<TypeTraitement> getTypeTraitement(@PathVariable Long id) {
        log.debug("REST request to get TypeTraitement : {}", id);
        Optional<TypeTraitement> typeTraitement = typeTraitementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeTraitement);
    }

    /**
     * DELETE  /type-traitements/:id : delete the "id" typeTraitement.
     *
     * @param id the id of the typeTraitement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-traitements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeTraitement(@PathVariable Long id) {
        log.debug("REST request to delete TypeTraitement : {}", id);
        typeTraitementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
