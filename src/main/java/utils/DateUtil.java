package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public static boolean loggedInToday() {
        return false;
    }

    public static boolean sameDate(Calendar c0, Calendar c1) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(c0.getTime()).equals(dateFormat.format(c1.getTime()));
    }

}
