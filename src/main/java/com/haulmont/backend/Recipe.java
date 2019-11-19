package com.haulmont.backend;

import java.time.LocalDate;

public class Recipe {
    private final Doctor doctor;
    private final Patient patient;
    private final String description;
    private final LocalDate createDate;
    private final LocalDate endDate;

    public Recipe(Doctor doctor, String description, Patient patient, LocalDate createDate, LocalDate endDate) {
        this.doctor = doctor;
        this.description = description;
        this.patient = patient;
        this.createDate = createDate;
        this.endDate = endDate;
    }
}
