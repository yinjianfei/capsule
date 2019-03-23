package com.capsule.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ExaminationReading.
 */
@Entity
@Table(name = "examination_reading")
public class ExaminationReading implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "examination_id")
    private Long examinationId;

    @Column(name = "expert_id")
    private Long expertId;

    /**
     * 申请人id
     */
    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "applicant_role")
    private String applicantRole;

    @OneToMany(mappedBy = "examinationReading")
    private Set<Comment> diagnoses = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public ExaminationReading examinationId(Long examinationId) {
        this.examinationId = examinationId;
        return this;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public ExaminationReading expertId(Long expertId) {
        this.expertId = expertId;
        return this;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public ExaminationReading applicantId(Long applicantId) {
        this.applicantId = applicantId;
        return this;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantRole() {
        return applicantRole;
    }

    public ExaminationReading applicantRole(String applicantRole) {
        this.applicantRole = applicantRole;
        return this;
    }

    public void setApplicantRole(String applicantRole) {
        this.applicantRole = applicantRole;
    }

    public Set<Comment> getDiagnoses() {
        return diagnoses;
    }

    public ExaminationReading diagnoses(Set<Comment> comments) {
        this.diagnoses = comments;
        return this;
    }

    public ExaminationReading addDiagnoses(Comment comment) {
        this.diagnoses.add(comment);
        comment.setExaminationReading(this);
        return this;
    }

    public ExaminationReading removeDiagnoses(Comment comment) {
        this.diagnoses.remove(comment);
        comment.setExaminationReading(null);
        return this;
    }

    public void setDiagnoses(Set<Comment> comments) {
        this.diagnoses = comments;
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
        ExaminationReading examinationReading = (ExaminationReading) o;
        if (examinationReading.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examinationReading.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExaminationReading{" +
            "id=" + getId() +
            ", examinationId=" + getExaminationId() +
            ", expertId=" + getExpertId() +
            ", applicantId=" + getApplicantId() +
            ", applicantRole='" + getApplicantRole() + "'" +
            "}";
    }
}
