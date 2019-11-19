package com.haulmont.backend.dao;

import com.haulmont.backend.Patient;

import java.util.List;

public interface PatientDao {
    Patient create();

    Patient read();

    void update(Patient patient);

    void delete(Patient patient);

    List<Patient> getAll();
}
