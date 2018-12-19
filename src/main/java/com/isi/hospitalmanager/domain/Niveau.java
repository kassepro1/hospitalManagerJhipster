package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Niveau.
 */
@Entity
@Table(name = "niveau")
public class Niveau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "niveau")
    private String niveau;

    @ManyToOne
    @JsonIgnoreProperties("niveaus")
    private Batiment batiment;

    @OneToMany(mappedBy = "niveau")
    private Set<Chambre> chambres = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public Niveau niveau(String niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public Niveau batiment(Batiment batiment) {
        this.batiment = batiment;
        return this;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Set<Chambre> getChambres() {
        return chambres;
    }

    public Niveau chambres(Set<Chambre> chambres) {
        this.chambres = chambres;
        return this;
    }

    public Niveau addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setNiveau(this);
        return this;
    }

    public Niveau removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setNiveau(null);
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
        Niveau niveau = (Niveau) o;
        if (niveau.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), niveau.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Niveau{" +
            "id=" + getId() +
            ", niveau='" + getNiveau() + "'" +
            "}";
    }
}
