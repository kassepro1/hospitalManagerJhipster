package com.isi.hospitalmanager.service.mapper;

import com.isi.hospitalmanager.domain.*;
import com.isi.hospitalmanager.service.dto.TypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Type and its DTO TypeDTO.
 */
@Mapper(componentModel = "spring", uses = {CategorieMapper.class, EquipementMapper.class})
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {

    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "equipement.id", target = "equipementId")
    TypeDTO toDto(Type type);

    @Mapping(source = "categorieId", target = "categorie")
    @Mapping(source = "equipementId", target = "equipement")
    Type toEntity(TypeDTO typeDTO);

    default Type fromId(Long id) {
        if (id == null) {
            return null;
        }
        Type type = new Type();
        type.setId(id);
        return type;
    }
}
