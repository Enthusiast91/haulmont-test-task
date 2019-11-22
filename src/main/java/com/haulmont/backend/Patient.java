package com.haulmont.backend;

public class Patient extends AbstractPerson {
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
}
