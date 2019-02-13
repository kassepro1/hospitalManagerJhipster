package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TypeChambre.
 */
@Entity
@Table(name = "type_chambre")
public class TypeChambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_inscription", nullable = false)
    private ZonedDateTime dateInscription;

    @OneToMany(mappedBy = "typeChambre")
    private Set<Chambre> chambres = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateInscription() {
        return dateInscription;
    }

    public TypeChambre dateInscription(ZonedDateTime dateInscription) {
        this.dateInscription = dateInscription;
        return this;
    }

    public void setDateInscription(ZonedDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Set<Chambre> getChambres() {
        return chambres;
    }

    public TypeChambre chambres(Set<Chambre> chambres) {
        this.chambres = chambres;
        return this;
    }

    public TypeChambre addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setTypeChambre(this);
        return this;
    }

    public TypeChambre removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setTypeChambre(null);
        return this;
    }

    public void setChambres(Set<Chambre> chambres) {
        this.chambres = chambres;
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
        TypeChambre typeChambre = (TypeChambre) o;
        if (typeChambre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeChambre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeChambre{" +
            "id=" + getId() +
            ", dateInscription='" + getDateInscription() + "'" +
            "}";
    }
}
