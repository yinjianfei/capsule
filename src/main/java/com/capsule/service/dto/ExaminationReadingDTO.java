package com.capsule.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ExaminationReading entity.
 */
public class ExaminationReadingDTO implements Serializable {

    private Long id;

    private Long examinationId;

    private Long expertId;

    /**
     * 申请人id
     */
    @ApiModelProperty(value = "申请人id")
    private Long applicantId;

    private String applicantRole;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantRole() {
        return applicantRole;
    }

    public void setApplicantRole(String applicantRole) {
        this.applicantRole = applicantRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExaminationReadingDTO examinationReadingDTO = (ExaminationReadingDTO) o;
        if (examinationReadingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examinationReadingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExaminationReadingDTO{" +
            "id=" + getId() +
            ", examinationId=" + getExaminationId() +
            ", expertId=" + getExpertId() +
            ", applicantId=" + getApplicantId() +
            ", applicantRole='" + getApplicantRole() + "'" +
            "}";
    }
}
