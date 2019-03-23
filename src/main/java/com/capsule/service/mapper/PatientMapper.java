package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patient and its DTO PatientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {


    @Mapping(target = "capsules", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
