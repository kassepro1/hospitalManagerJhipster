package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.Departement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Departement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    Optional<Departement> findOneByNom(String name);

}
