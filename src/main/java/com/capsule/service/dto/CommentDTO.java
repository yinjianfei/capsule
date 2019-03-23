package com.capsule.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Comment entity.
 */
public class CommentDTO implements Serializable {

    private Long id;

    @Size(max = 2048)
    private String content;

    private Long commentBy;

    private Instant commentAt;


    private Long examinationId;

    private Long capsuleImageId;

    private Long examinationReadingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(Long commentBy) {
        this.commentBy = commentBy;
    }

    public Instant getCommentAt() {
        return commentAt;
    }

    public void setCommentAt(Instant commentAt) {
        this.commentAt = commentAt;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    public Long getCapsuleImageId() {
        return capsuleImageId;
    }

    public void setCapsuleImageId(Long capsuleImageId) {
        this.capsuleImageId = capsuleImageId;
    }

    public Long getExaminationReadingId() {
        return examinationReadingId;
    }

    public void setExaminationReadingId(Long examinationReadingId) {
        this.examinationReadingId = examinationReadingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", commentBy=" + getCommentBy() +
            ", commentAt='" + getCommentAt() + "'" +
            ", examination=" + getExaminationId() +
            ", capsuleImage=" + getCapsuleImageId() +
            ", examinationReading=" + getExaminationReadingId() +
            "}";
    }
}
