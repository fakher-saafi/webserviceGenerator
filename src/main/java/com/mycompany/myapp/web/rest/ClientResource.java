package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Webservice;
import com.mycompany.myapp.domain.Webservice_;
import com.mycompany.myapp.repository.WebserviceRepository;
import com.mycompany.myapp.service.mysqldbService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/service")
public class ClientResource {


    private mysqldbService ms;
    private final WebserviceRepository webserviceRepository;

    public ClientResource(mysqldbService ms, WebserviceRepository webserviceRepository) {


        this.ms =ms;
        this.webserviceRepository = webserviceRepository;
    }

    /**
     * GET /users : get all .
     *
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("{id}/{table}")

    public List<Map> getAll(@PathVariable Long id,@PathVariable String table) {
        Optional<Webservice> ws=webserviceRepository.findById(id);
        if (ms.testConnection(ws.get().getDatabasePath(),ws.get().getDbUsername(),ws.get().getDbPass())){
            return ms.findAll(table);

        }
        return null;
    }

    @GetMapping("{id}/{table}/{column}/{columnValue}")
    public List<Map> getBy(@PathVariable Long id,@PathVariable String table,@PathVariable String column,@PathVariable String columnValue) {
        Optional<Webservice> ws=webserviceRepository.findById(id);
        if (ms.testConnection(ws.get().getDatabasePath(),ws.get().getDbUsername(),ws.get().getDbPass())){
            return ms.FindOneBy(table,column,columnValue);

        }
        return null;
    }



    @PostMapping("{id}/{table}")
    public ResponseEntity add(@PathVariable Long id, @PathVariable String table, @RequestBody Map map ){
        //map.forEach((k,v)->System.out.println("Name : " + k + " Type : " + v));
        Optional<Webservice> ws=webserviceRepository.findById(id);
        if (ms.testConnection(ws.get().getDatabasePath(),ws.get().getDbUsername(),ws.get().getDbPass())){
            try {
                ms.Add(map,table);
            } catch (SQLException e) {
                //throw new BadRequestAlertException("A new webservice cannot already have an ID", , "idexists");
                return ResponseEntity.badRequest().body(e);
            }
        }
        return ResponseEntity.ok().build();
    }


}
