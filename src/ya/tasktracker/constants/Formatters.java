package ya.tasktracker.constants;

import java.time.format.DateTimeFormatter;

public class Formatters {
    public static String dateTimeFormat = "yyyy.MM.dd HH:mm VV";
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
}
