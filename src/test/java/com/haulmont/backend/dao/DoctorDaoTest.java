package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;
import org.junit.Test;

import java.util.List;

public class DoctorDaoTest extends AbstractEntityDAOTest<Doctor> {
    public DoctorDaoTest() {
        super(new DoctorDao());
    }

    @Override
    protected Doctor getUpdateEntity(Doctor doctor) {
        doctor.setName("UpdateName");
        doctor.setLastName("UpdateLastName");
        doctor.setPatronymic("UpdatePatronymic");
        doctor.setSpecialization("UpdateSpecilization");
        return doctor;
    }

    @Override
    protected Doctor getNewEntity() {
        String name = "NewName";
        String lastName = "NewLastName";
        String patronymic = "NewPatronymic";
        String specialization = "NewSpecialization";

        return new Doctor(0, name, lastName, patronymic, specialization);
    }

    @Test
    public void getAllWithCountRecipesTest() {
        List<DoctorDao.DoctorAndQuantityRecipes> list = ((DoctorDao) entityDAO).getAllWithCountRecipes();
        for (DoctorDao.DoctorAndQuantityRecipes entry : list) {
            System.out.println(entry);
        }
    }

}