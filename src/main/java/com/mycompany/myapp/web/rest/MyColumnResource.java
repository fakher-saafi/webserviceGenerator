package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyColumn;
import com.mycompany.myapp.repository.MyColumnRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing MyColumn.
 */
@RestController
@RequestMapping("/api")
public class MyColumnResource {

    private final Logger log = LoggerFactory.getLogger(MyColumnResource.class);

    private static final String ENTITY_NAME = "myColumn";

    private final MyColumnRepository myColumnRepository;

    public MyColumnResource(MyColumnRepository myColumnRepository) {
        this.myColumnRepository = myColumnRepository;
    }

    /**
     * POST  /my-columns : Create a new myColumn.
     *
     * @param myColumn the myColumn to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myColumn, or with status 400 (Bad Request) if the myColumn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-columns")
    @Timed
    public ResponseEntity<MyColumn> createMyColumn(@RequestBody MyColumn myColumn) throws URISyntaxException {
        log.debug("REST request to save MyColumn : {}", myColumn);
        if (myColumn.getId() != null) {
            throw new BadRequestAlertException("A new myColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyColumn result = myColumnRepository.save(myColumn);
        return ResponseEntity.created(new URI("/api/my-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-columns : Updates an existing myColumn.
     *
     * @param myColumn the myColumn to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myColumn,
     * or with status 400 (Bad Request) if the myColumn is not valid,
     * or with status 500 (Internal Server Error) if the myColumn couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-columns")
    @Timed
    public ResponseEntity<MyColumn> updateMyColumn(@RequestBody MyColumn myColumn) throws URISyntaxException {
        log.debug("REST request to update MyColumn : {}", myColumn);
        if (myColumn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyColumn result = myColumnRepository.save(myColumn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myColumn.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-columns : get all the myColumns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myColumns in body
     */
    @GetMapping("/my-columns")
    @Timed
    public List<MyColumn> getAllMyColumns() {
        log.debug("REST request to get all MyColumns");
        return myColumnRepository.findAll();
    }

    /**
     * GET  /my-columns/:id : get the "id" myColumn.
     *
     * @param id the id of the myColumn to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myColumn, or with status 404 (Not Found)
     */
    @GetMapping("/my-columns/{id}")
    @Timed
    public ResponseEntity<MyColumn> getMyColumn(@PathVariable Long id) {
        log.debug("REST request to get MyColumn : {}", id);
        Optional<MyColumn> myColumn = myColumnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(myColumn);
    }

    /**
     * DELETE  /my-columns/:id : delete the "id" myColumn.
     *
     * @param id the id of the myColumn to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-columns/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyColumn(@PathVariable Long id) {
        log.debug("REST request to delete MyColumn : {}", id);

        myColumnRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
