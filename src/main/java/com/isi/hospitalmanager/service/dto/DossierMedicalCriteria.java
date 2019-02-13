package com.isi.hospitalmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the DossierMedical entity. This class is used in DossierMedicalResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /dossier-medicals?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DossierMedicalCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter numFiche;

    private DoubleFilter taille;

    private DoubleFilter poids;

    private StringFilter tension;

    private StringFilter temperature;

    private StringFilter photo;

    private StringFilter resultat;

    public DossierMedicalCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getNumFiche() {
        return numFiche;
    }

    public void setNumFiche(StringFilter numFiche) {
        this.numFiche = numFiche;
    }

    public DoubleFilter getTaille() {
        return taille;
    }

    public void setTaille(DoubleFilter taille) {
        this.taille = taille;
    }

    public DoubleFilter getPoids() {
        return poids;
    }

    public void setPoids(DoubleFilter poids) {
        this.poids = poids;
    }

    public StringFilter getTension() {
        return tension;
    }

    public void setTension(StringFilter tension) {
        this.tension = tension;
    }

    public StringFilter getTemperature() {
        return temperature;
    }

    public void setTemperature(StringFilter temperature) {
        this.temperature = temperature;
    }

    public StringFilter getPhoto() {
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
    }

    public StringFilter getResultat() {
        return resultat;
    }

    public void setResultat(StringFilter resultat) {
        this.resultat = resultat;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DossierMedicalCriteria that = (DossierMedicalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(numFiche, that.numFiche) &&
            Objects.equals(taille, that.taille) &&
            Objects.equals(poids, that.poids) &&
            Objects.equals(tension, that.tension) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(resultat, that.resultat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        prenom,
        numFiche,
        taille,
        poids,
        tension,
        temperature,
        photo,
        resultat
        );
    }

    @Override
    public String toString() {
        return "DossierMedicalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (numFiche != null ? "numFiche=" + numFiche + ", " : "") +
                (taille != null ? "taille=" + taille + ", " : "") +
                (poids != null ? "poids=" + poids + ", " : "") +
                (tension != null ? "tension=" + tension + ", " : "") +
                (temperature != null ? "temperature=" + temperature + ", " : "") +
                (photo != null ? "photo=" + photo + ", " : "") +
                (resultat != null ? "resultat=" + resultat + ", " : "") +
            "}";
    }

}
