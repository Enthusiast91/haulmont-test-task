package com.haulmont.backend.dao;

import java.io.File;

public class HSQLDBDao implements JDBCDao {
    private static HSQLDBDao jdbc;
    private String url;

    private HSQLDBDao() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Не удалось загурзить дрйвер БД");
        }
    }

    public static synchronized JDBCDao getInstance() {
        if (jdbc == null) {
            jdbc = new HSQLDBDao();
            jdbc.initDBPath();
        }
        return jdbc;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return "root";
    }

    public String getPass() {
        return "1234";
    }

    private void initDBPath() {
        String pathToProject = new File("").getAbsolutePath();
        pathToProject = pathToProject.replaceAll("[\\\\/]", "/");
        url = "jdbc:hsqldb:file:" + pathToProject + "/src/main/resources/db/maindb";
    }
}
