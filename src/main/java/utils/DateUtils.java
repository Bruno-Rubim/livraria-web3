package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String brazilianDateFormat(LocalDate date){
        DateTimeFormatter brazilianFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(brazilianFormat);
    }

}