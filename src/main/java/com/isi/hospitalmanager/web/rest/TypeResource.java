package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.service.TypeService;
import com.isi.hospitalmanager.web.rest.errors.BadRequestAlertException;
import com.isi.hospitalmanager.web.rest.util.HeaderUtil;
import com.isi.hospitalmanager.web.rest.util.PaginationUtil;
import com.isi.hospitalmanager.service.dto.TypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Type.
 */
@RestController
@RequestMapping("/api")
public class TypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeResource.class);

    private static final String ENTITY_NAME = "type";

    private final TypeService typeService;

    public TypeResource(TypeService typeService) {
        this.typeService = typeService;
    }

    /**
     * POST  /types : Create a new type.
     *
     * @param typeDTO the typeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDTO, or with status 400 (Bad Request) if the type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/types")
    @Timed
    public ResponseEntity<TypeDTO> createType(@Valid @RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to save Type : {}", typeDTO);
        if (typeDTO.getId() != null) {
            throw new BadRequestAlertException("A new type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity.created(new URI("/api/types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /types : Updates an existing type.
     *
     * @param typeDTO the typeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDTO,
     * or with status 400 (Bad Request) if the typeDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/types")
    @Timed
    public ResponseEntity<TypeDTO> updateType(@Valid @RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to update Type : {}", typeDTO);
        if (typeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /types : get all the types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of types in body
     */
    @GetMapping("/types")
    @Timed
    public ResponseEntity<List<TypeDTO>> getAllTypes(Pageable pageable) {
        log.debug("REST request to get a page of Types");
        Page<TypeDTO> page = typeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /types/:id : get the "id" type.
     *
     * @param id the id of the typeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/types/{id}")
    @Timed
    public ResponseEntity<TypeDTO> getType(@PathVariable Long id) {
        log.debug("REST request to get Type : {}", id);
        Optional<TypeDTO> typeDTO = typeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDTO);
    }

    /**
     * DELETE  /types/:id : delete the "id" type.
     *
     * @param id the id of the typeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/types/{id}")
    @Timed
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        log.debug("REST request to delete Type : {}", id);
        typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
