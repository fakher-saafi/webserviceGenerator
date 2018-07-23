package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyTable;
import com.mycompany.myapp.repository.MyTableRepository;
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
 * REST controller for managing MyTable.
 */
@RestController
@RequestMapping("/api")
public class MyTableResource {

    private final Logger log = LoggerFactory.getLogger(MyTableResource.class);

    private static final String ENTITY_NAME = "myTable";

    private final MyTableRepository myTableRepository;

    public MyTableResource(MyTableRepository myTableRepository) {
        this.myTableRepository = myTableRepository;
    }

    /**
     * POST  /my-tables : Create a new myTable.
     *
     * @param myTable the myTable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myTable, or with status 400 (Bad Request) if the myTable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-tables")
    @Timed
    public ResponseEntity<MyTable> createMyTable(@RequestBody MyTable myTable) throws URISyntaxException {
        log.debug("REST request to save MyTable : {}", myTable);
        if (myTable.getId() != null) {
            throw new BadRequestAlertException("A new myTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyTable result = myTableRepository.save(myTable);
        return ResponseEntity.created(new URI("/api/my-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-tables : Updates an existing myTable.
     *
     * @param myTable the myTable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myTable,
     * or with status 400 (Bad Request) if the myTable is not valid,
     * or with status 500 (Internal Server Error) if the myTable couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-tables")
    @Timed
    public ResponseEntity<MyTable> updateMyTable(@RequestBody MyTable myTable) throws URISyntaxException {
        log.debug("REST request to update MyTable : {}", myTable);
        if (myTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyTable result = myTableRepository.save(myTable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myTable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-tables : get all the myTables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myTables in body
     */
    @GetMapping("/my-tables")
    @Timed
    public List<MyTable> getAllMyTables() {
        log.debug("REST request to get all MyTables");
        return myTableRepository.findAll();
    }

    /**
     * GET  /my-tables/:id : get the "id" myTable.
     *
     * @param id the id of the myTable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myTable, or with status 404 (Not Found)
     */
    @GetMapping("/my-tables/{id}")
    @Timed
    public ResponseEntity<MyTable> getMyTable(@PathVariable Long id) {
        log.debug("REST request to get MyTable : {}", id);
        Optional<MyTable> myTable = myTableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(myTable);
    }

    /**
     * DELETE  /my-tables/:id : delete the "id" myTable.
     *
     * @param id the id of the myTable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-tables/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyTable(@PathVariable Long id) {
        log.debug("REST request to delete MyTable : {}", id);

        myTableRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
