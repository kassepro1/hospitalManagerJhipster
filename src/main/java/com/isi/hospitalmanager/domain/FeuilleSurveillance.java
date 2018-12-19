package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FeuilleSurveillance.
 */
@Entity
@Table(name = "feuille_surveillance")
public class FeuilleSurveillance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heure")
    private LocalDate heure;

    @Column(name = "pouls")
    private String pouls;

    @Column(name = "pa")
    private String pa;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "freq_respi")
    private String freqRespi;

    @Column(name = "diurese")
    private String diurese;

    @Column(name = "globe_uterin")
    private String globeUterin;

    @Column(name = "observation")
    private String observation;

    @ManyToOne
    @JsonIgnoreProperties("feuilleSurveillances")
    private Hospitalisation hospitalisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHeure() {
        return heure;
    }

    public FeuilleSurveillance heure(LocalDate heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(LocalDate heure) {
        this.heure = heure;
    }

    public String getPouls() {
        return pouls;
    }

    public FeuilleSurveillance pouls(String pouls) {
        this.pouls = pouls;
        return this;
    }

    public void setPouls(String pouls) {
        this.pouls = pouls;
    }

    public String getPa() {
        return pa;
    }

    public FeuilleSurveillance pa(String pa) {
        this.pa = pa;
        return this;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getTemperature() {
        return temperature;
    }

    public FeuilleSurveillance temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFreqRespi() {
        return freqRespi;
    }

    public FeuilleSurveillance freqRespi(String freqRespi) {
        this.freqRespi = freqRespi;
        return this;
    }

    public void setFreqRespi(String freqRespi) {
        this.freqRespi = freqRespi;
    }

    public String getDiurese() {
        return diurese;
    }

    public FeuilleSurveillance diurese(String diurese) {
        this.diurese = diurese;
        return this;
    }

    public void setDiurese(String diurese) {
        this.diurese = diurese;
    }

    public String getGlobeUterin() {
        return globeUterin;
    }

    public FeuilleSurveillance globeUterin(String globeUterin) {
        this.globeUterin = globeUterin;
        return this;
    }

    public void setGlobeUterin(String globeUterin) {
        this.globeUterin = globeUterin;
    }

    public String getObservation() {
        return observation;
    }

    public FeuilleSurveillance observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Hospitalisation getHospitalisation() {
        return hospitalisation;
    }

    public FeuilleSurveillance hospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
        return this;
    }

    public void setHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
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
        FeuilleSurveillance feuilleSurveillance = (FeuilleSurveillance) o;
        if (feuilleSurveillance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feuilleSurveillance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeuilleSurveillance{" +
            "id=" + getId() +
            ", heure='" + getHeure() + "'" +
            ", pouls='" + getPouls() + "'" +
            ", pa='" + getPa() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", freqRespi='" + getFreqRespi() + "'" +
            ", diurese='" + getDiurese() + "'" +
            ", globeUterin='" + getGlobeUterin() + "'" +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
