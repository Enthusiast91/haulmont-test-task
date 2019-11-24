package com.haulmont.backend.dao;

import com.haulmont.backend.Patient;

public class PatientDaoTest extends AbstractEntityDAOTest<Patient> {

    public PatientDaoTest() {
        super(new PatientDao());
    }

    @Override
    protected Patient getUpdateEntity(Patient patient) {
        patient.setName("UpdateName");
        patient.setLastName("UpdateLastName");
        patient.setPatronymic("UpdatePatronymic");
        patient.setPhone("81110000000");
        return patient;
    }

    @Override
    protected Patient getNewEntity() {
        String name = "NewName";
        String lastName = "NewLastName";
        String patronymic = "NewPatronymic";
        String phone = "81110000000";

        return new Patient(0, name, lastName, patronymic, phone);
    }
}