package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;

import java.sql.*;

public class DoctorDao extends AbstractControllerJDBC<Doctor> {

    @Override
    protected Doctor getEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String name = rs.getString(2);
        String lastName = rs.getString(3);
        String patronymic = rs.getString(4);
        String specialization = rs.getString(5);

        return new Doctor(id, name, lastName, patronymic, specialization);
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return statement.executeQuery("SELECT * FROM DOCTORS");
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DOCTORS WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Doctor doctor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE DOCTORS SET NAME = ?, LASTNAME = ?, PATRONYMIC = ?, SPECIALIZATION = ? WHERE ID = ?");
        preparedStatement.setString(1, doctor.getName());
        preparedStatement.setString(2, doctor.getLastName());
        preparedStatement.setString(3, doctor.getPatronymic());
        preparedStatement.setString(4, doctor.getSpecialization());
        preparedStatement.setLong(5, doctor.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForCreate(Connection connection, Doctor doctor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO DOCTORS (NAME, LASTNAME, PATRONYMIC, SPECIALIZATION) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, doctor.getName());
        preparedStatement.setString(2, doctor.getLastName());
        preparedStatement.setString(3, doctor.getPatronymic());
        preparedStatement.setString(4, doctor.getSpecialization());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DOCTORS WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }
}
