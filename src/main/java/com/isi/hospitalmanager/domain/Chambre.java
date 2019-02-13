package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Chambre.
 */
@Entity
@Table(name = "chambre")
public class Chambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "surface")
    private String surface;

    @ManyToOne
    @JsonIgnoreProperties("chambres")
    private Niveau niveau;

    @ManyToOne
    @JsonIgnoreProperties("chambres")
    private TypeChambre typeChambre;

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

    public Chambre libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getStatut() {
        return statut;
    }

    public Chambre statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getSurface() {
        return surface;
    }

    public Chambre surface(String surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Chambre niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public TypeChambre getTypeChambre() {
        return typeChambre;
    }

    public Chambre typeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
        return this;
    }

    public void setTypeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
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
        Chambre chambre = (Chambre) o;
        if (chambre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chambre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chambre{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", statut='" + getStatut() + "'" +
            ", surface='" + getSurface() + "'" +
            "}";
    }
}
