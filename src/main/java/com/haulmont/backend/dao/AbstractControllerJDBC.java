package com.haulmont.backend.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractControllerJDBC<E extends SQLEntity> {
    // Database management system
    private static final JDBCDao JDBC = HSQLDBDao.getInstance();

    protected abstract E getEntity(ResultSet rs) throws SQLException;

    protected abstract ResultSet getResultSetForGetAll(Statement statement) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForUpdate(Connection connection, E entity) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForCreate(Connection connection, E entity) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) throws SQLException;

    public List<E> getAll() {
        List<E> list = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = getResultSetForGetAll(statement)) {
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getEntity(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public E getEntityById(long id) {
        E entity = null;
        try (PreparedStatement preparedStatement = getPreparedStatementForGetEntityById(getConnection(), id);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                entity = getEntity(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return entity;
    }

    public boolean update(E entity) {
        try (PreparedStatement preparedStatement = getPreparedStatementForUpdate(getConnection(), entity)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean create(E entity) {
        try (PreparedStatement preparedStatement = getPreparedStatementForCreate(getConnection(), entity)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = getPreparedStatementForDelete(getConnection(), id)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    protected Connection getConnection() {
        String url = JDBC.getUrl();
        String userName = JDBC.getUserName();
        String pass = JDBC.getPass();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, pass);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return connection;
    }

    private void printSQLException(SQLException e) {
        System.err.println("SQLException " + e.getMessage());
        System.err.println("SQLException SQL state" + e.getSQLState());
        System.err.println("SQLException error code" + e.getErrorCode());
    }
}
