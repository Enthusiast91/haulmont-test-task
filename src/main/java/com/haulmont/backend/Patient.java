package com.haulmont.backend;

import java.util.Objects;

public class Patient extends Man {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Patient(String name, String lastName, String patronymic, String phone) {
        super(name, lastName, patronymic);
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return name.equals(patient.name) &&
                lastName.equals(patient.lastName) &&
                patronymic.equals(patient.patronymic) &&
                phone.equals(patient.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, patronymic, phone);
    }
}
