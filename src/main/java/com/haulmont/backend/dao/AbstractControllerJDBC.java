package com.haulmont.backend.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractControllerJDBC<E extends SQLEntity> {
    private static final IDaoFactory DAO_FACTORY = HSQLDBDao.getInstance();

    protected abstract E getTableEntry(ResultSet rs) throws SQLException;

    protected abstract ResultSet getResultSetForGetAll(Statement statement) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForUpdate(Connection connection, E entity) throws SQLException;

    public List<E> getAll() throws SQLException {
        List<E> list = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = getResultSetForGetAll(statement)) {
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getTableEntry(rs));
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
                entity = getTableEntry(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return entity;
    }

    public void update(E entity) {
        long id = entity.getId();
        try (PreparedStatement preparedStatement = getPreparedStatementForUpdate(getConnection(), entity)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


//
//    public boolean delete(E id) {
//
//    }
//
//    public boolean create(E entity) {
//
//    }

    private Connection getConnection() {
        String url = DAO_FACTORY.getUrl();
        String userName = DAO_FACTORY.getUserName();
        String pass = DAO_FACTORY.getPass();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void printSQLException(SQLException e) {
        System.err.println("SQLException " + e.getMessage());
        System.err.println("SQLException SQL state" + e.getSQLState());
        System.err.println("SQLException error code" + e.getErrorCode());
    }
}
