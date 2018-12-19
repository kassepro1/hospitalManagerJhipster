package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.Lit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LitRepository extends JpaRepository<Lit, Long> {

}
