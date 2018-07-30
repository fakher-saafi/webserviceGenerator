package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Webservice;
import com.mycompany.myapp.repository.WebserviceRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

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

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Webservice.
 */
@RestController
@RequestMapping("/api")
public class WebserviceResource {
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(WebserviceResource.class);

    private static final String ENTITY_NAME = "webservice";

    private final WebserviceRepository webserviceRepository;

    public WebserviceResource(UserService userService, WebserviceRepository webserviceRepository) {
        this.userService = userService;
        this.webserviceRepository = webserviceRepository;

    }

    /**
     * POST  /webservices : Create a new webservice.
     *
     * @param webservice the webservice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webservice, or with status 400 (Bad Request) if the webservice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/webservices")
    @Timed
    public ResponseEntity<Webservice> createWebservice(@RequestBody Webservice webservice) throws URISyntaxException {
        log.debug("REST request to save Webservice : {}", webservice);
        if (webservice.getId() != null) {
            throw new BadRequestAlertException("A new webservice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Webservice result = webserviceRepository.save(webservice.user(userService.getUserWithAuthorities().get()));
        return ResponseEntity.created(new URI("/api/webservices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /webservices : Updates an existing webservice.
     *
     * @param webservice the webservice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webservice,
     * or with status 400 (Bad Request) if the webservice is not valid,
     * or with status 500 (Internal Server Error) if the webservice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/webservices")
    @Timed
    public ResponseEntity<Webservice> updateWebservice(@RequestBody Webservice webservice) throws URISyntaxException {
        log.debug("REST request to update Webservice : {}", webservice);
        if (webservice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Webservice result = webserviceRepository.save(webservice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webservice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /webservices : get all the webservices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webservices in body
     */
    @GetMapping("/webservices")
    @Timed
    public ResponseEntity<List<Webservice>> getAllWebservices(Pageable pageable) {
        log.debug("REST request to get a page of Webservices");
        Page<Webservice> page = webserviceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/webservices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /webservices/:id : get the "id" webservice.
     *
     * @param id the id of the webservice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webservice, or with status 404 (Not Found)
     */
    @GetMapping("/webservices/{id}")
    @Timed
    public ResponseEntity<Webservice> getWebservice(@PathVariable Long id) {
        log.debug("REST request to get Webservice : {}", id);
        Optional<Webservice> webservice = webserviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(webservice);
    }

    /**
     * DELETE  /webservices/:id : delete the "id" webservice.
     *
     * @param id the id of the webservice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/webservices/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebservice(@PathVariable Long id) {
        log.debug("REST request to delete Webservice : {}", id);

        webserviceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /webservices/testConnction/:path
     *
     * @return the ResponseEntity with status 200 (OK) and with body the webservice, or with status 404 (Not Found)
     */
    @PostMapping("/webservices/testConnection")
    @Timed
    public ResponseEntity<Void> getConnectionTest(@RequestBody Webservice webservice) {
        log.debug("REST request to get Database connection test : {}", webservice);

        /*******************************/
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query ="show tables";
        String query1 ="SELECT COUNT(*) from company";
        String query2="SELECT table_name\n" +
            "FROM information_schema.tables\n" +
            "WHERE table_type='BASE TABLE'\n" +
            "AND table_schema='public'";
        try {
           // String path1 = "jdbc:mysql://localhost:3306/app3?useUnicode=true&characterEncoding=utf8&useSSL=false";
           // String username1 = "root";
          // String password = "";

            // Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(webservice.getDatabasePath().trim(), webservice.getDbUsername().trim(), webservice.getDbPass());
            if (con != null) { System.out .println("Successfully connected to MySQL database test");

            /***********************************************/
            if (webservice.getDatabaseProduct().toString().equals("MYSQL")){
                stmt = con.prepareStatement(query);
                //Resultset returned by query
                rs = stmt.executeQuery(query);
                while(rs.next()){ String tablename = rs.getString(1); System.out.println("table : " + tablename); }
            }  else if (webservice.getDatabaseProduct().toString().equals("POSTGRESQL")){
                stmt = con.createStatement();
                rs = stmt.executeQuery(query2);
               // while(rs.next()){ int count = rs.getInt(1); System.out.println("count of company : " + count); }
                while(rs.next()){ String tablename = rs.getString(1); System.out.println("table : " + tablename); }
            }

                /******************************************/
                return ResponseEntity.accepted().headers(HeaderUtil.createAlert("Successfully connected to "+webservice.getDatabaseProduct().toString()+" database","")).build();
            }
        } catch (SQLException ex) {
            System.out .println("pathhhhhhh "+webservice.getDatabasePath());
            System.out .println("An error occurred while connecting "+webservice.getDatabaseProduct().toString()+" database"); ex.printStackTrace();
            return ResponseEntity.noContent().headers(HeaderUtil.createAlert("An error occurred while connecting "+webservice.getDatabaseProduct().toString()+" database","")).build();



        }
        /***********************************/
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("","")).build();
    }
}
