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

    public static Integer getHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        
        return Integer.valueOf(sdf.format(Calendar.getInstance().getTime()));
    }
    
    public static String getHourAndMinute() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        return String.valueOf(sdf.format(Calendar.getInstance().getTime()));
    }

    public static Integer getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        
        return Integer.valueOf(sdf.format(Calendar.getInstance().getTime()));
    }
    
    public static Integer getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        
        return Integer.valueOf(sdf.format(Calendar.getInstance().getTime()));
    }
    
    public static Integer getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        
        return Integer.valueOf(sdf.format(Calendar.getInstance().getTime()));
    }
}
