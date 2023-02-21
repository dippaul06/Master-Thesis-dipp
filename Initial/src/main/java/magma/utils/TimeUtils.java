package magma.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.time.Instant.ofEpochMilli;
import static java.util.concurrent.TimeUnit.*;

public enum TimeUtils {
    ;

    static ZoneId zone = TimeZone.getDefault().toZoneId();

//    static final DateFormat DATE_FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

    public static long timeToLong(LocalDateTime time) {
        return Timestamp.valueOf(time).getTime();
    }


    public static int timeToInt(LocalDateTime time) {
        return (int) (timeToLong(time)/1_000L);
    }


    public static Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return Timestamp.valueOf(dateToConvert);
    }


    public static LocalDateTime longToLocalDateTime(long milliSeconds) {
        return LocalDateTime.ofInstant(ofEpochMilli(milliSeconds), zone);
    }


    public static LocalDateTime secondsToLocalDateTime(int milliSeconds) {
        return LocalDateTime.ofInstant(ofEpochMilli(milliSeconds * 1_000L), zone);
    }


    public static int nowToInt() {
        return (int) (Instant.now().toEpochMilli()/1_000L);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long diffInSeconds(Date from, Date to) {
        long diffInMillies = to.getTime() - from.getTime();
        return SECONDS.convert(diffInMillies, MILLISECONDS);
    }

    public static long diffInMinutes(Date from, Date to) {
        long diffInMillies = to.getTime() - from.getTime();
        return MINUTES.convert(diffInMillies, MILLISECONDS);
    }

    public static long diffInHours(Date from, Date to) {
        long diffInMillies = to.getTime() - from.getTime();
        return HOURS.convert(diffInMillies, MILLISECONDS);
    }

    public static long diffInDays(Date from, Date to) {
        long diffInMillies = to.getTime() - from.getTime();
        return DAYS.convert(diffInMillies, MILLISECONDS);
    }

    public static long currentTimeStamp() {
        return Instant.now().toEpochMilli();
    }

    public static String getFormattedDateForBotometer(Date date) {
        var sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public static Date parseMongoDate(String val) {
        Date date = null;
        try {
            date = DATE_FORMAT.parse(val);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + val);
            e.printStackTrace();
        }
        return date;
    }

    public static Date addHoursToJavaUtilDate(Date date, int hours) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date addHour(Date date) {
        return addHoursToJavaUtilDate(date, 1);
    }

    public static Date addDay(Date date) {
        return addHoursToJavaUtilDate(date, 24);
    }

    public static Date addWeek(Date date) {
        return addHoursToJavaUtilDate(date, 24 * 7);
    }
}