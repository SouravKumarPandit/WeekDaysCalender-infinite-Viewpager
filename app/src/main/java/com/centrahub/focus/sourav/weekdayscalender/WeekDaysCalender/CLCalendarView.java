package com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.centrahub.focus.sourav.weekdayscalender.R;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by sourav on 07-Nov-17.
 */

public class CLCalendarView extends ViewPager {
    private CLCalendarWeekDTO[] calenderWeekBeans;
    private CLCalendarItemView[] calenderItemViews;
    private int headerTextColor;
    private float headerTextSize;
    private int dateTextColor;
    private float dateTextSize;
    private int selectBackColor;
    private int selectTextColor;
    private float selectTextSize;
    public static Date MY_SELECTED_DATE;

    public CLCalendarView(Context context) {
        this(context, null);
    }

    public CLCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CLCalendarView.MY_SELECTED_DATE=new Date();
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        initCalenderView();
        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                position = position % 3;
                CLCalendarItemView calenderItemView = calenderItemViews[position];
                calenderItemView.setDate(calenderWeekBeans[position].getYear(), calenderWeekBeans[position].getMonth(), calenderWeekBeans[position].getDay());
                calenderItemView.setSelectDate(Calendar.getInstance().get(Calendar.DATE));
                if (calenderItemView.getParent() != null) {
                    container.removeView(calenderItemView);
                }
                container.addView(calenderItemView);
                return calenderItemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
        });
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPositionCalender(calenderWeekBeans[position % 3], position);
                getAdapter().notifyDataSetChanged();
                if (onCalenderPageChangeListener != null) {
                    onCalenderPageChangeListener.onChange(getCurrentCalender());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        int currentPosition = Integer.MAX_VALUE / 2;
        if (currentPosition % 3 == 2) {
            currentPosition++;
        } else if (currentPosition % 3 == 1) {
            currentPosition--;
        }
        setCurrentItem(currentPosition);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalenderView);
        headerTextColor = typedArray.getColor(R.styleable.CalenderView_headerTextColorCV, Color.GRAY);
        headerTextSize = typedArray.getFloat(R.styleable.CalenderView_headerTextSizeCV, 12);
        dateTextColor = typedArray.getColor(R.styleable.CalenderView_dateTextColorCV, Color.BLACK);
        dateTextSize = typedArray.getFloat(R.styleable.CalenderView_dateTextSizeCV, 15);
        selectBackColor = typedArray.getColor(R.styleable.CalenderView_selectBackColorCV, getResources().getColor(R.color.colorAccent));
        selectTextColor = typedArray.getColor(R.styleable.CalenderView_selectTextColorCV, Color.WHITE);
        selectTextSize = typedArray.getFloat(R.styleable.CalenderView_dateTextSizeCV, 17);
        typedArray.recycle();
    }

    /**
     * 初始化日历数据
     */
    private void initCalenderView() {
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
            cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        CLCalendarWeekDTO calenderWeekBean = CLCalendarWeekUtil.getWeekUtilCalender(cal.getTime(), CLCalendarWeekUtil.getWeek(cal.getTime()));
        int iyear = calenderWeekBean.getYear();
        int imonths = calenderWeekBean.getMonth();
        int iday = calenderWeekBean.getDay();
        calenderWeekBean = CLCalendarWeekUtil.getNewCalender(calenderWeekBean.getYear(), calenderWeekBean.getMonth(), calenderWeekBean.getDay());

        //创建当月、下月、上月日历数据
        calenderWeekBeans = new CLCalendarWeekDTO[]{calenderWeekBean, CLCalendarWeekUtil.nextUnit(calenderWeekBean.getYear(), calenderWeekBean.getMonth(), calenderWeekBean.getMonth())
                , CLCalendarWeekUtil.previousUnit(calenderWeekBean.getYear(), calenderWeekBean.getMonth(), calenderWeekBean.getMonth())};
        calenderItemViews = new CLCalendarItemView[calenderWeekBeans.length];
        for (int i = 0; i < calenderItemViews.length; i++) {
            //初始化日历控件
            CLCalendarItemView calenderItemView = calenderItemViews[i] == null ? new CLCalendarItemView(getContext()) : calenderItemViews[i];
            calenderItemView.setHeaderTextColor(headerTextColor);
            calenderItemView.setHeaderTextSize(headerTextSize);
            calenderItemView.setDateTextColor(dateTextColor);
            calenderItemView.setDateTextSize(dateTextSize);
            calenderItemView.setSelectBackColor(selectBackColor);
            calenderItemView.setSelectTextColor(selectTextColor);
            calenderItemView.setSelectTextSize(selectTextSize);
            calenderItemView.setOnItemSelectListener(new CLCalendarItemView.OnItemSelectListener() {
                @Override
                public void onSelectDate(CLCalendarWeekDTO calenderWeekBean, Date date) {
                    if (onItemSelectListener != null) {
                        onItemSelectListener.onSelectDate(calenderWeekBean, date);
                    }
                }
            });
            calenderItemViews[i] = calenderItemView;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (getAdapter() != null) {
            CLCalendarItemView calenderItemView = (CLCalendarItemView) getChildAt(0);
            if (calenderItemView != null) {
                height = calenderItemView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }


    /**
     * 设置日历数组数据
     *
     * @param calenderWeekBean
     * @param position
     */
    private void setPositionCalender(CLCalendarWeekDTO calenderWeekBean, int position) {
        position = position % 3;
        // the current month

        calenderWeekBeans[position] = calenderWeekBean;
        // position after the next month
        calenderWeekBeans[(position + 1) % 3] = CLCalendarWeekUtil.nextUnit(calenderWeekBean.getYear(), calenderWeekBean.getMonth(), calenderWeekBean.getDay());
        // the previous one is last month
        calenderWeekBeans[(position - 1 + 3) % 3] = CLCalendarWeekUtil.previousUnit(calenderWeekBean.getYear(), calenderWeekBean.getMonth(), calenderWeekBean.getDay());
        for (int i = 0; i < calenderWeekBeans.length; i++) {
            calenderItemViews[i].setDate(calenderWeekBeans[i].getYear(), calenderWeekBeans[i].getMonth(), calenderWeekBeans[i].getDay());

//            calenderItemViews[i].setSelectDate(-1);

            Date date = CLCalendarView.MY_SELECTED_DATE;
            int sldate = 0, slMonth = 0, slYear = 0;
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                sldate = calendar.get(Calendar.DATE);
                slMonth = calendar.get(Calendar.MONTH);
                slYear = calendar.get(Calendar.YEAR);
                Date[] dates = calenderWeekBean.getBeanDate();
                for (int sl = 0; sl < dates.length; sl++) {
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(dates[sl]);
                    int sldate2 = calendar2.get(Calendar.DATE);
                    int slMonth2 = calendar2.get(Calendar.MONTH);
                    int slYear2 = calendar2.get(Calendar.YEAR);
                    if (slYear == slYear2 && slMonth == slMonth2 && sldate == sldate2) {
                        calenderItemViews[i].setSelectDate(sldate);

                    }
                }
            }

        }
    }

    /**
     * 获取当前显示日历
     *
     * @return
     */
    public CLCalendarWeekDTO getCurrentCalender() {
        return calenderWeekBeans[getCurrentItem() % 3];
    }

    /**
     * 设置当前显示日历
     *
     * @param calenderBean
     */
    public void setCurrentCalender(CLCalendarWeekDTO calenderBean) {
        calenderBean = CLCalendarWeekUtil.getNewCalender(calenderBean.getYear(), calenderBean.getMonth(), calenderBean.getDay());
        int result = calenderBean.compareTo(CLCalendarWeekUtil.getNewCalender(getCurrentCalender().getYear(), getCurrentCalender().getMonth(), getCurrentCalender().getDay()));
        if (result != 0) {
            calenderWeekBeans[(getCurrentItem() + result) % 3] = calenderBean;
            setPositionCalender(calenderBean, getCurrentItem() + result);
            setCurrentItem(getCurrentItem() + result);
        }
    }

    /**
     * 获取选中日期
     *
     * @return
     */
    public CLCalendarWeekDTO getSelectDate() {
        CLCalendarWeekDTO CalenderWeekBean = calenderWeekBeans[getCurrentItem() % 3];
        CalenderWeekBean.setDay(calenderItemViews[getCurrentItem() % 3].getSelectDate());
        if (CalenderWeekBean.getDay() == -1) {
            return null;
        }
        return CalenderWeekBean;
    }

    public void setSelectDate(CLCalendarWeekDTO calender) {
        setCurrentCalender(calender);

        calenderItemViews[getCurrentItem() % 3].setSelectDate(calender.getDay());
    }

    private CLCalendarItemView.OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(CLCalendarItemView.OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    private OnCalenderPageChangeListener onCalenderPageChangeListener;

    public void setOnCalenderPageChangeListener(OnCalenderPageChangeListener onCalenderPageChangeListener) {
        this.onCalenderPageChangeListener = onCalenderPageChangeListener;
    }

    public interface OnCalenderPageChangeListener {
        void onChange(CLCalendarWeekDTO calenderWeekBean);
    }


}
