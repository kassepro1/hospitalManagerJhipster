package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.TypeHospitalisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeHospitalisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeHospitalisationRepository extends JpaRepository<TypeHospitalisation, Long> {

}
