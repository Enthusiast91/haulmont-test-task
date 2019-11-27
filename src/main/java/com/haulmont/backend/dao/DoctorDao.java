package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao extends AbstractEntityDAO<Doctor> {

    @Override
    protected Doctor getEntity(ResultSet rs) throws SQLException {
        return new Doctor(rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getString("LASTNAME"),
                rs.getString("PATRONYMIC"),
                rs.getString("SPECIALIZATION"));
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
    protected PreparedStatement getPreparedStatementForAdd(Connection connection, Doctor doctor) throws SQLException {
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

    public List<DoctorAndQuantityRecipes> getAllWithCountRecipes() {
        List<DoctorAndQuantityRecipes> list = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT ID, NAME, LASTNAME, PATRONYMIC, SPECIALIZATION, COUNT(*) QTY " +
                         "FROM DOCTORS D " +
                         "JOIN RECIPES R " +
                         "ON D.ID = R.DOCTORID " +
                         "GROUP BY D.ID")) {
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getEntityAndQuantityRecipes(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    private DoctorAndQuantityRecipes getEntityAndQuantityRecipes(ResultSet rs) throws SQLException {
        Doctor doctor = getEntity(rs);
        int quantityRecipes = rs.getInt("QTY");
        return new DoctorAndQuantityRecipes(doctor, quantityRecipes);
    }

    public static class DoctorAndQuantityRecipes {
        public Doctor doctor;
        public int quantityRecipes;

        DoctorAndQuantityRecipes(Doctor doctor, int quantityRecipes) {
            this.doctor = new Doctor(doctor.getId(), doctor.getName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpecialization());
            this.quantityRecipes = quantityRecipes;
        }

        @Override
        public String toString() {
            return doctor + " QTY= " + quantityRecipes;
        }
    }
}
