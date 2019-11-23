package com.haulmont.backend;

import com.haulmont.backend.dao.Entity;

import java.sql.Date;
import java.util.Objects;

public class Recipe implements Entity {
    private final long id;
    private final long doctorId;
    private final long patientId;
    private String description;
    private Date creationDate;
    private short validity;
    private RecipePriority priority;

    public long getDoctorId() {
        return doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

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

    public Recipe(long id, long doctorId, long patientId, String description, Date creationDate, short validity, RecipePriority priority) {
        this.id = id;
        this.description = description;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.creationDate = creationDate;
        this.validity = validity;
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
        return id == recipe.id &&
                doctorId == recipe.doctorId &&
                patientId == recipe.patientId &&
                validity == recipe.validity &&
                description.equals(recipe.description) &&
                creationDate.equals(recipe.creationDate) &&
                priority == recipe.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctorId, patientId, description, creationDate, validity, priority);
    }

    @Override
    public String toString() {
        return String.format("Id= %2d \tDoctorId= %2d \tPatientId= %2d \tCreationDate= %s \tValidity= %3d \tPriority= %-8s \tDescription= %s",
                id, doctorId, patientId, creationDate, validity, priority, description);
    }
}