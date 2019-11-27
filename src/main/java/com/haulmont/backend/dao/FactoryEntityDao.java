package com.haulmont.backend.dao;

public class FactoryEntityDao {
    public static PatientDao getPatientDao() {
        return new PatientDao();
    }
}
