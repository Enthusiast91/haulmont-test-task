import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class Test {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        long longDateTime = localDateTime.getLong(ChronoField.EPOCH_DAY);

        System.out.println(longDateTime);
        System.out.println(localDate);
        System.out.println(localDateTime);
    }
}
