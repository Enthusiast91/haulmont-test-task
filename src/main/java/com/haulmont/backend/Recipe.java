package com.haulmont.backend;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Recipe {
    private int id;
    private final int doctorId;
    private final int patientId;
    private final String description;
    private final LocalDate createDate;
    private final Period validity;

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Period getValidity() {
        return validity;
    }

    public Recipe(int doctorId, int patientId, String description, LocalDate createDate, Period validity) {
        this.description = description;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.createDate = createDate;
        this.validity = validity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return doctorId == recipe.doctorId &&
                patientId == recipe.patientId &&
                description.equals(recipe.description) &&
                createDate.equals(recipe.createDate) &&
                Objects.equals(validity, recipe.validity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, patientId, description, createDate, validity);
    }
}
