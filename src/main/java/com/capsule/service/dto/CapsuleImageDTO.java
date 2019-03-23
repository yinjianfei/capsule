package com.capsule.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CapsuleImage entity.
 */
public class CapsuleImageDTO implements Serializable {

    private Long id;

    private String imageUrl;


    private Long capsuleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

        CapsuleImageDTO capsuleImageDTO = (CapsuleImageDTO) o;
        if (capsuleImageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capsuleImageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapsuleImageDTO{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", capsule=" + getCapsuleId() +
            "}";
    }
}
