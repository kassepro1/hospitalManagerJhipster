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
 * A Docteur.
 */
@Entity
@Table(name = "docteur")
public class Docteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "tel", nullable = false)
    private String tel;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "docteur")
    private Set<RendezVous> docteurRvs = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("serviceDocs")
    private Service service;

    @ManyToOne
    @JsonIgnoreProperties("specialiteDocs")
    private Specialite specialite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Docteur code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrenom() {
        return prenom;
    }

    public Docteur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Docteur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return tel;
    }

    public Docteur tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public Docteur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RendezVous> getDocteurRvs() {
        return docteurRvs;
    }

    public Docteur docteurRvs(Set<RendezVous> rendezVous) {
        this.docteurRvs = rendezVous;
        return this;
    }

    public Docteur addDocteurRv(RendezVous rendezVous) {
        this.docteurRvs.add(rendezVous);
        rendezVous.setDocteur(this);
        return this;
    }

    public Docteur removeDocteurRv(RendezVous rendezVous) {
        this.docteurRvs.remove(rendezVous);
        rendezVous.setDocteur(null);
        return this;
    }

    public void setDocteurRvs(Set<RendezVous> rendezVous) {
        this.docteurRvs = rendezVous;
    }

    public Service getService() {
        return service;
    }

    public Docteur service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Docteur specialite(Specialite specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
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
        Docteur docteur = (Docteur) o;
        if (docteur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), docteur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Docteur{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", tel='" + getTel() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
