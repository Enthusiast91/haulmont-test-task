package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao extends AbstractEntityDAO<Recipe> {

    @Override
    protected Recipe getEntity(ResultSet rs) throws SQLException {
        RecipePriority recipePriority = new RecipePriorityDao().getById(rs.getLong("PRIORITYID"));
        Doctor doctor = new DoctorDao().getById(rs.getLong("DOCTORID"));
        Patient patient = new PatientDao().getById(rs.getLong("PATIENTID"));

        return new Recipe(rs.getLong("ID"),
                rs.getString("DESCRIPTION"),
                rs.getDate("CREATIONDATE"),
                rs.getShort("VALIDITY"),
                doctor,
                patient,
                recipePriority);
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return statement.executeQuery("SELECT * FROM RECIPES");
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM RECIPES WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Recipe recipe) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE RECIPES SET DESCRIPTION = ?, VALIDITY = ?, PRIORITYID = ? WHERE ID = ?");
        preparedStatement.setString(1, recipe.getDescription());
        preparedStatement.setShort(2, recipe.getValidity());
        preparedStatement.setLong(3, recipe.getPriority().getId());
        preparedStatement.setLong(4, recipe.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForAdd(Connection connection, Recipe recipe) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO RECIPES (DOCTORID, PATIENTID, DESCRIPTION, CREATIONDATE, VALIDITY, PRIORITYID) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setLong(1, recipe.getDoctor().getId());
        preparedStatement.setLong(2, recipe.getPatient().getId());
        preparedStatement.setString(3, recipe.getDescription());
        preparedStatement.setDate(4, recipe.getCreationDate());
        preparedStatement.setShort(5, recipe.getValidity());
        preparedStatement.setLong(6, recipe.getPriority().getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM RECIPES WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    public List<Recipe> getFiltered(long patientID, long priorityID, String description) {
        List<Recipe> list = null;
        String patientFilter = patientID > -1 ? " AND PATIENTID = " + patientID : "";
        String priorityFilter = priorityID > -1 ? " AND PRIORITYID = " + priorityID : "";
        String decriptionFilter = description.length() > 0 ? " AND DESCRIPTION LIKE '%" + description + "%'" : "";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM RECIPES WHERE TRUE " +
                     patientFilter + priorityFilter + decriptionFilter)) {
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getEntity(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }
}