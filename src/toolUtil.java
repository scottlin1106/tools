import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class toolUtil {
    public static Map<String, Object> result(String bodystr, String sub1, String sub2) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        String[] token = bodystr.split(sub1);

        for (int i = 0; i < token.length; i++) {
            if (token[i].split(sub2).length > 1) {
                resultMap.put(token[i].split(sub2)[0], token[i].substring(token[i].indexOf(sub2) + 1));
            } else {
                resultMap.put(token[i].split(sub2)[0], "");
            }
        }
        return resultMap;
    }


    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    public static String getYear() {
        LocalDate date = LocalDate.now();
        date.getYear();
        return String.valueOf(date.getYear());
    }

    public static String getStateDate(int day) {
        DateTimeFormatter format = DateTimeFormatter
                .ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime then = date.minusDays(day);
        return then.format(format);
    }

    public static Boolean validateDate(String day) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.parse(day);
        long day1 = now.toEpochDay();
        long day2 = date.toEpochDay();
        System.out.println("day2 - day1 = " +String.valueOf(day2 - day1));
        if (Math.abs(day2 - day1 )> 7) {
            return false;
        } else {
            return true;
        }
    }


}
