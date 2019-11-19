package com.haulmont.backend;

import java.util.Objects;

public class Doctor extends Man{
    private String specialization;

    protected Doctor(String name, String lastName, String patronymic) {
        super(name, lastName, patronymic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return name.equals(doctor.name) &&
                lastName.equals(doctor.lastName) &&
                patronymic.equals(doctor.patronymic) &&
                specialization.equals(doctor.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, patronymic, specialization);
    }
}
