package com.limitless.sourav.swipeactionlayout.calenderview;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by sourav on 07-Nov-17.
 */

public class CLCalendarWeekUtil
{
    public static CLCalendarWeekDTO getWeekUtilCalender(Calendar[] sevenDays) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
        CLCalendarWeekDTO calenderBean = new CLCalendarWeekDTO(sevenDays);
        return calenderBean;
    }

    public static CLCalendarWeekDTO getNewCalender(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = (Date) calendar.getTime().clone();
        return getWeekUtilCalender( getWeek(date));
    }

    public static CLCalendarWeekDTO previousUnit(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = (Date) calendar.getTime().clone();
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTime(date);
        prevCalendar.add(Calendar.DATE, -7);

        return getNewCalender(prevCalendar.get(Calendar.YEAR), prevCalendar.get(Calendar.MONTH), prevCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public static CLCalendarWeekDTO nextUnit(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = (Date) calendar.getTime().clone();
        Calendar nextCalendar = Calendar.getInstance();
        nextCalendar.setTime(date);
        nextCalendar.add(Calendar.DATE, 7);
        return getNewCalender(nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH), nextCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public static Calendar[] getWeek(Date date) {
        Calendar[] week = new Calendar[7];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);// convert date object to calendar object
        week[0] = getCalenerInstance(calendar.getTime()); // Sunday
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Monday
        week[1] = getCalenerInstance(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Tuesday
        week[2] = getCalenerInstance(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Wednesday
        week[3] = getCalenerInstance(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Thursday
        week[4] = getCalenerInstance(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Friday
        week[5] = getCalenerInstance(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 1);// Saturday
        week[6] = calendar;

        return week;

    }

    private static Calendar getCalenerInstance(Date time)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }

}
