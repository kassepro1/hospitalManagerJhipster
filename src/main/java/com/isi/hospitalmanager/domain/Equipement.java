package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Equipement.
 */
@Entity
@Table(name = "equipement")
public class Equipement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "equipement")
    private Set<Service> services = new HashSet<>();
    @OneToMany(mappedBy = "equipement")
    private Set<Type> types = new HashSet<>();
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

    public Equipement libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Equipement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public Equipement etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Service> getServices() {
        return services;
    }

    public Equipement services(Set<Service> services) {
        this.services = services;
        return this;
    }

    public Equipement addService(Service service) {
        this.services.add(service);
        service.setEquipement(this);
        return this;
    }

    public Equipement removeService(Service service) {
        this.services.remove(service);
        service.setEquipement(null);
        return this;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public Equipement types(Set<Type> types) {
        this.types = types;
        return this;
    }

    public Equipement addType(Type type) {
        this.types.add(type);
        type.setEquipement(this);
        return this;
    }

    public Equipement removeType(Type type) {
        this.types.remove(type);
        type.setEquipement(null);
        return this;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
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
        Equipement equipement = (Equipement) o;
        if (equipement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Equipement{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
