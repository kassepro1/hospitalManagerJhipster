package com.isi.hospitalmanager.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DossierMedical.
 */
@Entity
@Table(name = "dossier_medical")
public class DossierMedical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "num_fiche")
    private String numFiche;

    @Column(name = "taille")
    private Double taille;

    @Column(name = "poids")
    private Double poids;

    @Column(name = "tension")
    private String tension;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "photo")
    private String photo;

    @Column(name = "resultat")
    private String resultat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public DossierMedical nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public DossierMedical prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumFiche() {
        return numFiche;
    }

    public DossierMedical numFiche(String numFiche) {
        this.numFiche = numFiche;
        return this;
    }

    public void setNumFiche(String numFiche) {
        this.numFiche = numFiche;
    }

    public Double getTaille() {
        return taille;
    }

    public DossierMedical taille(Double taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(Double taille) {
        this.taille = taille;
    }

    public Double getPoids() {
        return poids;
    }

    public DossierMedical poids(Double poids) {
        this.poids = poids;
        return this;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public String getTension() {
        return tension;
    }

    public DossierMedical tension(String tension) {
        this.tension = tension;
        return this;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getTemperature() {
        return temperature;
    }

    public DossierMedical temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPhoto() {
        return photo;
    }

    public DossierMedical photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getResultat() {
        return resultat;
    }

    public DossierMedical resultat(String resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DossierMedical dossierMedical = (DossierMedical) o;
        if (dossierMedical.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dossierMedical.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DossierMedical{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numFiche='" + getNumFiche() + "'" +
            ", taille=" + getTaille() +
            ", poids=" + getPoids() +
            ", tension='" + getTension() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", resultat='" + getResultat() + "'" +
            "}";
    }
}
