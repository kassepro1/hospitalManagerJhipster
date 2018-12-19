package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.FeuilleSurveillance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FeuilleSurveillance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeuilleSurveillanceRepository extends JpaRepository<FeuilleSurveillance, Long> {

}
