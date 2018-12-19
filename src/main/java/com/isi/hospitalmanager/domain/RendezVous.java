package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RendezVous.
 */
@Entity
@Table(name = "rendez_vous")
public class RendezVous implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "num_rv", nullable = false)
    private String numRv;

    @NotNull
    @Column(name = "date_rv", nullable = false)
    private LocalDate dateRv;

    @ManyToOne
    @JsonIgnoreProperties("patientRvs")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("docteurRvs")
    private Docteur docteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumRv() {
        return numRv;
    }

    public RendezVous numRv(String numRv) {
        this.numRv = numRv;
        return this;
    }

    public void setNumRv(String numRv) {
        this.numRv = numRv;
    }

    public LocalDate getDateRv() {
        return dateRv;
    }

    public RendezVous dateRv(LocalDate dateRv) {
        this.dateRv = dateRv;
        return this;
    }

    public void setDateRv(LocalDate dateRv) {
        this.dateRv = dateRv;
    }

    public Patient getPatient() {
        return patient;
    }

    public RendezVous patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Docteur getDocteur() {
        return docteur;
    }

    public RendezVous docteur(Docteur docteur) {
        this.docteur = docteur;
        return this;
    }

    public void setDocteur(Docteur docteur) {
        this.docteur = docteur;
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
        RendezVous rendezVous = (RendezVous) o;
        if (rendezVous.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rendezVous.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RendezVous{" +
            "id=" + getId() +
            ", numRv='" + getNumRv() + "'" +
            ", dateRv='" + getDateRv() + "'" +
            "}";
    }
}
