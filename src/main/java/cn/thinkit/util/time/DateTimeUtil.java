package cn.thinkit.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 */
public class DateTimeUtil {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private DateTimeUtil( ) {
    	// null
    }
    /**
     * 根据传入格式格式化当前时间
     *
     * @param newDateTimeFormatter 格式
     * @return 格式化后的时间字符串
     */
    public static String getNowDateTime(DateTimeFormatter newDateTimeFormatter) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (newDateTimeFormatter == null) {
            return dateTimeFormatter.format(dateTime);
        }
        return newDateTimeFormatter.format(dateTime);
    }

    /**
     * 根据传入格式格式化当前日期
     *
     * @param newDateFormatter 格式
     * @return 格式化后的日期字符串
     */
    public static String getNowDate(DateTimeFormatter newDateFormatter) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (newDateFormatter == null) {
            return dateFormatter.format(dateTime);
        }
        return newDateFormatter.format(dateTime);
    }

    /**
     * 根据传入格式对日期进行格式化
     *
     * @param date             日期
     * @param newDateFormatter 格式
     * @return 格式化后的时间字符串
     */
    public static String formatDate(LocalDate date, DateTimeFormatter newDateFormatter) {
        if (newDateFormatter == null) {
            return dateFormatter.format(date);
        }
        return newDateFormatter.format(date);
    }

    /**
     * 根据传入格式对时间进行格式化
     *
     * @param dateTime             时间
     * @param newDateTimeFormatter 格式
     * @return 格式化后的时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, DateTimeFormatter newDateTimeFormatter) {
        if (newDateTimeFormatter == null) {
            return dateTimeFormatter.format(dateTime);
        }
        return newDateTimeFormatter.format(dateTime);
    }

    /**
     * 增加/减少多少分钟
     *
     * @param dateTime 时间
     * @param mins     分钟数，可为负
     * @return 新的时间
     */
    public static LocalDateTime addNMinute(LocalDateTime dateTime, int mins) {
    	return  dateTime.plusMinutes(mins);
    }

    /**
     * 增加/减少多少天
     *
     * @param date 日期
     * @param days 天数，可为负
     * @return 新的日期
     */
    public static LocalDate addNDay(LocalDate date, int days) {
    	return date.plusDays(days);
    }

    /**
     * 判断时间1是否在时间2之前
     *
     * @param time1
     * @param time2
     * @return true：时间1在时间2之前；false：时间1在时间2之后
     */
    public static boolean isBefore(LocalTime time1, LocalTime time2) {
    	return time1.isBefore(time2);
    }

    /**
     * 判断日期1是否等于日期2
     *
     * @param date1
     * @param date2
     * @return true：日期1等于日期2；false：日期1不等于日期2
     */
    public static boolean isEquals(LocalDate date1, LocalDate date2) {
    	return  date1.isEqual(date2);
    }
}
