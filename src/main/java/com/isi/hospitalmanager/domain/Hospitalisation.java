package com.isi.hospitalmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Hospitalisation.
 */
@Entity
@Table(name = "hospitalisation")
public class Hospitalisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_entree")
    private LocalDate dateEntree;

    @Column(name = "motif_entree")
    private String motifEntree;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @Column(name = "motif_sortie")
    private String motifSortie;

    @Column(name = "date_transfert")
    private String dateTransfert;

    @Column(name = "dossier_medical")
    private String dossierMedical;

    @OneToMany(mappedBy = "hospitalisation")
    private Set<FeuilleSurveillance> feuilleSurveillances = new HashSet<>();
    @OneToMany(mappedBy = "hospitalisation")
    private Set<Lit> lits = new HashSet<>();
    @OneToMany(mappedBy = "hospitalisation")
    private Set<Traitement> traitements = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("hospitalisations")
    private TypeHospitalisation typeHospitalisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public Hospitalisation dateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
        return this;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public String getMotifEntree() {
        return motifEntree;
    }

    public Hospitalisation motifEntree(String motifEntree) {
        this.motifEntree = motifEntree;
        return this;
    }

    public void setMotifEntree(String motifEntree) {
        this.motifEntree = motifEntree;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public Hospitalisation dateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
        return this;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getMotifSortie() {
        return motifSortie;
    }

    public Hospitalisation motifSortie(String motifSortie) {
        this.motifSortie = motifSortie;
        return this;
    }

    public void setMotifSortie(String motifSortie) {
        this.motifSortie = motifSortie;
    }

    public String getDateTransfert() {
        return dateTransfert;
    }

    public Hospitalisation dateTransfert(String dateTransfert) {
        this.dateTransfert = dateTransfert;
        return this;
    }

    public void setDateTransfert(String dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public String getDossierMedical() {
        return dossierMedical;
    }

    public Hospitalisation dossierMedical(String dossierMedical) {
        this.dossierMedical = dossierMedical;
        return this;
    }

    public void setDossierMedical(String dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    public Set<FeuilleSurveillance> getFeuilleSurveillances() {
        return feuilleSurveillances;
    }

    public Hospitalisation feuilleSurveillances(Set<FeuilleSurveillance> feuilleSurveillances) {
        this.feuilleSurveillances = feuilleSurveillances;
        return this;
    }

    public Hospitalisation addFeuilleSurveillance(FeuilleSurveillance feuilleSurveillance) {
        this.feuilleSurveillances.add(feuilleSurveillance);
        feuilleSurveillance.setHospitalisation(this);
        return this;
    }

    public Hospitalisation removeFeuilleSurveillance(FeuilleSurveillance feuilleSurveillance) {
        this.feuilleSurveillances.remove(feuilleSurveillance);
        feuilleSurveillance.setHospitalisation(null);
        return this;
    }

    public void setFeuilleSurveillances(Set<FeuilleSurveillance> feuilleSurveillances) {
        this.feuilleSurveillances = feuilleSurveillances;
    }

    public Set<Lit> getLits() {
        return lits;
    }

    public Hospitalisation lits(Set<Lit> lits) {
        this.lits = lits;
        return this;
    }

    public Hospitalisation addLit(Lit lit) {
        this.lits.add(lit);
        lit.setHospitalisation(this);
        return this;
    }

    public Hospitalisation removeLit(Lit lit) {
        this.lits.remove(lit);
        lit.setHospitalisation(null);
        return this;
    }

    public void setLits(Set<Lit> lits) {
        this.lits = lits;
    }

    public Set<Traitement> getTraitements() {
        return traitements;
    }

    public Hospitalisation traitements(Set<Traitement> traitements) {
        this.traitements = traitements;
        return this;
    }

    public Hospitalisation addTraitement(Traitement traitement) {
        this.traitements.add(traitement);
        traitement.setHospitalisation(this);
        return this;
    }

    public Hospitalisation removeTraitement(Traitement traitement) {
        this.traitements.remove(traitement);
        traitement.setHospitalisation(null);
        return this;
    }

    public void setTraitements(Set<Traitement> traitements) {
        this.traitements = traitements;
    }

    public TypeHospitalisation getTypeHospitalisation() {
        return typeHospitalisation;
    }

    public Hospitalisation typeHospitalisation(TypeHospitalisation typeHospitalisation) {
        this.typeHospitalisation = typeHospitalisation;
        return this;
    }

    public void setTypeHospitalisation(TypeHospitalisation typeHospitalisation) {
        this.typeHospitalisation = typeHospitalisation;
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
        Hospitalisation hospitalisation = (Hospitalisation) o;
        if (hospitalisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hospitalisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hospitalisation{" +
            "id=" + getId() +
            ", dateEntree='" + getDateEntree() + "'" +
            ", motifEntree='" + getMotifEntree() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            ", motifSortie='" + getMotifSortie() + "'" +
            ", dateTransfert='" + getDateTransfert() + "'" +
            ", dossierMedical='" + getDossierMedical() + "'" +
            "}";
    }
}
