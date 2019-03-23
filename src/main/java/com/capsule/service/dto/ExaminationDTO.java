package com.capsule.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Examination entity.
 */
public class ExaminationDTO implements Serializable {

    private Long id;

    private Long patientId;

    private Long doctorId;

    private String status;

    private String useDate;


    private Long capsuleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public Long getCapsuleId() {
        return capsuleId;
    }

    public void setCapsuleId(Long capsuleId) {
        this.capsuleId = capsuleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExaminationDTO examinationDTO = (ExaminationDTO) o;
        if (examinationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examinationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExaminationDTO{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", doctorId=" + getDoctorId() +
            ", status='" + getStatus() + "'" +
            ", useDate='" + getUseDate() + "'" +
            ", capsule=" + getCapsuleId() +
            "}";
    }
}
