package com.haulmont.backend.dao;

import com.haulmont.backend.Patient;

import static org.junit.Assert.*;

public class PatientDaoTest extends AbstractEntityDAOTest {

    public PatientDaoTest() {
        super(new PatientDao());
    }

    @Override
    protected Entity getUpdateEntity(Entity entity) {
        Patient patient = (Patient) entity;
        patient.setName("UpdateName");
        patient.setLastName("UpdateLastName");
        patient.setPatronymic("UpdatePatronymic");
        patient.setPhone("81110000000");
        return patient;
    }

    @Override
    protected Entity getNewEntity() {
        String name = "NewName";
        String lastName = "NewLastName";
        String patronymic = "NewPatronymic";
        String phone = "81110000000";

        return new Patient(0, name, lastName, patronymic, phone);
    }
}