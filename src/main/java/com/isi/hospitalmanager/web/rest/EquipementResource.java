package com.isi.hospitalmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isi.hospitalmanager.service.EquipementService;
import com.isi.hospitalmanager.web.rest.errors.BadRequestAlertException;
import com.isi.hospitalmanager.web.rest.util.HeaderUtil;
import com.isi.hospitalmanager.web.rest.util.PaginationUtil;
import com.isi.hospitalmanager.service.dto.EquipementDTO;
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
 * REST controller for managing Equipement.
 */
@RestController
@RequestMapping("/api")
public class EquipementResource {

    private final Logger log = LoggerFactory.getLogger(EquipementResource.class);

    private static final String ENTITY_NAME = "equipement";

    private final EquipementService equipementService;

    public EquipementResource(EquipementService equipementService) {
        this.equipementService = equipementService;
    }

    /**
     * POST  /equipements : Create a new equipement.
     *
     * @param equipementDTO the equipementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipementDTO, or with status 400 (Bad Request) if the equipement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equipements")
    @Timed
    public ResponseEntity<EquipementDTO> createEquipement(@Valid @RequestBody EquipementDTO equipementDTO) throws URISyntaxException {
        log.debug("REST request to save Equipement : {}", equipementDTO);
        if (equipementDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipementDTO result = equipementService.save(equipementDTO);
        return ResponseEntity.created(new URI("/api/equipements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equipements : Updates an existing equipement.
     *
     * @param equipementDTO the equipementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipementDTO,
     * or with status 400 (Bad Request) if the equipementDTO is not valid,
     * or with status 500 (Internal Server Error) if the equipementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equipements")
    @Timed
    public ResponseEntity<EquipementDTO> updateEquipement(@Valid @RequestBody EquipementDTO equipementDTO) throws URISyntaxException {
        log.debug("REST request to update Equipement : {}", equipementDTO);
        if (equipementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquipementDTO result = equipementService.save(equipementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equipementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equipements : get all the equipements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of equipements in body
     */
    @GetMapping("/equipements")
    @Timed
    public ResponseEntity<List<EquipementDTO>> getAllEquipements(Pageable pageable) {
        log.debug("REST request to get a page of Equipements");
        Page<EquipementDTO> page = equipementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/equipements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /equipements/:id : get the "id" equipement.
     *
     * @param id the id of the equipementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/equipements/{id}")
    @Timed
    public ResponseEntity<EquipementDTO> getEquipement(@PathVariable Long id) {
        log.debug("REST request to get Equipement : {}", id);
        Optional<EquipementDTO> equipementDTO = equipementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipementDTO);
    }

    /**
     * DELETE  /equipements/:id : delete the "id" equipement.
     *
     * @param id the id of the equipementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquipement(@PathVariable Long id) {
        log.debug("REST request to delete Equipement : {}", id);
        equipementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
