package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.ExpertDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Expert and its DTO ExpertDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExpertMapper extends EntityMapper<ExpertDTO, Expert> {



    default Expert fromId(Long id) {
        if (id == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setId(id);
        return expert;
    }
}
