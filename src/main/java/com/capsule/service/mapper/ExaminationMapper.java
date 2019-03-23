package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.ExaminationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Examination and its DTO ExaminationDTO.
 */
@Mapper(componentModel = "spring", uses = {CapsuleMapper.class})
public interface ExaminationMapper extends EntityMapper<ExaminationDTO, Examination> {

    @Mapping(source = "capsule.id", target = "capsuleId")
    ExaminationDTO toDto(Examination examination);

    @Mapping(source = "capsuleId", target = "capsule")
    @Mapping(target = "comments", ignore = true)
    Examination toEntity(ExaminationDTO examinationDTO);

    default Examination fromId(Long id) {
        if (id == null) {
            return null;
        }
        Examination examination = new Examination();
        examination.setId(id);
        return examination;
    }
}
