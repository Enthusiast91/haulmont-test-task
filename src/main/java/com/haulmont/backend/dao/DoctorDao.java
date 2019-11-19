package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;

import java.util.List;

public interface DoctorDao {
    Doctor create();

    Doctor read();

    void update(Doctor doctor);

    void delete(Doctor doctor);

    List<Doctor> getAll();
}
