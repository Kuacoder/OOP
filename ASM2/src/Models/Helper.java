package Models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Helper {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (Exception e) {
            return new Date();
        }
    }
    public static String getHourMinuteFromDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%s : %s",
                hour < 10 ? String.format("0%d", hour) : String.format("%d", hour),
                minute < 10 ? String.format("0%d", minute) : String.format("%d", minute)
        );
    }
    public static Boolean compare2Dates(Date date1, Date date2) {
        TimeZone timeZone = Calendar.getInstance().getTimeZone();
        Calendar calendar1 = Calendar.getInstance(timeZone);
        Calendar calendar2 = Calendar.getInstance(timeZone);
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return  calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                &&calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                &&calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }
    public static String convertDateToString(Date date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return dtf.format(localDate);
    }
}
