package com.limitless.sourav.swipeactionlayout.calenderview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


import com.limitless.sourav.swipeactionlayout.R;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by sourav on 07-Nov-17.
 */

public class CLCalendarView extends ViewPager
{
    private CLCalendarWeekDTO[] calenderWeekBeans;
    private CLCalendarItemView[] calenderItemViews;
    private int headerTextColor;
    private float headerTextSize;
    private int dateTextColor;
    private float dateTextSize;
    private int selectBackColor;
    private int selectTextColor;
    private float selectTextSize;
    public static Calendar selectedDate = null;
    public CLCalendarView(Context context)
    {
        super(context);
        init(null);
    }

    public CLCalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public CLCalendarView(Context context, Calendar selectedDate)
    {
        super(context, null);
        CLCalendarView.selectedDate = selectedDate;
        init(null);
    }

    private void init(AttributeSet attrs)
    {
        if (selectedDate == null)
            selectedDate = Calendar.getInstance();

        initAttrs(attrs);
        initCalenderView();
        setPagerAdapter();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        ScaleAnimation anim = new ScaleAnimation(0,1,0,1);
        TranslateAnimation anim=new TranslateAnimation(getScreenWidth(),0,0,0);
        anim.setDuration(200);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    }


   /* @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        TranslateAnimation anim = new TranslateAnimation(0, -getScreenWidth(), 0, 0);
        anim.setDuration(300);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    }*/


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    private void setPagerAdapter() {
//        setAdapter(null);
        setAdapter(new PagerAdapter()
        {
            @Override
            public int getCount()
            {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object)
            {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position)
            {
                int iPosition = position % 3;
                CLCalendarItemView calenderItemView = calenderItemViews[iPosition];
                calenderItemView.setDate(calenderWeekBeans[iPosition].getBeanDate()[0].get(Calendar.YEAR), calenderWeekBeans[iPosition].getBeanDate()[0].get(Calendar.MONTH), calenderWeekBeans[iPosition].getBeanDate()[0].get(Calendar.DATE));
                if (calenderItemView.getParent() != null)
                {
                    container.removeView(calenderItemView);
                }
                container.addView(calenderItemView);
                return calenderItemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
            }
        });
        addOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                setPositionCalender(calenderWeekBeans[position % 3], position);
                if (getAdapter() != null)
                    getAdapter().notifyDataSetChanged();
                if (onCalenderPageChangeListener != null)
                {

                    onCalenderPageChangeListener.onChange(getCurrentCalender(),calenderItemViews[0].getDayOfWeek(),calenderItemViews[0].getXPlotLocation(),calenderItemViews[0].getYPlotLocation());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        int currentPosition = Integer.MAX_VALUE / 2;
        setCurrentItem(currentPosition);
    }

    private void initAttrs(AttributeSet attrs)
    {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalenderView);
        headerTextColor = typedArray.getColor(R.styleable.CalenderView_headerTextColorCV, Color.GRAY);
        headerTextSize = typedArray.getFloat(R.styleable.CalenderView_headerTextSizeCV, 14);
        dateTextColor = typedArray.getColor(R.styleable.CalenderView_dateTextColorCV, Color.BLACK);
        dateTextSize = typedArray.getFloat(R.styleable.CalenderView_dateTextSizeCV, 15);
        selectBackColor = typedArray.getColor(R.styleable.CalenderView_selectBackColorCV, 0xFFD81B60);
        selectTextColor = typedArray.getColor(R.styleable.CalenderView_selectTextColorCV, Color.WHITE);
        selectTextSize = typedArray.getFloat(R.styleable.CalenderView_dateTextSizeCV, 15);
        typedArray.recycle();
    }

