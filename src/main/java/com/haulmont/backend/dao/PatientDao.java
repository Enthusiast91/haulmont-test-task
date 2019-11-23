package com.haulmont.backend.dao;

import com.haulmont.backend.Patient;

import java.sql.*;

public class PatientDao extends AbstractControllerJDBC<Patient> {

    @Override
    protected Patient getEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String name = rs.getString(2);
        String lastName = rs.getString(3);
        String patronymic = rs.getString(4);
        String phone = rs.getString(5);

        return new Patient(id, name, lastName, patronymic, phone);
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return statement.executeQuery("SELECT * FROM PATIENTS");
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PATIENTS WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Patient patient) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE PATIENTS SET NAME = ?, LASTNAME = ?, PATRONYMIC = ?, PHONE = ? WHERE ID = ?");
        preparedStatement.setString(1, patient.getName());
        preparedStatement.setString(2, patient.getLastName());
        preparedStatement.setString(3, patient.getPatronymic());
        preparedStatement.setString(4, patient.getPhone());
        preparedStatement.setLong(5, patient.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForCreate(Connection connection, Patient patient) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO PATIENTS (NAME, LASTNAME, PATRONYMIC, PHONE) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, patient.getName());
        preparedStatement.setString(2, patient.getLastName());
        preparedStatement.setString(3, patient.getPatronymic());
        preparedStatement.setString(4, patient.getPhone());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PATIENTS WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }
}
