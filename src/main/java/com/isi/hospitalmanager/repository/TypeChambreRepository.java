package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.TypeChambre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeChambre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeChambreRepository extends JpaRepository<TypeChambre, Long> {

}
