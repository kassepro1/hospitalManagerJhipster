package com.isi.hospitalmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isi.hospitalmanager.domain.DossierMedical;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical, String>
{
	public DossierMedical create();
	public List<DossierMedical> finById();
	

}
