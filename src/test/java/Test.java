import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class Test {
    public static void main(String[] args) {
        String url = "jdbc:hsqldb:file:C:/Java/IdeaProjects/haulmont-test-task/src/main/resources/db/maindb";
        String userName = "root";
        String pass = "1234";

        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загурзить дрйвер БД");
            e.printStackTrace();
            System.exit(1);
        }

        try(Connection connection = DriverManager.getConnection(url, userName, pass)) {
            System.out.println("Connection successful");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
























