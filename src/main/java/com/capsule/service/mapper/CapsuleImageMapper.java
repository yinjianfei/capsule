package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.CapsuleImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CapsuleImage and its DTO CapsuleImageDTO.
 */
@Mapper(componentModel = "spring", uses = {CapsuleMapper.class})
public interface CapsuleImageMapper extends EntityMapper<CapsuleImageDTO, CapsuleImage> {

    @Mapping(source = "capsule.id", target = "capsuleId")
    CapsuleImageDTO toDto(CapsuleImage capsuleImage);

    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "capsuleId", target = "capsule")
    CapsuleImage toEntity(CapsuleImageDTO capsuleImageDTO);

    default CapsuleImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        CapsuleImage capsuleImage = new CapsuleImage();
        capsuleImage.setId(id);
        return capsuleImage;
    }
}
