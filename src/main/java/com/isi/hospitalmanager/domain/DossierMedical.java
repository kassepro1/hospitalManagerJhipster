package com.isi.hospitalmanager.domain;

public class DossierMedical
{
	private int id;
	private String nom;
	private String prenom;
	private String numFiche;
	private double taille;
	private double poids;
	private String tension;
	private String temperature;
	private String photo;
	private String resultat;
	
	
	public DossierMedical(String nom, String prenom, String numFiche, double taille, double poids, String tension,
			String temperature, String photo, String resultat) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numFiche = numFiche;
		this.taille = taille;
		this.poids = poids;
		this.tension = tension;
		this.temperature = temperature;
		this.photo = photo;
		this.resultat = resultat;
		
		
	}


	public DossierMedical() {
	
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getNumFiche() {
		return numFiche;
	}


	public void setNumFiche(String numFiche) {
		this.numFiche = numFiche;
	}


	public double getTaille() {
		return taille;
	}


	public void setTaille(double taille) {
		this.taille = taille;
	}


	public double getPoids() {
		return poids;
	}


	public void setPoids(double poids) {
		this.poids = poids;
	}


	public String getTension() {
		return tension;
	}


	public void setTension(String tension) {
		this.tension = tension;
	}


	public String getTemperature() {
		return temperature;
	}


	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getResultat() {
		return resultat;
	}


	public void setResultat(String resultat) {
		this.resultat = resultat;
	}


	@Override
	public String toString() {
		return "DossierMedical [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", numFiche=" + numFiche
				+ ", taille=" + taille + ", poids=" + poids + ", tension=" + tension + ", temperature=" + temperature
				+ ", photo=" + photo + ", resultat=" + resultat + "]";
	}
	
	
	
	
	

}
