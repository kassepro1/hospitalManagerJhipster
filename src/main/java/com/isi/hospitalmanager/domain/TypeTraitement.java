package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TypeTraitement.
 */
@Entity
@Table(name = "type_traitement")
public class TypeTraitement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "typeTraitement")
    private Set<Traitement> typeTraitements = new HashSet<>();
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

    public TypeTraitement libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Traitement> getTypeTraitements() {
        return typeTraitements;
    }

    public TypeTraitement typeTraitements(Set<Traitement> traitements) {
        this.typeTraitements = traitements;
        return this;
    }

    public TypeTraitement addTypeTraitement(Traitement traitement) {
        this.typeTraitements.add(traitement);
        traitement.setTypeTraitement(this);
        return this;
    }

    public TypeTraitement removeTypeTraitement(Traitement traitement) {
        this.typeTraitements.remove(traitement);
        traitement.setTypeTraitement(null);
        return this;
    }

    public void setTypeTraitements(Set<Traitement> traitements) {
        this.typeTraitements = traitements;
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
        TypeTraitement typeTraitement = (TypeTraitement) o;
        if (typeTraitement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeTraitement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeTraitement{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
