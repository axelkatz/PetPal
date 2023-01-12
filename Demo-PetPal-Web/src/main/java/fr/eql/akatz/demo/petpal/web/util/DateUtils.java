package fr.eql.akatz.demo.petpal.web.util;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;


public class DateUtils {

    public static String fullDate(LocalDate date) {
        String month = date.getMonth().getDisplayName(
                TextStyle.FULL ,
                Locale.FRANCE
        );
        return date.getDayOfMonth() + " " + month + " " + date.getYear();
    }
}
