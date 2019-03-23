package com.capsule.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Expert.
 */
@Entity
@Table(name = "expert")
public class Expert implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Expert userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        Expert expert = (Expert) o;
        if (expert.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expert.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Expert{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
