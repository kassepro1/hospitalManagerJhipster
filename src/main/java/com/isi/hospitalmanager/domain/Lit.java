package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Lit.
 */
@Entity
@Table(name = "lit")
public class Lit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "etat")
    private Boolean etat;

    @ManyToOne
    @JsonIgnoreProperties("lits")
    private Hospitalisation hospitalisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Lit numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Lit etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Hospitalisation getHospitalisation() {
        return hospitalisation;
    }

    public Lit hospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
        return this;
    }

    public void setHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
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
        Lit lit = (Lit) o;
        if (lit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lit{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", etat='" + isEtat() + "'" +
            "}";
    }
}
