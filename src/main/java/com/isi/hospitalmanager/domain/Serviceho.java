package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Serviceho.
 */
@Entity
@Table(name = "serviceho")
public class Serviceho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToOne
    @JsonIgnoreProperties("services")
    private Departement departement;

    @OneToMany(mappedBy = "serviceho")
    private Set<Batiment> batiments = new HashSet<>();
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

    public Serviceho nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Departement getDepartement() {
        return departement;
    }

    public Serviceho departement(Departement departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Set<Batiment> getBatiments() {
        return batiments;
    }

    public Serviceho batiments(Set<Batiment> batiments) {
        this.batiments = batiments;
        return this;
    }

    public Serviceho addBatiment(Batiment batiment) {
        this.batiments.add(batiment);
        batiment.setServiceho(this);
        return this;
    }

    public Serviceho removeBatiment(Batiment batiment) {
        this.batiments.remove(batiment);
        batiment.setServiceho(null);
        return this;
    }

    public void setBatiments(Set<Batiment> batiments) {
        this.batiments = batiments;
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
        Serviceho serviceho = (Serviceho) o;
        if (serviceho.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceho.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Serviceho{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
