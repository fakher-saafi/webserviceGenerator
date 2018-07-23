package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Webservice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Webservice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebserviceRepository extends JpaRepository<Webservice, Long> {

    @Query("select webservice from Webservice webservice where webservice.user.login = ?#{principal.username}")
    List<Webservice> findByUserIsCurrentUser(Pageable pageable);

}
