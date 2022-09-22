package com.github.scipioutils.core.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 *
 * @author Alan Min
 * @since 2020/9/29
 */
public class TimeUtils {

    /**
     * 默认时间日期格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    //============================================= ↓↓↓↓↓↓ LocalDateTime ↓↓↓↓↓↓ =============================================

    /**
     * 字符串转日期
     *
     * @param pattern    字符串日期格式
     * @param dateStr    待转换的字符串数据
     * @param isOnlyDate 是否只有日期（而没有时分秒），为true代表是
     * @return 转换好的日期对象（如果转换失败则为null）
     */
    public static LocalDateTime asTime(String dateStr, String pattern, boolean isOnlyDate) {
        if (isOnlyDate) {
            pattern += "[['T'HH][:mm][:ss]]";
        }
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(pattern)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * 字符串转日期（默认时间格式）
     *
     * @param dateStr 待转换的字符串数据
     * @return 转换好的日期对象（如果转换失败则为null）
     */
    public static LocalDateTime asTime(String dateStr) {
        return asTime(DEFAULT_DATE_TIME_FORMAT, dateStr, false);
    }

    /**
     * 日期转字符串
     *
     * @param pattern 字符串日期格式
     * @param date    待转换的日期对象
     * @return 转换好的字符串（如果转换失败则为null）
     */
    public static String asString(LocalDateTime date, String pattern) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }

    /**
     * 日期转字符串（默认时间格式）
     *
     * @param date 待转换的日期对象
     * @return 转换好的字符串（如果转换失败则为null）
     */
    public static String asString(LocalDateTime date) {
        return asString(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 时间戳转LocalDateTime对象
     *
     * @param timestamp  时间戳（毫秒级）
     * @param zoneOffset 时区
     * @return LocalDateTime对象
     */
    public static LocalDateTime getByTimestamp(long timestamp, ZoneOffset zoneOffset) {
        return Instant.ofEpochMilli(timestamp).atZone(zoneOffset).toLocalDateTime();
    }

    /**
     * 时间戳转LocalDateTime对象，默认时区为东八区
     *
     * @param timestamp 时间戳（毫秒级）
     * @return LocalDateTime对象
     */
    public static LocalDateTime getByTimestamp(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * LocalDateTime对象转时间戳
     *
     * @param obj        LocalDateTime对象
     * @param zoneOffset 时区偏移量(可以视为就是时区)
     * @return 时间戳（毫秒级）
     */
    public static long getTimestamp(LocalDateTime obj, ZoneOffset zoneOffset) {
        return obj.toInstant(zoneOffset).toEpochMilli();
    }

    /**
     * LocalDateTime对象转时间戳，默认时区为系统时区
     *
     * @param obj LocalDateTime对象
     * @return 时间戳（毫秒级）
     */
    public static long getTimestamp(LocalDateTime obj) {
        return obj.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //============================================= ↓↓↓↓↓↓ Date ↓↓↓↓↓↓ =============================================

    /**
     * 字符串转日期
     *
     * @param pattern 字符串日期格式
     * @param dateStr 待转换的字符串数据
     * @return 转换好的日期对象（如果转换失败则为null）
     */
    public static Date asDate(String pattern, String dateStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期（默认日期格式）
     *
     * @param dateStr 待转换的字符串数据
     * @return 转换好的日期对象（如果转换失败则为null）
     */
    public static Date asDate(String dateStr) {
        return asDate(DEFAULT_DATE_FORMAT, dateStr);
    }

    /**
     * 日期转字符串
     *
     * @param pattern 字符串日期格式
     * @param date    待转换的日期对象
     * @return 转换好的字符串（如果转换失败则为null）
     */
    public static String asString(String pattern, Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 日期转字符串（默认日期格式）
     *
     * @param date 待转换的日期对象
     * @return 转换好的字符串（如果转换失败则为null）
     */
    public static String asString(Date date) {
        return asString(DEFAULT_DATE_FORMAT, date);
    }

    //============================================= ↓↓↓↓↓↓ LocalDate ↓↓↓↓↓↓ =============================================

    /**
     * 字符串转{@link LocalDate}
     *
     * @param str           字符串
     * @param formatPattern 日期格式
     * @return {@link LocalDate}对象
     */
    public static LocalDate asLocalDate(String str, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return LocalDate.parse(str, formatter);
    }

    /**
     * 字符串转{@link LocalDate}，采用默认日期格式
     *
     * @param str 字符串
     * @return {@link LocalDate}对象
     */
    public static LocalDate asLocalDate(String str) {
        return asLocalDate(str, DEFAULT_DATE_FORMAT);
    }

    /**
     * {@link LocalDate}转字符串
     *
     * @param date          {@link LocalDate}对象
     * @param formatPattern 日期格式
     * @return 转换后的字符串
     */
    public static String asString(LocalDate date, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return date.format(formatter);
    }

    /**
     * {@link LocalDate}转字符串，采用默认日期格式
     *
     * @param date 字符串
     * @return 转换后的字符串
     */
    public static String asString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return date.format(formatter);
    }

    //============================================= ↓↓↓↓↓↓ Others ↓↓↓↓↓↓ =============================================

    /**
     * 获取当前时间的字符串
     *
     * @param pattern 日期格式
     */
    public static String now(String pattern) {
        return asString(LocalDateTime.now(), pattern);
    }

    /**
     * 获取当前时间的字符串（默认时间格式）
     */
    public static String now() {
        return asString(LocalDateTime.now());
    }

    /**
     * 猜测并构成日期格式，目前支持以下格式：
     * <ul>
     *     <li> yyyy-MM-dd </li>
     *     <li> yyyy/MM/dd </li>
     *     <li> yyyy.MM.dd </li>
     *     <li> yyyyMMdd </li>
     * </ul>
     *
     * @param str 待检测字符串
     * @return 日期格式字符串
     * @throws IllegalArgumentException 检测的字符串不在支持范围内
     */
    public static String guessPattern(String str) throws IllegalArgumentException {
        String pattern0 = getDatePattern(str, "-");
        if (pattern0 != null) {
            return pattern0;
        }
        String pattern1 = getDatePattern(str, "/");
        if (pattern1 != null) {
            return pattern1;
        }
        String pattern2 = getDatePattern(str, "");
        if (pattern2 != null) {
            return pattern2;
        }
        String pattern3 = getDatePattern(str, "\\.");
        if (pattern3 != null) {
            return pattern3;
        }
        throw new IllegalArgumentException("Illegal date string: \"" + str + "\"");
    }

    private static String getDatePattern(String str, String split) {
        Pattern pattern = Pattern.compile("(\\d{4})" + split + "(\\d{1,2})" + split + "(\\d{1,2})");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            String yearPattern = "yyyy", monthPattern, dayPattern;
            //月份格式
            String month = matcher.group(2);
            monthPattern = (month.length() == 1 ? "M" : "MM");
            //日格式
            String day = matcher.group(3);
            dayPattern = (day.length() == 1 ? "d" : "dd");
            //合成最终的格式
            return yearPattern + split + monthPattern + split + dayPattern;
        } else {
            return null;
        }
    }

}
