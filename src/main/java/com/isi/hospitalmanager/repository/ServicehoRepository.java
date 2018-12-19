package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.Serviceho;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Serviceho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicehoRepository extends JpaRepository<Serviceho, Long> {

}
