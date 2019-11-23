package com.haulmont.backend;

import com.haulmont.backend.dao.SQLEntity;

import java.sql.Date;

public class Recipe implements SQLEntity {
    private final long id;
    private final long doctorId;
    private final long patientId;
    private String description;
    private Date creationDate;
    private int validity;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public int getValidity() {
        return validity;
    }

    public RecipePriority getPriority() {
        return priority;
    }

    public Recipe(long id, long doctorId, long patientId, String description, Date creationDate, int validity, RecipePriority priority) {
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
}
