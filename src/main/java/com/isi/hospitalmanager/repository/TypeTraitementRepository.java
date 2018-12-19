package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.TypeTraitement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeTraitement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTraitementRepository extends JpaRepository<TypeTraitement, Long> {

}
