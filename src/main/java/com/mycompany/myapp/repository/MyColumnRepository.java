package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyColumn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MyColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyColumnRepository extends JpaRepository<MyColumn, Long> {

}
