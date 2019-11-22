
import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;
import elemental.json.impl.JsonUtil;

import java.beans.Expression;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static String pathToSQLDBInitFile;
    public static String url;
    public static final String userName = "root";
    public static final String pass = "1234";
    public static final String jdbcDriver = "org.hsqldb.jdbcDriver";

    public static void main(String[] args) {
        String pathToProject = new File("").getAbsolutePath();
        String fSep = System.getProperty("file.separator");
        pathToSQLDBInitFile = pathToProject + fSep + "src" + fSep + "main" + fSep + "resources" + fSep + "db" + fSep + "initDBinline.sql";
        pathToProject = pathToProject.replaceAll("[\\\\/]", "/");
        url = "jdbc:hsqldb:file:" + pathToProject + "/src/main/resources/db/maindb";

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загурзить дрйвер БД");
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, userName, pass);
             Statement statement = connection.createStatement()) {

            initDatabase(statement);

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initDatabase(Statement statement) throws SQLException, FileNotFoundException {
        BufferedReader sqlFileReader = new BufferedReader(new FileReader(pathToSQLDBInitFile));
        Scanner scanner = new Scanner(sqlFileReader);
        String sqlLine = "";

        while (scanner.hasNextLine()) {
            sqlLine = scanner.nextLine();
            if (sqlLine.endsWith(";")) {
                sqlLine = sqlLine.substring(0, sqlLine.length() - 1);
            }
            statement.execute(sqlLine);
        }
    }

    List<Object> getSome(Statement statement, ResultSet rs) throws SQLException {
        List<Object> list = null;
        try {
            rs = statement.executeQuery("SELECT * FROM RECIPES");
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getUnit(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQLException " + e.getMessage());
            System.err.println("SQLException SQL state" + e.getSQLState());
            System.err.println("SQLException error code" + e.getErrorCode());
        } finally {
            if (rs != null) {
                rs.close();
            } else {
                System.out.println("Error reading database");
            }
        }
        return list;
    }

    public Recipe getUnit(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long doctorId = rs.getLong(2);
        long patientID = rs.getLong(3);
        String description = rs.getString(4);
        Date creationDate = rs.getDate(5);
        int validity = rs.getInt(6);
        int priority = rs.getInt(7);

        return new Recipe(id, doctorId, patientID, description, creationDate, validity);
    }


    public void getPacients(Statement statement, ResultSet rs) throws SQLException {
        try {
            rs = statement.executeQuery("SELECT * FROM RECIPES");
            while (rs.next()) {
                long id = rs.getLong(1);

                //Patient patient = new Patient()

            }
        } catch (SQLException e) {
            System.err.println("SQLException " + e.getMessage());
            System.err.println("SQLException SQL state" + e.getSQLState());
            System.err.println("SQLException error code" + e.getErrorCode());
        } finally {
            if (rs != null) {
                rs.close();
            } else {
                System.out.println("Error reading database");
            }
        }
    }

    public void updateRecipeDate(Statement statement, PreparedStatement prepStat, int index, Date creationDate) throws SQLException {
        prepStat.setDate(index, creationDate);
    }
}
















