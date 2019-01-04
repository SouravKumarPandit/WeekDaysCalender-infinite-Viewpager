package com.limitless.sourav.swipeactionlayout.calenderview;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sourav on 07-Nov-17.
 */

public class CLCalendarWeekDTO
{
    private Calendar[] beanDate;
   public CLCalendarWeekDTO(Calendar[] dateArray)
    {
        beanDate = dateArray;
    }


    public Calendar[] getBeanDate()
    {
        return beanDate;
    }

    public void setBeanDate(Calendar[] beanDate)
    {
        this.beanDate = beanDate;
    }


    @Override
    public String toString()
    {
        return String.format("%d-%d-%d", getBeanDate()[0].get(Calendar.DATE), getBeanDate()[0].get(Calendar.MONTH)+ 1, getBeanDate()[0].get(Calendar.YEAR));
    }
  /*  public int compareTo(int comparePos)
    {
        if (comparePos<0)
            return -1;
        else
        if (comparePos>6)
            return +1;
        else return 0;
    }*/

}
