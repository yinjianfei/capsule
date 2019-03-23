package com.capsule.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 2048)
    @Column(name = "content", length = 2048)
    private String content;

    @Column(name = "comment_by")
    private Long commentBy;

    @Column(name = "comment_at")
    private Instant commentAt;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Examination examination;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private CapsuleImage capsuleImage;

    @ManyToOne
    @JsonIgnoreProperties("diagnoses")
    private ExaminationReading examinationReading;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentBy() {
        return commentBy;
    }

    public Comment commentBy(Long commentBy) {
        this.commentBy = commentBy;
        return this;
    }

    public void setCommentBy(Long commentBy) {
        this.commentBy = commentBy;
    }

    public Instant getCommentAt() {
        return commentAt;
    }

    public Comment commentAt(Instant commentAt) {
        this.commentAt = commentAt;
        return this;
    }

    public void setCommentAt(Instant commentAt) {
        this.commentAt = commentAt;
    }

    public Examination getExamination() {
        return examination;
    }

    public Comment examination(Examination examination) {
        this.examination = examination;
        return this;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public CapsuleImage getCapsuleImage() {
        return capsuleImage;
    }

    public Comment capsuleImage(CapsuleImage capsuleImage) {
        this.capsuleImage = capsuleImage;
        return this;
    }

    public void setCapsuleImage(CapsuleImage capsuleImage) {
        this.capsuleImage = capsuleImage;
    }

    public ExaminationReading getExaminationReading() {
        return examinationReading;
    }

    public Comment examinationReading(ExaminationReading examinationReading) {
        this.examinationReading = examinationReading;
        return this;
    }

    public void setExaminationReading(ExaminationReading examinationReading) {
        this.examinationReading = examinationReading;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", commentBy=" + getCommentBy() +
            ", commentAt='" + getCommentAt() + "'" +
            "}";
    }
}
