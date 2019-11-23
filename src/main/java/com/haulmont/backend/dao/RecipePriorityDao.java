package com.haulmont.backend.dao;

import com.haulmont.backend.RecipePriority;

import java.sql.*;

public class RecipePriorityDao extends AbstractEntityDAO<RecipePriority> {

    @Override
    protected RecipePriority getEntity(ResultSet rs) throws SQLException {
        String priorityName = rs.getString(2);
        return RecipePriority.valueOf(priorityName);
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return statement.executeQuery("SELECT * FROM RECIPEPRIORITY");
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM RECIPEPRIORITY WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, RecipePriority recipePriority) {
        return null;
    }

    @Override
    protected PreparedStatement getPreparedStatementForAdd(Connection connection, RecipePriority recipePriority) {
        return null;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) {
        return null;
    }

    @Override
    public void update(RecipePriority entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(RecipePriority entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
