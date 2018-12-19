package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.Docteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Docteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocteurRepository extends JpaRepository<Docteur, Long> {

}
