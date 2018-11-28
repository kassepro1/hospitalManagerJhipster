package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TypeHospitalisation.
 */
@Entity
@Table(name = "type_hospitalisation")
public class TypeHospitalisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "typeHospitalisation")
    private Set<Hospitalisation> hospitalisations = new HashSet<>();
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

    public TypeHospitalisation libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public TypeHospitalisation hospitalisations(Set<Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
        return this;
    }

    public TypeHospitalisation addHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisations.add(hospitalisation);
        hospitalisation.setTypeHospitalisation(this);
        return this;
    }

    public TypeHospitalisation removeHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisations.remove(hospitalisation);
        hospitalisation.setTypeHospitalisation(null);
        return this;
    }

    public void setHospitalisations(Set<Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
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
        TypeHospitalisation typeHospitalisation = (TypeHospitalisation) o;
        if (typeHospitalisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeHospitalisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeHospitalisation{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
