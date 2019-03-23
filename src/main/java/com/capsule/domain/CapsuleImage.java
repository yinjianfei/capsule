package com.capsule.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CapsuleImage.
 */
@Entity
@Table(name = "capsule_image")
public class CapsuleImage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "capsuleImage")
    private Set<Comment> comments = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("images")
    private Capsule capsule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CapsuleImage imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public CapsuleImage comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public CapsuleImage addComments(Comment comment) {
        this.comments.add(comment);
        comment.setCapsuleImage(this);
        return this;
    }

    public CapsuleImage removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setCapsuleImage(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Capsule getCapsule() {
        return capsule;
    }

    public CapsuleImage capsule(Capsule capsule) {
        this.capsule = capsule;
        return this;
    }

    public void setCapsule(Capsule capsule) {
        this.capsule = capsule;
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
        CapsuleImage capsuleImage = (CapsuleImage) o;
        if (capsuleImage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capsuleImage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapsuleImage{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
