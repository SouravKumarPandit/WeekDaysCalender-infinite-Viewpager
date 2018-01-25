package com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender;

import java.util.Date;

/**
 * Created by sourav on 07-Nov-17.
 */

public class CLCalendarWeekDTO
{
    private int year;
    private int month;
    private int day;

    Date[] beanDate;


    public CLCalendarWeekDTO(int year, int month, int day, Date[] dateArray)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        beanDate = dateArray;

    }


    public Date[] getBeanDate()
    {
        return beanDate;
    }

    public void setBeanDate(Date[] beanDate)
    {
        this.beanDate = beanDate;
    }


    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    @Override
    public String toString()
    {
        return String.format("%d-%d-%d", year, month + 1, day);
    }

    public int compareTo(CLCalendarWeekDTO calenderBean)
    {
        if (year != calenderBean.getYear())
        {
            return year > calenderBean.getYear() ? 1 : -1;
        }
        else if (month != calenderBean.getMonth())
        {
            return month > calenderBean.getMonth() ? 1 : -1;
        }
        else if (day != calenderBean.getDay())
        {
            return day > calenderBean.getDay() ? 1 : -1;
        }
        return 0;
    }


}
