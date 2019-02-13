package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.Chambre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chambre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {

}
