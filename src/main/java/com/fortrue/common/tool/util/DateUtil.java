package com.fortrue.common.tool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    /**
     * 用于date转string
     */
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>().withInitial(SimpleDateFormat::new);


    /**
     * 字符串和日期格式map，用于localDate或者localDateTime转string
     * 不能手动修改value
     */
    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new HashMap<>();

    private DateUtil() {
    }

    /**
     * date -> string
     * @param date
     * @param datePattern
     * @return
     */
    public static final String date2String(Date date, String datePattern){

        SimpleDateFormat sdf = SIMPLE_DATE_FORMAT_THREAD_LOCAL.get();
        sdf.applyPattern(datePattern);
        return sdf.format(date);

    }

    /**
     * string -> date
     * @param dateTime
     * @param datePattern
     * @return
     */
    public static final Date parseDate(String dateTime, String datePattern) throws ParseException {
        SimpleDateFormat sdf = SIMPLE_DATE_FORMAT_THREAD_LOCAL.get();
        sdf.applyPattern(datePattern);
        return sdf.parse(dateTime);
    }

    /**
     * localDateTime -> string
     * @param localDateTime
     * @param datePattern
     * @return
     */
    public static final String date2String(LocalDateTime localDateTime, String datePattern){

        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(datePattern);

        if(dateTimeFormatter == null){
            dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
            DATE_TIME_FORMATTER_MAP.put(datePattern, dateTimeFormatter);
        }

        return localDateTime.format(dateTimeFormatter);

    }

    /**
     * string -> localDateTime
     * @param dateTimeString
     * @param datePattern
     * @return
     */
    public static final LocalDateTime parseLocalDateTime(String dateTimeString, String datePattern){

        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(datePattern);

        if(dateTimeFormatter == null){
            dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
            DATE_TIME_FORMATTER_MAP.put(datePattern, dateTimeFormatter);
        }

        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

    /**
     * localDate -> string
     * @param localDate
     * @param datePattern
     * @return
     */
    public static final String date2String(LocalDate localDate, String datePattern){

        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(datePattern);

        if(dateTimeFormatter == null){
            synchronized (DateUtil.class) {
                dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(datePattern);
                if(dateTimeFormatter == null) {
                    dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
                    DATE_TIME_FORMATTER_MAP.put(datePattern, dateTimeFormatter);
                }
            }
        }

        return localDate.format(dateTimeFormatter);

    }

    /**
     * string -> localDate
     * @param dateString
     * @param datePattern
     * @return
     */
    public static final LocalDate parseLocalDate(String dateString, String datePattern){

        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(datePattern);

        if(dateTimeFormatter == null){
            synchronized (DateUtil.class) {
                dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(datePattern);
                if(dateTimeFormatter == null) {
                    dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
                    DATE_TIME_FORMATTER_MAP.put(datePattern, dateTimeFormatter);
                }
            }
        }

        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    /**
     * 获取一天开始的时间
     * @param localDateTime
     * @return
     */
    public static final LocalDateTime getDayStart(LocalDateTime localDateTime, long months, long days, long hours){
        return localDateTime.plusMonths(months).plusDays(days).plusHours(hours)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取一天开始的时间
     * @param date
     * @return
     */
    public static final Date getDayStart(Date date, int months, int days, int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }


}
