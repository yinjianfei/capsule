package com.capsule.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Capsule.
 */
@Entity
@Table(name = "capsule")
public class Capsule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capsule_type")
    private String capsuleType;

    @Column(name = "capsule_number")
    private String capsuleNumber;

    @OneToMany(mappedBy = "capsule")
    private Set<CapsuleImage> images = new HashSet<>();
    @OneToOne(mappedBy = "capsule")
    @JsonIgnore
    private Examination examination;

    @ManyToOne
    @JsonIgnoreProperties("capsules")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapsuleType() {
        return capsuleType;
    }

    public Capsule capsuleType(String capsuleType) {
        this.capsuleType = capsuleType;
        return this;
    }

    public void setCapsuleType(String capsuleType) {
        this.capsuleType = capsuleType;
    }

    public String getCapsuleNumber() {
        return capsuleNumber;
    }

    public Capsule capsuleNumber(String capsuleNumber) {
        this.capsuleNumber = capsuleNumber;
        return this;
    }

    public void setCapsuleNumber(String capsuleNumber) {
        this.capsuleNumber = capsuleNumber;
    }

    public Set<CapsuleImage> getImages() {
        return images;
    }

    public Capsule images(Set<CapsuleImage> capsuleImages) {
        this.images = capsuleImages;
        return this;
    }

    public Capsule addImages(CapsuleImage capsuleImage) {
        this.images.add(capsuleImage);
        capsuleImage.setCapsule(this);
        return this;
    }

    public Capsule removeImages(CapsuleImage capsuleImage) {
        this.images.remove(capsuleImage);
        capsuleImage.setCapsule(null);
        return this;
    }

    public void setImages(Set<CapsuleImage> capsuleImages) {
        this.images = capsuleImages;
    }

    public Examination getExamination() {
        return examination;
    }

    public Capsule examination(Examination examination) {
        this.examination = examination;
        return this;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Patient getPatient() {
        return patient;
    }

    public Capsule patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
        Capsule capsule = (Capsule) o;
        if (capsule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capsule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Capsule{" +
            "id=" + getId() +
            ", capsuleType='" + getCapsuleType() + "'" +
            ", capsuleNumber='" + getCapsuleNumber() + "'" +
            "}";
    }
}
