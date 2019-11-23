package com.haulmont.backend;

import java.util.Objects;

public class Patient extends AbstractPerson{
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Patient(long id, String name, String lastName, String patronymic, String phone) {
        super(id, name, lastName, patronymic);
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return phone.equals(patient.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone);
    }

    @Override
    public String toString() {
        return String.format("Id= %2d \tName= %s \tLastName= %s \tPatronymic= %s \tPhone= %s",
                id, name, lastName, patronymic, phone);
    }
}