    private void initCalenderView()
    {
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek())
        {
            cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        CLCalendarWeekDTO calenderWeekBean = CLCalendarWeekUtil.getWeekUtilCalender(CLCalendarWeekUtil.getWeek(cal.getTime()));
        calenderWeekBean = CLCalendarWeekUtil.getNewCalender(calenderWeekBean.getBeanDate()[0].get(Calendar.YEAR), calenderWeekBean.getBeanDate()[0].get(Calendar.MONTH), calenderWeekBean.getBeanDate()[0].get(Calendar.DATE));
        calenderWeekBeans = new CLCalendarWeekDTO[]{calenderWeekBean, CLCalendarWeekUtil.nextUnit(calenderWeekBean.getBeanDate()[0].get(Calendar.YEAR), calenderWeekBean.getBeanDate()[0].get(Calendar.MONTH), calenderWeekBean.getBeanDate()[0].get(Calendar.DATE))
                , CLCalendarWeekUtil.previousUnit(calenderWeekBean.getBeanDate()[0].get(Calendar.YEAR), calenderWeekBean.getBeanDate()[0].get(Calendar.MONTH), calenderWeekBean.getBeanDate()[0].get(Calendar.DATE))};
        calenderItemViews = new CLCalendarItemView[calenderWeekBeans.length];
        for (int i = 0; i < calenderItemViews.length; i++)
        {
            CLCalendarItemView calenderItemView = calenderItemViews[i] == null ? new CLCalendarItemView(getContext()) : calenderItemViews[i];
            calenderItemView.setHeaderTextColor(headerTextColor);
            calenderItemView.setHeaderTextSize(headerTextSize);
            calenderItemView.setDateTextColor(dateTextColor);
            calenderItemView.setDateTextSize(dateTextSize);
            calenderItemView.setSelectBackColor(selectBackColor);
            calenderItemView.setSelectTextColor(selectTextColor);
            calenderItemView.setSelectTextSize(selectTextSize);
            calenderItemView.setOnItemSelectListener(new CLCalendarItemView.OnItemSelectListener()
            {
                @Override
                public void onSelectDate(Calendar calendar, float xPosition, float yPosition) {
                    if (onItemSelectListener != null)
                    {
                        onItemSelectListener.onSelectDate(calendar,xPosition,yPosition);
                        selectedDate = calendar;
                    }
                }


            });
            calenderItemViews[i] = calenderItemView;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (getAdapter() != null)
        {
            CLCalendarItemView calenderItemView = (CLCalendarItemView) getChildAt(0);
            if (calenderItemView != null)
            {
                height = calenderItemView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    public Calendar getSelectedDate()
    {
        return selectedDate;
    }

    public void setNextSelectedDate()
    {
        long ltime = selectedDate.getTime().getTime() + 86400000;//1*24*60*60*1000  adding one day;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(ltime));
        setSelectDate(1, calendar);

    }

    public void setPreviousSelectedDate()
    {
        long ltime = selectedDate.getTime().getTime() - 86400000;//1*24*60*60*1000  subtracting one day;;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(ltime));
        setSelectDate(-1, calendar);
    }

    private void setPositionCalender(CLCalendarWeekDTO calenderWeekBean, int position)
    {
        position = position % 3;
        // the current month
        calenderWeekBeans[position] = calenderWeekBean;
        // position after the next month
        calenderWeekBeans[(position + 1) % 3] = CLCalendarWeekUtil.nextUnit(calenderWeekBean.getBeanDate()[0].get(Calendar.YEAR), calenderWeekBean.getBeanDate()[0].get(Calendar.MONTH), calenderWeekBean.getBeanDate()[0].get(Calendar.DATE));
        // the previous one is last month
        calenderWeekBeans[(position + 2) % 3] = CLCalendarWeekUtil.previousUnit(calenderWeekBean.getBeanDate()[0].get(Calendar.YEAR), calenderWeekBean.getBeanDate()[0].get(Calendar.MONTH), calenderWeekBean.getBeanDate()[0].get(Calendar.DATE));
        for (int i = 0; i < calenderWeekBeans.length; i++)
        {
            calenderItemViews[i].setDate(calenderWeekBeans[i].getBeanDate()[0].get(Calendar.YEAR), calenderWeekBeans[i].getBeanDate()[0].get(Calendar.MONTH), calenderWeekBeans[i].getBeanDate()[0].get(Calendar.DATE));
            Calendar date = CLCalendarView.selectedDate;
            int sldate = 0, slMonth = 0, slYear = 0;
            if (date != null)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date.getTime());
                sldate = calendar.get(Calendar.DATE);
                slMonth = calendar.get(Calendar.MONTH);
                slYear = calendar.get(Calendar.YEAR);
                Calendar[] dates = calenderWeekBean.getBeanDate();
                for (int sl = 0; sl < dates.length; sl++)
                {
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(dates[sl].getTime());
                    int sldate2 = calendar2.get(Calendar.DATE);
                    int slMonth2 = calendar2.get(Calendar.MONTH);
                    int slYear2 = calendar2.get(Calendar.YEAR);
                    if (sldate == sldate2 && slMonth == slMonth2 & slYear == slYear2)
                    {
                        calenderItemViews[i].setSelectDate(sldate, slMonth, slYear);
                    }
                }
            }

        }
    }

    public CLCalendarWeekDTO getCurrentCalender()
    {
        return calenderWeekBeans[getCurrentItem() % 3];
    }

    public Calendar getSelectDate()
    {
        return selectedDate;
    }

    public void setSelectDate(Calendar calender)
    {
        setSelectDate(0, calender);
    }
    public void selectCurrentDate()
    {
        setSelectDate(0, Calendar.getInstance());
//        setPagerAdapter();
    }
    public void setSelectDate(int sVal, Calendar calender)
    {
        setCurrentCalender(CLCalendarItemView.iSelectedPosition += sVal, calender);
    }

    public void setCurrentCalender(int comparePos, Calendar calender)
    {
        if (comparePos < 0)
        {
            setCurrentItem(getCurrentItem() - 1);
            CLCalendarItemView.iSelectedPosition = 6;
        } else if (comparePos > 6)
        {
            setCurrentItem(getCurrentItem() + 1);
            CLCalendarItemView.iSelectedPosition = 0;
        }


        if (calenderItemViews[0].getOnItemSelectListener() != null)
            calenderItemViews[0].getOnItemSelectListener().onSelectDate(calender,0,0);
        calenderItemViews[0].invalidate();
        calenderItemViews[1].invalidate();
        calenderItemViews[2].invalidate();
        calenderItemViews[0].setSelectDate(calender.get(Calendar.DATE), calender.get(Calendar.MONTH), calender.get(Calendar.YEAR));
        selectedDate = calender;

    }

    private CLCalendarItemView.OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(CLCalendarItemView.OnItemSelectListener onItemSelectListener)
    {
        this.onItemSelectListener = onItemSelectListener;
    }

    private OnCalenderPageChangeListener onCalenderPageChangeListener;

    public void setOnCalenderPageChangeListener(OnCalenderPageChangeListener onCalenderPageChangeListener)
    {
        this.onCalenderPageChangeListener = onCalenderPageChangeListener;
    }

    public interface OnCalenderPageChangeListener
    {
        void onChange(CLCalendarWeekDTO calenderWeekBean,int dayOfWeek,float xPositionPoint,float yPositionPoint);
    }


}
