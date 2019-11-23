package com.haulmont.backend.dao;

import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;

import java.sql.*;

public class RecipeDao extends AbstractEntityDAO<Recipe> {

    @Override
    protected Recipe getEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long doctorId = rs.getLong(2);
        long patientID = rs.getLong(3);
        String description = rs.getString(4);
        Date creationDate = rs.getDate(5);
        short validity = rs.getShort(6);
        long priorityId = rs.getLong(7);

        RecipePriority recipePriority = new RecipePriorityDao().getEntityById(priorityId);
        return new Recipe(id, doctorId, patientID, description, creationDate, validity, recipePriority);
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
        preparedStatement.setLong(1, recipe.getDoctorId());
        preparedStatement.setLong(2, recipe.getPatientId());
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
}