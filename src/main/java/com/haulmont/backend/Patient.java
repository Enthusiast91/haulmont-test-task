package com.haulmont.backend;

import java.util.Objects;

public class Patient extends Man {
    private String phoneNumber;

    protected Patient(String name, String lastName, String patronymic) {
        super(name, lastName, patronymic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return name.equals(patient.name) &&
                lastName.equals(patient.lastName) &&
                patronymic.equals(patient.patronymic) &&
                phoneNumber.equals(patient.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, patronymic, phoneNumber);
    }
}
