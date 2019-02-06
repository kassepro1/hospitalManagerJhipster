package com.isi.hospitalmanager.service.mapper;

import com.isi.hospitalmanager.domain.*;
import com.isi.hospitalmanager.service.dto.ServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Service and its DTO ServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {EquipementMapper.class})
public interface ServiceMapper extends EntityMapper<ServiceDTO, Service> {

    @Mapping(source = "equipement.id", target = "equipementId")
    ServiceDTO toDto(Service service);

    @Mapping(source = "equipementId", target = "equipement")
    Service toEntity(ServiceDTO serviceDTO);

    default Service fromId(Long id) {
        if (id == null) {
            return null;
        }
        Service service = new Service();
        service.setId(id);
        return service;
    }
}
