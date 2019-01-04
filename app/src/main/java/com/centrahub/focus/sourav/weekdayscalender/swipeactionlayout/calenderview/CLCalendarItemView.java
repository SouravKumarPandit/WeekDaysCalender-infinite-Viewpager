package com.limitless.sourav.swipeactionlayout.calenderview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.time.YearMonth;
import java.util.Calendar;


/**
 * Created by sourav on 07-Nov-17.
 */


public class CLCalendarItemView extends View /*implements View.OnClickListener*/
{
//    private static final String TAG = "CalenderItemView";
    private Calendar[] weekDates;
    private int currentTextColor;
    private  int [] viewLocation=new int[2];
    private float xPlotLocation,yPlotLocation;

    public static int iSelectedPosition;

    public CLCalendarItemView(Context context)
    {
        this(context, null);
    }

    public CLCalendarItemView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CLCalendarItemView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //for touch calculation
    private PointF startPoint;
    private Paint ringPaint;
    private Calendar todaysDate;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    private int dayOfWeek;
    private int year, month, day;
    public static int selectYear, selectMonth, selectDay;
    public static int preYear, preMonth, preDay;
    private int itemWidth;
    private int itemHeight;
    private int rows = 7;
    private int iSundayOn;

    private int[] s7dates = new int[7];
    // head font color
    private int headerTextColor;
    // head font size
    private float headerTextSize;
    // head brush
    private Paint headerPaint;
    private Paint weekendPaint;
    private Paint currnetDatePaint;

    // Date font color
    private int dateTextColor;
    private int weekenddateTextColor;
    // Date font size
    private float dateTextSize;
    // date brush
    private Paint datePaint;
    // background color
    private int backColor;
    // check the background color
    private int selectBackColor;
    // Select the font color
    private int selectTextColor;
    // select the font size
    private float selectTextSize;
    // selected brush
    private Paint selectItemPaint;

    //    private Paint backgroundPaint;
    private TextPaint monthsPaint;
    //    private RectF backgroundRect;


    private void init(Context context)
    {
        initAttrs();
        initTools(context);
    }

