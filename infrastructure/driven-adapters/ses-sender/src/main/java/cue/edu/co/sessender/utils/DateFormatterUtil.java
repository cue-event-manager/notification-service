package cue.edu.co.sessender.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatterUtil {
    private DateFormatterUtil(){}

    public static String formatDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d 'de' MMMM 'de' yyyy, h:mm a", new Locale("es", "CO"));

        return date.format(formatter);
    }
}
