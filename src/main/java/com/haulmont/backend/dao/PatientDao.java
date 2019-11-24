package com.haulmont.backend.dao;

import com.haulmont.backend.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PatientDao extends AbstractEntityDAO<Patient> {

    @Override
    protected Patient getEntity(ResultSet rs) throws SQLException {
        return new Patient(rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getString("LASTNAME"),
                rs.getString("PATRONYMIC"),
                rs.getString("PHONE"));
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
    protected PreparedStatement getPreparedStatementForAdd(Connection connection, Patient patient) throws SQLException {
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
