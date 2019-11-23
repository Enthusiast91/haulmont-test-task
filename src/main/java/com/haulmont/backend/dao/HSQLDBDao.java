package com.haulmont.backend.dao;

import java.io.File;

public class HSQLDBDao implements JDBCDao {
    private final String name = "root";
    private final String pass = "1234";
    //private String pathToSQLDBInitFile;
    private String url;

    // Database management system
    private static HSQLDBDao jdbc;

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return name;
    }

    public String getPass() {
        return pass;
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
        //pathToSQLDBInitFile = pathToProject + fSep + "src" + fSep + "main" + fSep + "resources" + fSep + "db" + fSep + "initDBinline.sql";
        pathToProject = pathToProject.replaceAll("[\\\\/]", "/");
        url = "jdbc:hsqldb:file:" + pathToProject + "/src/main/resources/db/maindb";
    }
}
