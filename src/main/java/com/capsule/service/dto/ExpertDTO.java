package com.capsule.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Expert entity.
 */
public class ExpertDTO implements Serializable {

    private Long id;

    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpertDTO expertDTO = (ExpertDTO) o;
        if (expertDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expertDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpertDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
