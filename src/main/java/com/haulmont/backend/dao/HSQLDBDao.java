package com.haulmont.backend.dao;

import java.io.File;

public class HSQLDBDao implements JDBCDao {
    private String url;

    private static HSQLDBDao jdbc;

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return "root";
    }

    public String getPass() {
        return "1234";
    }

    public static synchronized JDBCDao getInstance() {
        if (jdbc == null) {
            jdbc = new HSQLDBDao();
            jdbc.initDBPath();
        }
        return jdbc;
    }

    private HSQLDBDao() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Не удалось загурзить дрйвер БД");
        }
    }

    private void initDBPath() {
        String fSep = System.getProperty("file.separator");
        String pathToProject = new File("").getAbsolutePath();
        pathToProject = pathToProject.replaceAll("[\\\\/]", "/");
        url = "jdbc:hsqldb:file:" + pathToProject + "/src/main/resources/db/maindb";
    }
}
