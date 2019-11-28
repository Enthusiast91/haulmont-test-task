package com.haulmont.backend;

import com.haulmont.backend.dao.Entity;
import com.haulmont.ui.components.Viewable;

import java.util.Objects;

public class Patient extends AbstractPerson implements Viewable<Patient> {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Patient getEmpty() {
        return new Patient(0, "", "", "", "");
    }

    public Patient(long id, String name, String lastName, String patronymic, String phone) {
        super(id, name, lastName, patronymic);
        this.phone = phone;
    }

//    @Override
//    public void updateValue(Patient patient) {
//        super.updateValue(patient);
//        setPhone(patient.getPhone());
//    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Patient getCopy(){
        return new Patient(id, name, lastName, patronymic, phone);
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
