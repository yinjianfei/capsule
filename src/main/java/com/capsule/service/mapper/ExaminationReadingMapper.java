package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.ExaminationReadingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExaminationReading and its DTO ExaminationReadingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExaminationReadingMapper extends EntityMapper<ExaminationReadingDTO, ExaminationReading> {


    @Mapping(target = "diagnoses", ignore = true)
    ExaminationReading toEntity(ExaminationReadingDTO examinationReadingDTO);

    default ExaminationReading fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExaminationReading examinationReading = new ExaminationReading();
        examinationReading.setId(id);
        return examinationReading;
    }
}
