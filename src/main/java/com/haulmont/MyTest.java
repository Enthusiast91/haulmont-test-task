package com.haulmont;

import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class MyTest {
    private static String pathToSQLDBInitFile;
    private static String url;
    private static final String userName = "root";
    private static final String pass = "1234";
    private static final String jdbcDriver = "org.hsqldb.jdbcDriver";

    public static void main(String[] args) {
        initJDBCPath();

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загурзить дрйвер БД");
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, userName, pass);
             Statement statement = connection.createStatement()) {

            //initDatabase(statement);

//            ResultSet rs = null;
//            try {
//                rs = statement.executeQuery("SELECT * FROM RECIPES");
//                while (rs.next()) {
//                    System.out.println(rs.getLong(0));
//                }
//            } catch (SQLException e) {
//                System.err.println("SQLException " + e.getMessage());
//                System.err.println("SQLException SQL state" + e.getSQLState());
//                System.err.println("SQLException error code" + e.getErrorCode());
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                } else {
//                    System.out.println("Error reading database");
//                }
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initDatabase(Statement statement) throws SQLException {
        BufferedReader sqlFileReader = null;
        try {
            sqlFileReader = new BufferedReader(new FileReader(pathToSQLDBInitFile));
            Scanner scanner = new Scanner(sqlFileReader);
            String sqlLine = "";

            while (scanner.hasNextLine()) {
                sqlLine = scanner.nextLine();
                if (sqlLine.endsWith(";")) {
                    sqlLine = sqlLine.substring(0, sqlLine.length() - 1);
                }
                statement.execute(sqlLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initJDBCPath() {
        String pathToProject = new File("").getAbsolutePath();
        String fSep = System.getProperty("file.separator");
        pathToSQLDBInitFile = pathToProject + fSep + "src" + fSep + "main" + fSep + "resources" + fSep + "db" + fSep + "initDBinline.sql";
        pathToProject = pathToProject.replaceAll("[\\\\/]", "/");
        url = "jdbc:hsqldb:file:" + pathToProject + "/src/main/resources/db/maindb";
    }
}
