    private void initAttrs()
    {
        headerTextColor = Color.BLACK;
        headerTextSize = 1;
        //normal color
        dateTextColor = Color.BLACK;
        dateTextSize = 1;
//        for week day color
        weekenddateTextColor = Color.RED;
        //for currnet day color
        currentTextColor = getResources().getColor(android.R.color.holo_purple);
//        currentTextColor = Color.GREEN;

        backColor = getDrawingCacheBackgroundColor();

//        selected day color
        selectBackColor = Color.BLUE;
        selectTextColor = Color.WHITE;
        todaysDate = Calendar.getInstance();
        selectDay = todaysDate.get(Calendar.DATE);
        selectMonth = todaysDate.get(Calendar.MONTH);
        selectYear = todaysDate.get(Calendar.YEAR);
        dayOfWeek = todaysDate.get(Calendar.DAY_OF_WEEK);
        iSelectedPosition = dayOfWeek - 1;
        while (todaysDate.get(Calendar.DAY_OF_WEEK) > todaysDate.getFirstDayOfWeek())
        {
            todaysDate.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        if (todaysDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
        {
            iSundayOn = 6;
        }
        if (iSundayOn == 6)
        {
            dayOfWeek = dayOfWeek - 2;

        } else dayOfWeek--;

    }

    private void initTools(Context context)
    {
        //top text
        ringPaint = new Paint();
        ringPaint.setColor(Color.BLACK);
        ringPaint.setStrokeWidth(2);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setAntiAlias(true);

        monthsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        monthsPaint.setTextSize(sp2px(dateTextSize));//dateTextSize * 1.2f
        monthsPaint.setColor(Color.GRAY);
        // initialize the head brush
        headerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setColor(headerTextColor);
        headerPaint.setTextSize(sp2px(headerTextSize));

        // Initialize the specific date brush
        datePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        datePaint.setColor(dateTextColor);
        datePaint.setTextSize(sp2px(dateTextSize));

        weekendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        weekendPaint.setColor(weekenddateTextColor);
        weekendPaint.setTextSize(sp2px(headerTextSize));


        currnetDatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currnetDatePaint.setColor(currentTextColor);
        currnetDatePaint.setTextSize(sp2px(dateTextSize));

        // Initialize the selected brush
        selectItemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectItemPaint.setColor(selectBackColor);
        selectItemPaint.setStrokeWidth(selectTextColor);
        selectItemPaint.setTextSize(sp2px(selectTextSize));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
        itemWidth = width / rows;
        itemHeight = itemWidth;
        setMeasuredDimension(itemWidth * rows, (int) (itemHeight * (1.5f)));

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawMonthsYear(canvas);
        drawHeader(canvas);
        drawDayItem(canvas);
    }

    private void drawTodaysDate(Canvas canvas, String text, int centerX, int centerY)
    {

        getLocationOnScreen(viewLocation);
        xPlotLocation=centerX+(float) sp2px(selectTextSize)*3;
        yPlotLocation=viewLocation[1]+centerY+(float) sp2px(selectTextSize)*3;
        canvas.drawCircle(centerX, centerY, (float) sp2px(selectTextSize - 1), ringPaint);
    }

    private void drawMonthsYear(Canvas canvas)
    {
        getCalenderBean().getBeanDate()[3].setTime(weekDates[3].getTime());
        String sMonthsYear = YearMonth.values()[getCalenderBean().getBeanDate()[3].get(Calendar.MONTH)] + "   " + getCalenderBean().getBeanDate()[3].get(Calendar.YEAR);
        canvas.drawText(sMonthsYear, canvas.getWidth() / 2 - (int) (monthsPaint.measureText(sMonthsYear) / 2), itemHeight / 3, monthsPaint);
    }

    /**
     * @param canvas
     */

    private void drawHeader(Canvas canvas)
    {
        headerPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        weekendPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        //add centery /2 or /3 for gape between days and date
        int headerWidth = (int) (itemWidth / 1.5);
        if (iSundayOn == 0)
        {
            for (int j = 0; j < 7; j++)
            {
                if (j != iSundayOn)
                    drawOneText(canvas, WeekDays.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, headerPaint);
                else
                    drawOneText(canvas, WeekDays.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, weekendPaint);
            }
        } else
        {
            for (int j = 0; j < 7; j++)
            {
                if (j != iSundayOn)
                    drawOneText(canvas, WeekDayLast.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, headerPaint);
                else
                    drawOneText(canvas, WeekDayLast.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, weekendPaint);
            }
        }
    }


    private void drawOneText(Canvas canvas, String text, int centerX, int centerY, Paint paint)
    {
        float textWidth = paint.measureText(text);
        canvas.drawText(text, centerX - textWidth / 2, centerY - (paint.descent() + paint.ascent()) / 2, paint);
    }

    private void drawDayItem(Canvas canvas)
    {
        int dayItemY = (int) ((itemHeight + itemHeight / 2) / 1.3);
        for (int j = 0; j < rows; j++)
        {
            if (todaysDate.get(Calendar.YEAR) == year && todaysDate.get(Calendar.MONTH) == month && todaysDate.get(Calendar.DATE) == day && j == dayOfWeek)
                drawTodaysDate(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY);
            if (s7dates[j] == preDay)
            {
                if (j == iSundayOn)
                    drawSundayItem(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, false);
                else
                    drawSelectItem(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, false);
            }
            if (getCalenderBean().getBeanDate()[j].get(Calendar.DATE) == selectDay
                    && getCalenderBean().getBeanDate()[j].get(Calendar.MONTH) == selectMonth
                    && getCalenderBean().getBeanDate()[j].get(Calendar.YEAR) == selectYear)
            {
                drawSelectItem(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, true);
            } else if (s7dates[j] > 0)
            {
                if (j == iSundayOn)
                    drawSunText(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, weekendPaint);
                else if (CLCalendarView.selectedDate.get(Calendar.YEAR) == year && CLCalendarView.selectedDate.get(Calendar.MONTH) == month && CLCalendarView.selectedDate.get(Calendar.DATE) == day && j == dayOfWeek)
                    drawSunText(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, currnetDatePaint);
                else
                    drawOneText(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, datePaint);
            }

        }


    }

    private void drawSunText(Canvas canvas, String text, int centerX, int centerY, Paint datePaint)
    {
        float textWidth = datePaint.measureText(text);
        canvas.drawText(text, centerX - textWidth / 2, centerY - (datePaint.descent() + datePaint.ascent()) / 2, datePaint);

    }

    private void drawSelectItem(Canvas canvas, String text, int centerX, int centerY, boolean isSelect)
    {
        selectItemPaint.setColor(isSelect ? selectBackColor : backColor);


        canvas.drawCircle(centerX, centerY, (float) sp2px(selectTextSize - 1), selectItemPaint);
        if (isSelect)
        {
            selectItemPaint.setColor(selectTextColor);
            drawOneText(canvas, text, centerX, centerY, selectItemPaint);
        }
    }

    private void drawSundayItem(Canvas canvas, String text, int centerX, int centerY, boolean isSelect)
    {
        selectItemPaint.setColor(isSelect ? selectBackColor : backColor);
        canvas.drawCircle(centerX, centerY, (float) (Math.min(itemWidth, itemHeight) / 5), selectItemPaint);
        if (isSelect)
        {
            selectItemPaint.setColor(selectTextColor);
            drawSunText(canvas, text, centerX, centerY, weekendPaint);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            startPoint = new PointF(event.getX(), event.getY());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            float x = event.getX();
            float y = event.getY();
            if (Math.abs(startPoint.x - x) < 20 && Math.abs(startPoint.y - y) < 20)
            {
                for (int j = 0; j < rows; j++)
                {
                    if (s7dates[j] > 0 && x > itemWidth * j && x < itemWidth * (j + 1) && y > itemHeight * (0.6f) && y < itemHeight * (1.5f))
                    {
                        iSelectedPosition = j;
                        preDay = selectDay;
                        preMonth = selectMonth;
                        preYear = selectYear;
                        CLCalendarView.selectedDate = weekDates[j];
                        selectDay = CLCalendarView.selectedDate.get(Calendar.DATE);
                        selectMonth = CLCalendarView.selectedDate.get(Calendar.MONTH);
                        selectYear = CLCalendarView.selectedDate.get(Calendar.YEAR);
                        if (onItemSelectListener != null)
                        {
                            onItemSelectListener.onSelectDate(weekDates[j],xPlotLocation,yPlotLocation);
                        }
                        invalidate();
                    }
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }


    public void setDate(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        initDates(year, month, day);
        invalidate();
    }

    private void initDates(int year, int month, int day)
    {
        CLCalendarWeekDTO calenderBean = CLCalendarWeekUtil.getNewCalender(year, month, day);
        weekDates = calenderBean.getBeanDate();
        for (int i = 0; i < 7; i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(weekDates[i].getTime());
            int tempDay = cal.get(Calendar.DAY_OF_MONTH);
            s7dates[i] = tempDay;

        }
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }


    /**
     * @return
     */
    public CLCalendarWeekDTO getCalenderBean()
    {
        return CLCalendarWeekUtil.getNewCalender(year, month, day);
    }

    /**
     * @return
     */


    public void setSelectDate(int day, int month, int year)
    {
        selectDay = day;
        selectMonth = month;
        selectYear = year;
        invalidate();
    }

    public int sp2px(float sp)
    {
        Resources r = Resources.getSystem();
        final float scale = r.getDisplayMetrics().density;
        return (int) (sp * scale + 0.2f);
    }

    public int getHeaderTextColor()
    {
        return headerTextColor;
    }

    public void setHeaderTextColor(int headerTextColor)
    {
        this.headerTextColor = headerTextColor;
        initTools(getContext());
        invalidate();
    }

    public float getHeaderTextSize()
    {
        return headerTextSize;
    }

    public void setHeaderTextSize(float headerTextSize)
    {
        this.headerTextSize = headerTextSize;
        initTools(getContext());
        invalidate();
    }

    public int getDateTextColor()
    {
        return dateTextColor;
    }

    public void setDateTextColor(int dateTextColor)
    {
        this.dateTextColor = dateTextColor;
        initTools(getContext());
        invalidate();
    }

    public float getDateTextSize()
    {
        return dateTextSize;
    }

    public void setDateTextSize(float dateTextSize)
    {
        this.dateTextSize = dateTextSize;
        initTools(getContext());
        invalidate();
    }

    public int getSelectBackColor()
    {
        return selectBackColor;
    }

    public void setSelectBackColor(int selectBackColor)
    {
        this.selectBackColor = selectBackColor;
        initTools(getContext());
        invalidate();
    }

    public int getSelectTextColor()
    {
        return selectTextColor;
    }

    public void setSelectTextColor(int selectTextColor)
    {
        this.selectTextColor = selectTextColor;
        initTools(getContext());
        invalidate();
    }

    public float getSelectTextSize()
    {
        return selectTextSize;
    }

    public void setSelectTextSize(float selectTextSize)
    {
        this.selectTextSize = selectTextSize;
        initTools(getContext());
        invalidate();
    }


    public float getXPlotLocation() {
        return xPlotLocation;
    }

    public float getYPlotLocation() {
        return yPlotLocation;
    }

    public OnItemSelectListener getOnItemSelectListener()
    {
        return onItemSelectListener;
    }

    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener)
    {
        this.onItemSelectListener = onItemSelectListener;
    }


    public interface OnItemSelectListener
    {
        void onSelectDate(Calendar calendar,float xPosition,float yPosition);

    }

    public enum YearMonth
    {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
        JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    public enum WeekDayLast
    {
        Mon, Tue, Wed, Thu, Fri, Sat, Sun;
    }

    public enum WeekDays
    {
        Sun, Mon, Tue, Wed, Thu, Fri, Sat;
    }

}
