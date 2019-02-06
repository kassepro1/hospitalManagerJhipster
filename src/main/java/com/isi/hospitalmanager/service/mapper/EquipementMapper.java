package com.isi.hospitalmanager.service.mapper;

import com.isi.hospitalmanager.domain.*;
import com.isi.hospitalmanager.service.dto.EquipementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Equipement and its DTO EquipementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EquipementMapper extends EntityMapper<EquipementDTO, Equipement> {


    @Mapping(target = "services", ignore = true)
    @Mapping(target = "types", ignore = true)
    Equipement toEntity(EquipementDTO equipementDTO);

    default Equipement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equipement equipement = new Equipement();
        equipement.setId(id);
        return equipement;
    }
}
