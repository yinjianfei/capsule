package com.capsule.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Examination.
 */
@Entity
@Table(name = "examination")
public class Examination implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "status")
    private String status;

    @Column(name = "use_date")
    private String useDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Capsule capsule;

    @OneToMany(mappedBy = "examination")
    private Set<Comment> comments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Examination patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Examination doctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getStatus() {
        return status;
    }

    public Examination status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUseDate() {
        return useDate;
    }

    public Examination useDate(String useDate) {
        this.useDate = useDate;
        return this;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public Capsule getCapsule() {
        return capsule;
    }

    public Examination capsule(Capsule capsule) {
        this.capsule = capsule;
        return this;
    }

    public void setCapsule(Capsule capsule) {
        this.capsule = capsule;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Examination comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Examination addComments(Comment comment) {
        this.comments.add(comment);
        comment.setExamination(this);
        return this;
    }

    public Examination removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setExamination(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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
        Examination examination = (Examination) o;
        if (examination.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examination.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Examination{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", doctorId=" + getDoctorId() +
            ", status='" + getStatus() + "'" +
            ", useDate='" + getUseDate() + "'" +
            "}";
    }
}
