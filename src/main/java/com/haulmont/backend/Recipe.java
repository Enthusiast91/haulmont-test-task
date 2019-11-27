package com.haulmont.backend;

import com.haulmont.backend.dao.Entity;

import java.sql.Date;
import java.util.Objects;

public class Recipe implements Entity {
    private final long id;
    private String description;
    private Date creationDate;
    private short validity;
    private RecipePriority priority;

    private Patient patient;
    private final Doctor doctor;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public short getValidity() {
        return validity;
    }

    public void setValidity(short validity) {
        this.validity = validity;
    }

    public RecipePriority getPriority() {
        return priority;
    }

    public void setPriority(RecipePriority priority) {
        this.priority = priority;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Recipe(long id, String description, Date creationDate, short validity, Doctor doctor, Patient patient, RecipePriority priority) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.validity = validity;
        this.doctor = doctor;
        this.patient = patient;
        this.priority = priority;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return doctor.getId() == recipe.doctor.getId() &&
                patient.getId() == recipe.patient.getId() &&
                validity == recipe.validity &&
                description.equals(recipe.description) &&
                creationDate.equals(recipe.creationDate) &&
                priority == recipe.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor.getId(), patient.getId(), description, creationDate, validity, priority);
    }

    @Override
    public String toString() {
        return String.format("Id= %2d \tDoctorId= %2d \tPatientId= %2d \tCreationDate= %s \tValidity= %3d \tPriority= %-8s \tDescription= %s",
                id, doctor.getId(), patient.getId(), creationDate, validity, priority, description);
    }
}