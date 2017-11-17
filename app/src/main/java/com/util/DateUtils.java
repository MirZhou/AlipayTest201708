package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date对象通用类
 *
 * @author 周光兵
 */
public class DateUtils {
    /**
     * Determines whether the current date is between the start date and the end date.
     *
     * @param startTime Start date.
     * @param endTime   End date.
     * @return Critical result.
     */
    public static boolean currentDateBetween(Date startTime, Date endTime) {
        boolean flag;

        Date currentDate = new Date(); // Get current time.

        // Activity end time plus 1 day. Used for time comparison.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        endTime = calendar.getTime();

        int startTimeCompareValue = currentDate.compareTo(startTime); // Current time and activity start time comparison result.
        int endTimeCompareValue = currentDate.compareTo(endTime); // Current time and activity end time of the activity.

        // Filter promotions that are not active during the event.
        flag = (startTimeCompareValue != -1) &&
                (endTimeCompareValue != 1);

        return flag;
    }

    /**
     * The date is converted to a date string.
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 时间日期转换为字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return 转换后的字符串
     */
    public static String formatDatetime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转换为指定格式字符串
     *
     * @param date   日期
     * @param format 格式
     * @return 转换后的字符串
     */
    public static String formatDate(Date date, String format) {
        String value = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        value = dateFormat.format(date);

        return value;
    }

    /**
     * The string is converted to a date.
     *
     * @param text
     * @return
     * @throws ParseException
     */
    public static Date getDate(String text) throws ParseException {
        return parse(text, "yyy-MM-dd");
    }

    /**
     * The string is converted to a datetime type.
     *
     * @param text
     * @return
     * @throws ParseException
     */
    public static Date getDateTime(String text) throws ParseException {
        return parse(text, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * The string is converted to the data of the specified format.
     *
     * @param text
     * @param pattern the pattern describing the date and time format
     * @return
     * @throws ParseException
     */
    public static Date parse(String text, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(text);
    }

} // end public class DateUtils