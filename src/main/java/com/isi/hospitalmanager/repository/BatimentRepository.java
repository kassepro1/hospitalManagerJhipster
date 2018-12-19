package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.Batiment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Batiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatimentRepository extends JpaRepository<Batiment, Long> {

}
