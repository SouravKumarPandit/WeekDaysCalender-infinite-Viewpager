package com.limitless.sourav.swipeactionlayout;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.limitless.sourav.swipeactionlayout.calenderview.CLCalendarItemView;
import com.limitless.sourav.swipeactionlayout.calenderview.CLCalendarView;
import com.limitless.sourav.swipeactionlayout.calenderview.CLCalendarWeekDTO;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements CLCalendarItemView.OnItemSelectListener, View.OnClickListener, CLCalendarView.OnCalenderPageChangeListener {

    float xFlotPoint, yFlotPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* CLSwipeLinearLayout clSwipeLinearLayout = new CLSwipeLinearLayout(this);
        clSwipeLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        clSwipeLinearLayout.setBackgroundColor(0xffdedede);
        clSwipeLinearLayout.setOnSwipeToDirection(this);
        clSwipeLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        clSwipeLinearLayout.setOrientation(LinearLayout.VERTICAL);
        clCalendarView = new CLCalendarView(this);
        clCalendarView.setOnItemSelectListener(this);
        clCalendarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        clSwipeLinearLayout.addView(clCalendarView);


        Button button
                = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText("Next day 2+");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = clCalendarView.getSelectedDate();
                long ltime = date.getTime().getTime() + 86400000*2;
                date .setTime( new Date(ltime));
                clCalendarView.setSelectDate(2,date);
            }
        });

        final Button button1
                = new Button(this);
        button1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button1.setAllCaps(true);
        button1.setText("get Selected date -");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = clCalendarView.getSelectedDate();
                button1.setText("Selected date - " + date.get(Calendar.DATE) + " / " + date.get(Calendar.MONTH) + " / " + date.get(Calendar.YEAR));

            }
        });
        clSwipeLinearLayout.addView(button);
        clSwipeLinearLayout.addView(button1);*/

        setContentView(getActivityLayout());

    }

    private View getActivityLayout() {
        Context clContext = this;
        FrameLayout clFrameLayout = new FrameLayout(clContext);
//        clFrameLayout.addView(getF);
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(clContext);
        coordinatorLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        coordinatorLayout.addView(getCalendarViewLayout());
        addFloatingButton(coordinatorLayout);
        clFrameLayout.addView(coordinatorLayout);
        return clFrameLayout;
    }

    private CLCalendarView getCalendarViewLayout() {
        CLCalendarView clCalendarView = new CLCalendarView(this);
        clCalendarView.setId(R.id.calander_view);
        clCalendarView.setOnItemSelectListener(this);
        clCalendarView.setOnCalenderPageChangeListener(this);
        clCalendarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        clCalendarView.selectCurrentDate();
        return clCalendarView;
    }

    @SuppressLint("RestrictedApi")
    private void addFloatingButton(CoordinatorLayout coordinatorLayout) {
        int i20 = dpToPx(20);
        int i50 = dpToPx(50);
//        Drawable clEditDrawable = CLViewUtil.getIcon(getActivity(), getResources().getString(R.string.icon_plus_symbol), i20, Color.WHITE, false);

        CoordinatorLayout.LayoutParams clFloatingButtonParams = new CoordinatorLayout.LayoutParams(i50, i50);
        clFloatingButtonParams.setMargins(0, 0, i20, i20);
        clFloatingButtonParams.gravity = Gravity.BOTTOM | Gravity.END;
        FloatingActionButton clFloatingActionButton = new FloatingActionButton(this);
//        clFloatingActionButton.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
//        clFloatingButtonParams.anchorGravity = Gravity.BOTTOM | Gravity.END;
        clFloatingActionButton.setId(R.id.anouncement_fab);
        clFloatingActionButton.setOnClickListener(this);
        clFloatingActionButton.setVisibility(View.INVISIBLE);
//        clFloatingActionButton.setImageResource(R.drawable.plus_add);
//        clFloatingActionButton.setImageBitmap(textAsBitmap("OK", dpToPx(18), Color.WHITE));
        coordinatorLayout.addView(clFloatingActionButton, clFloatingButtonParams);

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onSelectDate(Calendar calendar, float xPositionPoint, float yPositionPoint) {
//        Toast.makeText(this, calendar.get(Calendar.DATE) + "  -  " + calendar.get(Calendar.MONTH) + "  -  " + calendar.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
        if (xPositionPoint > 0)
            this.xFlotPoint = xPositionPoint;
        if (xPositionPoint > 0)
            this.yFlotPoint = yPositionPoint;

        final FloatingActionButton floatingActionButton = findViewById(R.id.anouncement_fab);
        final CLCalendarView  clCalendarView= findViewById(R.id.calander_view);
        if (floatingActionButton != null&&clCalendarView!=null)
            if (!isSameDay(calendar, Calendar.getInstance())||!isSameDay(calendar,clCalendarView.getSelectDate())) {

                if (floatingActionButton.getVisibility() == View.INVISIBLE) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setImageBitmap(textAsBitmap("" + Calendar.getInstance().get(Calendar.DATE), dpToPx(18), Color.WHITE));
                    floatingActionButton.setTranslationX(-getScreenWidth() + xFlotPoint);
                    floatingActionButton.setTranslationY(-getScreenHeight() + yFlotPoint);
                    floatingActionButton.setScaleX(0.5f);
                    floatingActionButton.setScaleY(0.5f);
                    floatingActionButton.animate().translationX(0).translationY(0).scaleX(1).scaleY(1).setDuration(300).setListener(null).start();

                }

            } else {

                if (floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setImageBitmap(textAsBitmap("" + Calendar.getInstance().get(Calendar.DATE), dpToPx(18), Color.WHITE));
                    floatingActionButton.animate().translationX(-getScreenWidth() + xFlotPoint).translationY(-getScreenHeight() + yFlotPoint).scaleX(0.5f).scaleY(0.5f).setDuration(300).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            floatingActionButton.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).start();

                }

            }

//        onResizeView(floatingActionButton);


    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }



    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / ((float) densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }  public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.anouncement_fab) {

            CLCalendarView clCalendarView = findViewById(R.id.calander_view);
//            clCalendarView.animate().translationX(-getScreenWidth()).setDuration(300).start();
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) clCalendarView.getParent();
            coordinatorLayout.removeViewAt(0);
            coordinatorLayout.addView(getCalendarViewLayout(), 0);
            view.setVisibility(View.VISIBLE);
            onSelectDate(Calendar.getInstance(), -1, -1);
//            onResizeView(view);
        }

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public void onChange(CLCalendarWeekDTO calenderWeekBean, int dayOfWeak, float xPositionPoint, float yPositionPoint) {
        onSelectDate(calenderWeekBean.getBeanDate()[dayOfWeak], xPositionPoint, yPositionPoint);
    }

}
