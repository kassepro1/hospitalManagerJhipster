package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
public class Service implements Serializable {

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

    @OneToMany(mappedBy = "service")
    private Set<Docteur> serviceDocs = new HashSet<>();
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

    public Service numService(String numService) {
        this.numService = numService;
        return this;
    }

    public void setNumService(String numService) {
        this.numService = numService;
    }

    public String getLibelle() {
        return libelle;
    }

    public Service libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Docteur> getServiceDocs() {
        return serviceDocs;
    }

    public Service serviceDocs(Set<Docteur> docteurs) {
        this.serviceDocs = docteurs;
        return this;
    }

    public Service addServiceDoc(Docteur docteur) {
        this.serviceDocs.add(docteur);
        docteur.setService(this);
        return this;
    }

    public Service removeServiceDoc(Docteur docteur) {
        this.serviceDocs.remove(docteur);
        docteur.setService(null);
        return this;
    }

    public void setServiceDocs(Set<Docteur> docteurs) {
        this.serviceDocs = docteurs;
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
        Service service = (Service) o;
        if (service.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), service.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", numService='" + getNumService() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
