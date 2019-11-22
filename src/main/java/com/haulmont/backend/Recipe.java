package com.haulmont.backend;

import com.haulmont.backend.dao.SQLEntity;
import java.util.Date;

public class Recipe implements SQLEntity {
    private final long id;
    private final long doctorId;
    private final long patientId;
    private String description;
    private Date createDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public int getValidity() {
        return validity;
    }

    public RecipePriority getPriority() {
        return priority;
    }

    public Recipe(long id, long doctorId, long patientId, String description, Date createDate, int validity, RecipePriority priority) {
        this.id = id;
        this.description = description;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.createDate = createDate;
        this.validity = validity;
        this.priority = priority;
    }

    @Override
    public long getId() {
        return id;
    }
}
