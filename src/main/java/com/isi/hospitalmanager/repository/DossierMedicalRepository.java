package com.isi.hospitalmanager.repository;

import com.isi.hospitalmanager.domain.DossierMedical;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DossierMedical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long>, JpaSpecificationExecutor<DossierMedical> {

}
