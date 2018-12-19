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
 * A Batiment.
 */
@Entity
@Table(name = "batiment")
public class Batiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @ManyToOne
    @JsonIgnoreProperties("batiments")
    private Serviceho serviceho;

    @OneToMany(mappedBy = "batiment")
    private Set<Niveau> niveaus = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Batiment libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Serviceho getServiceho() {
        return serviceho;
    }

    public Batiment serviceho(Serviceho serviceho) {
        this.serviceho = serviceho;
        return this;
    }

    public void setServiceho(Serviceho serviceho) {
        this.serviceho = serviceho;
    }

    public Set<Niveau> getNiveaus() {
        return niveaus;
    }

    public Batiment niveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
        return this;
    }

    public Batiment addNiveau(Niveau niveau) {
        this.niveaus.add(niveau);
        niveau.setBatiment(this);
        return this;
    }

    public Batiment removeNiveau(Niveau niveau) {
        this.niveaus.remove(niveau);
        niveau.setBatiment(null);
        return this;
    }

    public void setNiveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
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
        Batiment batiment = (Batiment) o;
        if (batiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), batiment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Batiment{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
