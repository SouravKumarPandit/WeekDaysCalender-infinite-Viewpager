package com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.centrahub.focus.sourav.weekdayscalender.R;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by sourav on 07-Nov-17.
 */


public class CLCalendarItemView extends View /*implements View.OnClickListener*/ {
    private static final String TAG = "CalenderItemView";

    public CLCalendarItemView(Context context) {
        this(context, null);
    }

    public CLCalendarItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CLCalendarItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint ringPaint;
    private Calendar todaysDate;
    private int dayOfWeek;
    private int year, month, day;
    private int itemWidth;
    private int itemHeight;
    private int rows = 7;
    private int cols = 1;
    private int preSelectDate = -1;
    //    private int selectDate = -1;
    public static int SELECTED_DATE = -1;

    //    private int[][] dates = new int[cols][rows];
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

    private Paint backgroundPaint;
    private TextPaint monthsPaint;
    private RectF backgroundRect;
    private Date[] weekDates;
    private int iCurrentDay;
    private int currentTextColor;
    private static int iSundayOn;

    private void init(Context context) {
//        setOnClickListener(this);
        initAttrs();
        initTools(context);
    }

    private void initAttrs() {
        headerTextColor = Color.BLACK;
        headerTextSize = 12;
//normal color
        dateTextColor = Color.BLACK;
        dateTextSize = 15;

//        for week day color
        weekenddateTextColor = Color.RED;
        //for currnet day color
        currentTextColor = getResources().getColor(R.color.colorAccent);

        backColor = getDrawingCacheBackgroundColor();

//        selected day color
        selectBackColor = Color.BLUE;
        selectTextColor = Color.WHITE;
        selectTextSize = dateTextSize;
        Calendar cal = Calendar.getInstance();
//cal.setTime(new Date());//Set specific Date if you want to
        todaysDate = cal;
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        iCurrentDay = cal.get(Calendar.DATE);
        if (SELECTED_DATE < 0) {
            setSelectDate(cal.get(Calendar.DATE));
        }
        ;
        setSelectDate(iCurrentDay);
        while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
            cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            iSundayOn = 7;
        }
        if (iSundayOn == 7) {
            dayOfWeek = dayOfWeek - 2;
        } else dayOfWeek--;

/*        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayOfWeek = 0;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            dayOfWeek = 1;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            dayOfWeek = 2;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            dayOfWeek = 3;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            dayOfWeek = 4;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            dayOfWeek = 5;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayOfWeek = 6;
        }*/
        CLCalendarWeekDTO calenderBean = CLCalendarWeekUtil.getWeekUtilCalender(cal.getTime(), CLCalendarWeekUtil.getWeek(cal.getTime()));
        this.year = calenderBean.getYear();
        this.month = calenderBean.getMonth();
        this.day = calenderBean.getDay();
        setDate(calenderBean.getYear(), calenderBean.getMonth(), calenderBean.getDay());
    }

    private void initTools(Context context) {
        //top text
        ringPaint = new Paint();
        ringPaint.setColor(getResources().getColor(R.color.colorPrimary));
//        ringPaint.setColor(Color.LTGRAY);
        ringPaint.setStrokeWidth(3);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setAntiAlias(true);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(getResources().getColor(android.R.color.white));
//        backgroundPaint.setColor( Color.parseColor("#FF963DB9"));
        monthsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        monthsPaint.setTextSize(sp2px(dateTextSize));//dateTextSize * 1.2f
        monthsPaint.setColor(ContextCompat.getColor(context, android.R.color.darker_gray));

        backgroundRect = new RectF();

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
        weekendPaint.setTextSize(sp2px(dateTextSize));


        currnetDatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currnetDatePaint.setColor(currentTextColor);
        currnetDatePaint.setTextSize(sp2px(selectTextSize));
        // Initialize the selected brush
        selectItemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectItemPaint.setColor(selectBackColor);
        selectItemPaint.setStrokeWidth(selectTextColor);
        selectItemPaint.setTextSize(sp2px(selectTextSize));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
//        int height=MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
        itemWidth = width / rows;
        itemHeight = itemWidth;
        setMeasuredDimension(itemWidth * rows, (int) (itemHeight * (1.6f)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMonthsYear(canvas);
        drawHeader(canvas);
        drawDayItem(canvas);
    }

    private void drawTodaysDate(Canvas canvas, String text, int centerX, int centerY) {
        ringPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerY, (float) (Math.min(itemWidth, itemHeight) / 4), ringPaint);
//        canvas.drawCircle(centerX, centerY, (float) (Math.min(itemWidth, itemHeight) / 2.6), selectItemPaint);

    }

    private void drawMonthsYear(Canvas canvas) {
//        backgroundRect.set(0, itemHeight/9, itemWidth * rows, itemHeight * 0.7f);
        backgroundRect.set(0, 0, itemWidth * rows, itemHeight / 2);
        canvas.drawRoundRect(backgroundRect, 0, 0, backgroundPaint);
        String sMonthsYear = YearMonth.values()[weekDates[3].getMonth()] + "   " + year;
//        int xPos = (canvas.getWidth() / 2);
        int xPos = canvas.getWidth() / 2 - (int) (monthsPaint.measureText(sMonthsYear) / 2);
        canvas.drawText(sMonthsYear, xPos, itemHeight / 3, monthsPaint);
//        canvas.drawText(YearMonth.values()[Calendar.getInstance().get(Calendar.MONTH)] + "   " + year, canvas.getWidth() / 2 - itemWidth * 2 / 3, itemHeight / 3, monthsPaint);
//        canvas.drawText(YearMonth.values()[weekDates[3].getMonth()] + "   " + year,canvas.getWidth()/2-itemWidth, itemHeight/3, monthsPaint);
    }

    /*private void drawMonthsYear(Canvas canvas) {
//        backgroundRect.set(0, itemHeight/9, itemWidth * rows, itemHeight * 0.7f);
        backgroundRect.set(0, 0, itemWidth * rows, itemHeight / 2);
        canvas.drawRoundRect(backgroundRect, 0, 0, backgroundPaint);

        canvas.drawText(YearMonth.values()[Calendar.getInstance().get(Calendar.MONTH)] + "   " + year, canvas.getWidth() / 2 - itemWidth * 2 / 3, itemHeight / 3, monthsPaint);
//        canvas.drawText(YearMonth.values()[weekDates[3].getMonth()] + "   " + year,canvas.getWidth()/2-itemWidth, itemHeight/3, monthsPaint);
    }*/

    /**
     * @param canvas
     */
    private void drawHeader(Canvas canvas) {
        headerPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        //add centery /2 or /3 for gape between days and date
        int headerWidth = (int) (itemWidth / 1.5);
        if (iSundayOn == 0) {
            for (int j = 0; j < 7; j++) {
                if (j != iSundayOn)
                    drawOneText(canvas, WeekDays.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, headerPaint);
                else
                    drawOneText(canvas, WeekDays.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, weekendPaint);
            }
        } else {
            for (int j = 0; j < 7; j++) {
                if (j != iSundayOn)
                    drawOneText(canvas, WeekDayLast.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, headerPaint);
                else
                    drawOneText(canvas, WeekDayLast.values()[j].toString(), itemWidth / 2 + itemWidth * j, headerWidth, weekendPaint);
            }
        }
    }

    /**
     * @param canvas
     * @param text
     * @param centerX
     * @param centerY
     * @param paint
     */
    private void drawOneText(Canvas canvas, String text, int centerX, int centerY, Paint paint) {
        float textWidth = paint.measureText(text);
        canvas.drawText(text, centerX - textWidth / 2, centerY - (paint.descent() + paint.ascent()) / 2, paint);
    }

    /**
     * @param canvas
     */

    private void drawDayItem(Canvas canvas) {
//        int headerWidth=(int) (itemWidth/1.5);
        int dayItemY = (int) ((itemHeight * (1) + itemHeight / 2) / 1.2);
        for (int j = 0; j < rows; j++) {
            if (s7dates[j] == preSelectDate) {
                if (j == iSundayOn)
                    drawSundayItem(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, false);
                else
                    drawSelectItem(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, false);
            }
            if (s7dates[j] == SELECTED_DATE) {
                drawSelectItem(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, true);
            } else if (s7dates[j] > 0) {
                if (j == iSundayOn)
                    drawSunText(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, weekendPaint);
                else if (todaysDate.get(Calendar.YEAR) == year && todaysDate.get(Calendar.MONTH) == month && todaysDate.get(Calendar.DATE) == day && j == dayOfWeek)
                    drawSunText(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, currnetDatePaint);
                else
                    drawOneText(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY, datePaint);
            }

//            if (j == iSundayOn) {
//                if (todaysDate.get(Calendar.YEAR) == year && todaysDate.get(Calendar.MONTH) == month && todaysDate.get(Calendar.DATE) == day && j == dayOfWeek)
//                drawTodaysDate(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, itemHeight * (1) + itemHeight / 2);
//            }else{
            if (todaysDate.get(Calendar.YEAR) == year && todaysDate.get(Calendar.MONTH) == month && todaysDate.get(Calendar.DATE) == day && j == dayOfWeek)
                drawTodaysDate(canvas, String.valueOf(s7dates[j]), itemWidth * j + itemWidth / 2, dayItemY);

//            }

        }


    }

    private void drawSunText(Canvas canvas, String text, int centerX, int centerY, Paint datePaint) {
        float textWidth = datePaint.measureText(text);
        canvas.drawText(text, centerX - textWidth / 2, centerY - (datePaint.descent() + datePaint.ascent()) / 2, datePaint);

    }

    private void drawSelectItem(Canvas canvas, String text, int centerX, int centerY, boolean isSelect) {
        selectItemPaint.setColor(isSelect ? selectBackColor : backColor);
        canvas.drawCircle(centerX, centerY, (float) (Math.min(itemWidth, itemHeight) / 4), selectItemPaint);
        if (isSelect) {
            selectItemPaint.setColor(selectTextColor);
            drawOneText(canvas, text, centerX, centerY, selectItemPaint);
        }
    }

    private void drawSundayItem(Canvas canvas, String text, int centerX, int centerY, boolean isSelect) {
        selectItemPaint.setColor(isSelect ? selectBackColor : backColor);
        canvas.drawCircle(centerX, centerY, (float) (Math.min(itemWidth, itemHeight) / 4), selectItemPaint);
        if (isSelect) {
            selectItemPaint.setColor(selectTextColor);
            drawSunText(canvas, text, centerX, centerY, weekendPaint);
        }
    }


//    @Override
//    public void onClick(View view)
//    {
//        for (int j = 0; j < rows; j++)
//        {
//            if (s7dates[j] > 0)
//            {
//                preSelectDate = SELECTED_DATE;
//                SELECTED_DATE = s7dates[j];
//                CLCalendarView.MY_SELECTED_DATE = weekDates[j];
//                if (onItemSelectListener != null)
//                {
//                    onItemSelectListener.onSelectDate(CLCalendarWeekUtil.getNewCalender(year, month, SELECTED_DATE), weekDates[j]);
//                }
//                invalidate();
//            }
////                    }
//        }
//    }

    private PointF startPoint;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startPoint = new PointF(event.getX(), event.getY());
//            Log.i(TAG,startPoint.toString());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
//            Log.i(TAG,x+","+y);
            if (Math.abs(startPoint.x - x) < 20 && Math.abs(startPoint.y - y) < 20) {
//                for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    if (s7dates[j] > 0 && x > itemWidth * j && x < itemWidth * (j + 1) && y > itemHeight * (0.6f) && y < itemHeight * (1.5)) {
                        preSelectDate = SELECTED_DATE;
                        SELECTED_DATE = s7dates[j];
                        CLCalendarView.MY_SELECTED_DATE = weekDates[j];
                        if (onItemSelectListener != null) {
                            onItemSelectListener.onSelectDate(CLCalendarWeekUtil.getNewCalender(year, month, SELECTED_DATE), weekDates[j]);
                        }
                        invalidate();
                    }
//                    }
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * @param year
     * @param month
     */
    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        initDates(year, month, day);
        invalidate();
    }

    /**
     * @param year
     * @param month
     * @param day
     */
    private void initDates(int year, int month, int day) {
        CLCalendarWeekDTO calenderBean = CLCalendarWeekUtil.getNewCalender(year, month, day);
        weekDates = calenderBean.getBeanDate();
        for (int i = 0; i < 7; i++) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(weekDates[i]);
            int tempDay = cal.get(Calendar.DAY_OF_MONTH);
            s7dates[i] = tempDay;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekDates[0]);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            iSundayOn = 6;
        }
        setSelectDate(-1);

    }

