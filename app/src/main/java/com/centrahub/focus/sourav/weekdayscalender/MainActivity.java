package com.centrahub.focus.sourav.weekdayscalender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender.CLCalendarItemView;
import com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender.CLCalendarView;
import com.centrahub.focus.sourav.weekdayscalender.WeekDaysCalender.CLCalendarWeekDTO;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CLCalendarItemView.OnItemSelectListener {

    private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainlinearLayou());
    }

    public LinearLayout getMainlinearLayou() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        CLCalendarView clCalendarView = new CLCalendarView(this);
        clCalendarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        clCalendarView.setOnItemSelectListener(this);


        linearLayout.addView(clCalendarView);


        currentDate = new TextView(this);
        currentDate.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        currentDate.setPadding(0, 50, 0, 0);
        currentDate.setText("notSelected");
        onSelectDate(null, new Date());

        linearLayout.addView(currentDate);
        return linearLayout;
    }

    @Override
    public void onSelectDate(CLCalendarWeekDTO calenderBean, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        currentDate.setText("Selected Date : " + calendar.get(Calendar.DATE) + " - " + (calendar.get(Calendar.MONTH) + 1) + " - " + calendar.get(Calendar.YEAR));

    }
}
