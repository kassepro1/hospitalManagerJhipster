package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Traitement.
 */
@Entity
@Table(name = "traitement")
public class Traitement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "observation")
    private String observation;

    @Column(name = "resultat")
    private String resultat;

    @Column(name = "detais_traitement")
    private String detaisTraitement;

    @ManyToOne
    @JsonIgnoreProperties("traitements")
    private Hospitalisation hospitalisation;

    @ManyToOne
    @JsonIgnoreProperties("typeTraitements")
    private TypeTraitement typeTraitement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Traitement date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getObservation() {
        return observation;
    }

    public Traitement observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getResultat() {
        return resultat;
    }

    public Traitement resultat(String resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getDetaisTraitement() {
        return detaisTraitement;
    }

    public Traitement detaisTraitement(String detaisTraitement) {
        this.detaisTraitement = detaisTraitement;
        return this;
    }

    public void setDetaisTraitement(String detaisTraitement) {
        this.detaisTraitement = detaisTraitement;
    }

    public Hospitalisation getHospitalisation() {
        return hospitalisation;
    }

    public Traitement hospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
        return this;
    }

    public void setHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
    }

    public TypeTraitement getTypeTraitement() {
        return typeTraitement;
    }

    public Traitement typeTraitement(TypeTraitement typeTraitement) {
        this.typeTraitement = typeTraitement;
        return this;
    }

    public void setTypeTraitement(TypeTraitement typeTraitement) {
        this.typeTraitement = typeTraitement;
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
        Traitement traitement = (Traitement) o;
        if (traitement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), traitement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Traitement{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", observation='" + getObservation() + "'" +
            ", resultat='" + getResultat() + "'" +
            ", detaisTraitement='" + getDetaisTraitement() + "'" +
            "}";
    }
}
