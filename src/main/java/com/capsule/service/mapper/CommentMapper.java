package com.capsule.service.mapper;

import com.capsule.domain.*;
import com.capsule.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {ExaminationMapper.class, CapsuleImageMapper.class, ExaminationReadingMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "examination.id", target = "examinationId")
    @Mapping(source = "capsuleImage.id", target = "capsuleImageId")
    @Mapping(source = "examinationReading.id", target = "examinationReadingId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "examinationId", target = "examination")
    @Mapping(source = "capsuleImageId", target = "capsuleImage")
    @Mapping(source = "examinationReadingId", target = "examinationReading")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
