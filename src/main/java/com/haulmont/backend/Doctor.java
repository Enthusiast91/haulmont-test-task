package com.haulmont.backend;

public class Doctor extends AbstractPerson {
    private String specialization;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Doctor(long id, String name, String lastName, String patronymic, String specialization) {
        super(id, name, lastName, patronymic);
        this.specialization = specialization;
    }

}
