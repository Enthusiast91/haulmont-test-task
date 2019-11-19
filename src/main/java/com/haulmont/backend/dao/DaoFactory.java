package com.haulmont.backend.dao;

import java.sql.Connection;

public interface DaoFactory {
    Connection getConnection();

    DoctorDao getDoctorDao();

    PatientDao getPatientDao();

    RecipeDao getRecipeDao();
}
