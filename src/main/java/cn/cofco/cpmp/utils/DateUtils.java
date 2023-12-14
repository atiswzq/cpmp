package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.log.LoggerManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static Logger logger = LoggerManager.getSysLog();

    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String date2String(Date date) {
        DateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date == null) {
            return "";
        }
        return simpleFormat.format(date);
    }

    public static String date2SimpleString(Date date) {
        DateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(date == null) {
            return "";
        }
        return simpleFormat.format(date);
    }


    public static String date2String(Date date, String pattern) {
        DateFormat simpleFormat = new SimpleDateFormat(pattern);
        if(date == null) {
            return "";
        }
        return simpleFormat.format(date);
    };

    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimeStamp(String dateStr) throws Exception {
        if (dateStr == null) {
            return null;
        }

        return Timestamp.valueOf(dateStr);
    }

    public static long getTimeStampLong(Timestamp timestamp) {
        return timestamp.getTime();
    }


    public static Timestamp getTimestamp(long timestampLong) {
        return new Timestamp(timestampLong);
    }

    public static Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return getYear(calendar);
    }

    public static int getDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return getDayOfMonth(calendar);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return getMonth(calendar);
    }

    public static String timestamp2String(Timestamp timestamp) {
        DateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(timestamp == null) {
            return "";
        }
        return simpleFormat.format(timestamp);
    }

    public static Timestamp string2timestamp(String time) {

        if(time == null) {
            return null;
        }
        String timestampStr = null;
        DateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat simpleFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            timestampStr = simpleFormat1.format(simpleFormat.parse(time));
        } catch (ParseException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        if(timestampStr == null) {
            return null;
        }
        return Timestamp.valueOf(timestampStr);
    }

}
