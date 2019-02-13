package com.isi.hospitalmanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Service entity.
 */
public class ServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String numService;

    @NotNull
    private String libelle;

    private Long equipementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumService() {
        return numService;
    }

    public void setNumService(String numService) {
        this.numService = numService;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getEquipementId() {
        return equipementId;
    }

    public void setEquipementId(Long equipementId) {
        this.equipementId = equipementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceDTO serviceDTO = (ServiceDTO) o;
        if (serviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
            "id=" + getId() +
            ", numService='" + getNumService() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", equipement=" + getEquipementId() +
            "}";
    }
}
