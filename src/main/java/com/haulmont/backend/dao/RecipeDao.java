package com.haulmont.backend.dao;

import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;

import java.sql.*;

public class RecipeDao extends AbstractControllerJDBC<Recipe> {

    @Override
    protected Recipe getTableEntry(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long doctorId = rs.getLong(2);
        long patientID = rs.getLong(3);
        String description = rs.getString(4);
        Date creationDate = rs.getDate(5);
        int validity = rs.getInt(6);
        long priority = rs.getLong(7);

        return new Recipe(id, doctorId, patientID, description, creationDate, validity, );
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
        long id = recipe.getId();
        String description = recipe.getDescription();
        int validity = recipe.getValidity();
        long priorityId = recipe.getPriority().getId();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RECIPES SET DESCRIPTION = ?,  WHERE ID = 3;");
        preparedStatement.setLong(1, id);
        return null;
    }
}