package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Departement.
 */
@Entity
@Table(name = "departement")
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @OneToMany(mappedBy = "departement")
    private Set<Serviceho> services = new HashSet<>();
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

    public Departement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Serviceho> getServices() {
        return services;
    }

    public Departement services(Set<Serviceho> servicehos) {
        this.services = servicehos;
        return this;
    }

    public Departement addService(Serviceho serviceho) {
        this.services.add(serviceho);
        serviceho.setDepartement(this);
        return this;
    }

    public Departement removeService(Serviceho serviceho) {
        this.services.remove(serviceho);
        serviceho.setDepartement(null);
        return this;
    }

    public void setServices(Set<Serviceho> servicehos) {
        this.services = servicehos;
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
        Departement departement = (Departement) o;
        if (departement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Departement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
