package com.capsule.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Capsule entity.
 */
public class CapsuleDTO implements Serializable {

    private Long id;

    private String capsuleType;

    private String capsuleNumber;


    private Long patientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapsuleType() {
        return capsuleType;
    }

    public void setCapsuleType(String capsuleType) {
        this.capsuleType = capsuleType;
    }

    public String getCapsuleNumber() {
        return capsuleNumber;
    }

    public void setCapsuleNumber(String capsuleNumber) {
        this.capsuleNumber = capsuleNumber;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CapsuleDTO capsuleDTO = (CapsuleDTO) o;
        if (capsuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capsuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapsuleDTO{" +
            "id=" + getId() +
            ", capsuleType='" + getCapsuleType() + "'" +
            ", capsuleNumber='" + getCapsuleNumber() + "'" +
            ", patient=" + getPatientId() +
            "}";
    }
}
