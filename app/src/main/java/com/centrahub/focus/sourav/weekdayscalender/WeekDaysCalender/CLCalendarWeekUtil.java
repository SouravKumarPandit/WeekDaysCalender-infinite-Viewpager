package com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sourav on 07-Nov-17.
 */

public class CLCalendarWeekUtil
{
    public static CLCalendarWeekDTO getWeekUtilCalender(Date date, Date[] sevenDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        CLCalendarWeekDTO calenderBean = new CLCalendarWeekDTO(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), sevenDays);
        return calenderBean;
    }

    public static CLCalendarWeekDTO getNewCalender(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = (Date) calendar.getTime().clone();
        return getWeekUtilCalender(calendar.getTime(), getWeek(date));
    }

    public static CLCalendarWeekDTO previousUnit(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = (Date) calendar.getTime().clone();
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTime(date);
        prevCalendar.add(Calendar.DATE, -7);
/*
        for (int i = 0; i < 7; i++) {
//            prevCalendar.get(Calendar.DAY_OF_MONTH);
            prevCalendar.add(Calendar.DAY_OF_MONTH, -1);
        }*/

        return getNewCalender(prevCalendar.get(Calendar.YEAR), prevCalendar.get(Calendar.MONTH), prevCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public static CLCalendarWeekDTO nextUnit(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = (Date) calendar.getTime().clone();
        Calendar nextCalendar = Calendar.getInstance();
        nextCalendar.setTime(date);
        nextCalendar.add(Calendar.DATE, 7);
/*        for (int i = 0; i < 7; i++) {
//            prevCalendar.get(Calendar.DAY_OF_MONTH);
            nextCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }*/
        return getNewCalender(nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH), nextCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public static Date[] getWeek(Date date) {
        Date[] week = new Date[7];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);// convert date object to calendar object
        int day = calendar.get(Calendar.DAY_OF_WEEK); // day (Sunday = 1)1 to 7

        week[0] = calendar.getTime(); // Sunday
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Monday
        week[1] = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Tuesday
        week[2] = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Wednesday
        week[3] = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Thursday
        week[4] = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Friday
        week[5] = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Saturday
        week[6] = calendar.getTime();

        return week;

    }

}
