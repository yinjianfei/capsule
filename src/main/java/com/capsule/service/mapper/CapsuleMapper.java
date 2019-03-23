package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.CapsuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Capsule and its DTO CapsuleDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface CapsuleMapper extends EntityMapper<CapsuleDTO, Capsule> {

    @Mapping(source = "patient.id", target = "patientId")
    CapsuleDTO toDto(Capsule capsule);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "examination", ignore = true)
    @Mapping(source = "patientId", target = "patient")
    Capsule toEntity(CapsuleDTO capsuleDTO);

    default Capsule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Capsule capsule = new Capsule();
        capsule.setId(id);
        return capsule;
    }
}
