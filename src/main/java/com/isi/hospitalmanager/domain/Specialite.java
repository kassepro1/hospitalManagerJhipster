package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "num_service", nullable = false)
    private String numService;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "specialite")
    private Set<Docteur> specialiteDocs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumService() {
        return numService;
    }

    public Specialite numService(String numService) {
        this.numService = numService;
        return this;
    }

    public void setNumService(String numService) {
        this.numService = numService;
    }

    public String getLibelle() {
        return libelle;
    }

    public Specialite libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Docteur> getSpecialiteDocs() {
        return specialiteDocs;
    }

    public Specialite specialiteDocs(Set<Docteur> docteurs) {
        this.specialiteDocs = docteurs;
        return this;
    }

    public Specialite addSpecialiteDoc(Docteur docteur) {
        this.specialiteDocs.add(docteur);
        docteur.setSpecialite(this);
        return this;
    }

    public Specialite removeSpecialiteDoc(Docteur docteur) {
        this.specialiteDocs.remove(docteur);
        docteur.setSpecialite(null);
        return this;
    }

    public void setSpecialiteDocs(Set<Docteur> docteurs) {
        this.specialiteDocs = docteurs;
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
        Specialite specialite = (Specialite) o;
        if (specialite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", numService='" + getNumService() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
