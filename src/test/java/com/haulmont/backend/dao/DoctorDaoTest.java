package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;

import static org.junit.Assert.*;

public class DoctorDaoTest extends AbstractEntityDAOTest {
    public DoctorDaoTest() {
        super(new DoctorDao());
    }

    @Override
    protected Entity getUpdateEntity(Entity entity) {
        Doctor doctor = (Doctor) entity;
        doctor.setName("UpdateName");
        doctor.setLastName("UpdateLastName");
        doctor.setPatronymic("UpdatePatronymic");
        doctor.setSpecialization("UpdateSpecilization");
        return doctor;
    }

    @Override
    protected Entity getNewEntity() {
        String name = "NewName";
        String lastName = "NewLastName";
        String patronymic = "NewPatronymic";
        String specialization = "NewSpecialization";

        return new Doctor(0, name, lastName, patronymic, specialization);
    }
}