//    private void initDates(int year, int month, int day) {
//        CLCalendarWeekDTO calenderBean = CLCalendarWeekUtil.getNewCalender(year, month, day);
//        weekDates = calenderBean.getBeanDate();
//        for (int i = 0; i < 7; i++) {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(weekDates[i]);
//            int tempDay = cal.get(Calendar.DAY_OF_MONTH);
//            int tempMonth = cal.get(Calendar.MONTH);
//            int tempyear = cal.get(Calendar.YEAR);
//            s7dates[i] = tempDay;
//
//
//            Calendar currentCalender = Calendar.getInstance();
//            int currentdate = currentCalender.get(Calendar.DATE);
//            int currentmonth = currentCalender.get(Calendar.MONTH);
//            int currentyear = currentCalender.get(Calendar.YEAR);
//
//            if (tempDay == currentdate && tempMonth == currentmonth && tempyear == currentyear) {
//                if (onItemSelectListener != null) {
//                    onItemSelectListener.onSelectDate(CLCalendarWeekUtil.getNewCalender(year, month, SELECTED_DATE), weekDates[i]);
//                    Toast.makeText(getContext(), "" + tempDay, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//
//        setSelectDate(-1);
//    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


    /**
     * @return
     */
    public CLCalendarWeekDTO getCalenderBean() {
        return CLCalendarWeekUtil.getNewCalender(year, month, day);
    }

    /**
     * @return
     */
    public int getSelectDate() {
        return SELECTED_DATE;
    }

    /**
     * @param selectDate
     */
    public void setSelectDate(int selectDate) {
        SELECTED_DATE = selectDate;
        invalidate();
    }

    public int sp2px(float sp) {
        Resources r = Resources.getSystem();
        final float scale = r.getDisplayMetrics().density;
        return (int) (sp * scale + 0.5f);
    }

    public int getHeaderTextColor() {
        return headerTextColor;
    }

    public void setHeaderTextColor(int headerTextColor) {
        this.headerTextColor = headerTextColor;
        initTools(getContext());
        invalidate();
    }

    public float getHeaderTextSize() {
        return headerTextSize;
    }

    public void setHeaderTextSize(float headerTextSize) {
        this.headerTextSize = headerTextSize;
        initTools(getContext());
        invalidate();
    }

    public int getDateTextColor() {
        return dateTextColor;
    }

    public void setDateTextColor(int dateTextColor) {
        this.dateTextColor = dateTextColor;
        initTools(getContext());
        invalidate();
    }

    public float getDateTextSize() {
        return dateTextSize;
    }

    public void setDateTextSize(float dateTextSize) {
        this.dateTextSize = dateTextSize;
        initTools(getContext());
        invalidate();
    }

    public int getSelectBackColor() {
        return selectBackColor;
    }

    public void setSelectBackColor(int selectBackColor) {
        this.selectBackColor = selectBackColor;
        initTools(getContext());
        invalidate();
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public void setSelectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        initTools(getContext());
        invalidate();
    }

    public float getSelectTextSize() {
        return selectTextSize;
    }

    public void setSelectTextSize(float selectTextSize) {
        this.selectTextSize = selectTextSize;
        initTools(getContext());
        invalidate();
    }

    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }


    public interface OnItemSelectListener {
        void onSelectDate(CLCalendarWeekDTO calenderBean, Date date);

    }

    public enum YearMonth {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
        JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    }

    public enum WeekDayLast {
        Mon, Tue, Wed, Thu, Fri, Sat, Sun;
    }

    public enum WeekDays {
        Sun, Mon, Tue, Wed, Thu, Fri, Sat;
    }
}
