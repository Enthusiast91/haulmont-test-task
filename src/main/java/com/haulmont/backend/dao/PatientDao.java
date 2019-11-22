package com.haulmont.backend.dao;

import com.haulmont.backend.Patient;

import java.sql.*;

public class PatientDao extends AbstractControllerJDBC<Patient> {

    @Override
    protected Patient getTableEntry(ResultSet rs)  throws SQLException{
        return null;
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id)  throws SQLException{
        return null;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, long id)  throws SQLException{
        return null;
    }
}
