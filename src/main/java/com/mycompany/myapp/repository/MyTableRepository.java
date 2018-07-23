package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MyTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyTableRepository extends JpaRepository<MyTable, Long> {

}
