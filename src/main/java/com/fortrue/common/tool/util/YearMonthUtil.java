package com.fortrue.common.tool.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class YearMonthUtil {

    private YearMonthUtil() {
    }

    /**
     * 获取某年某月的天数
     * @param date
     * @return
     */
    public static final int getMonthDays(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return getMonthDays(month, year);

    }


    /**
     * 获取某年某月的天数
     * @param localDate
     * @return
     */
    public static final int getMonthDays(LocalDate localDate){

        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        return getMonthDays(month, year);

    }

    /**
     * 获取某年某月的天数
     * @param localDateTime
     * @return
     */
    public static final int getMonthDays(LocalDateTime localDateTime){

        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        return getMonthDays(month, year);

    }


    /**
     * 获取某年某月的天数
     * @param month
     * @param year
     * @return
     */
    public static final int getMonthDays(int month, int year){
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                throw new IllegalArgumentException("月份不正确！");
        }
    }


    /**
     * 获取某年的天数
     * @param date
     * @return
     */
    public static final int getYearDays(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return isLeapYear(year) ? 366 : 365;

    }


    /**
     * 获取某年的天数
     * @param localDate
     * @return
     */
    public static final int getYearDays(LocalDate localDate){

        int year = localDate.getYear();
        return isLeapYear(year) ? 366 : 365;

    }

    /**
     * 获取某年的天数
     * @param localDateTime
     * @return
     */
    public static final int getYearDays(LocalDateTime localDateTime){

        int year = localDateTime.getYear();
        return isLeapYear(year) ? 366 : 365;

    }




    /**
     * 获取某年的天数
     * @param year
     * @return
     */
    public static final int getYearDays(int year){

        return isLeapYear(year) ? 366 : 365;

    }

    /**
     * 判断是否为闰年
     * @param year
     * @return
     */
    public static final boolean isLeapYear(int year){
        return (year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }


}
