package com.limitless.sourav.swipeactionlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.limitless.sourav.swipeactionlayout.viewlistviewpager.SwipeLoopViewPager;

public class ViewHolderActivity extends AppCompatActivity
{


    public TextView[] listOfFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArrayList();
        setContentView(getSwipeScrollView());
    }

    private void initArrayList() {
        listOfFood = new TextView[15];

        LinearLayout.LayoutParams textParam=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < 15; i++) {
            listOfFood[i ]=new TextView(this);
            listOfFood[i ] .setText("Food Item - " + "<"+i+">");
            listOfFood[i].setBackgroundColor(Color.WHITE);
            listOfFood[i].setGravity(Gravity.CENTER);
            listOfFood[i ].setLayoutParams(textParam);
        }
    }


    public View getSwipeScrollView() {
        RelativeLayout linearLayout = new RelativeLayout(this);
        linearLayout.setGravity(Gravity.CENTER);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.gray_light));
//        linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
//        ((LinearLayout.LayoutParams) layoutParams).setMargins(15,15,15,15);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding(30,30,30,30);

        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER);
        img.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        img.setImageDrawable(getResources().getDrawable(R.drawable.bg_image));


        SwipeLoopViewPager swipeLoopViewPager = new SwipeLoopViewPager(this, listOfFood);
//        swipeSelectionView.setViewArrayList(listOfFood);
//        clCalendarView.setCurrentValue(17);
        RelativeLayout.LayoutParams simpleDetailsViewParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        simpleDetailsViewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        swipeLoopViewPager.setLayoutParams(simpleDetailsViewParam);
        swipeLoopViewPager.setOnCalenderPageChangeListener(new SwipeLoopViewPager.OnCalenderPageChangeListener() {
            @Override
            public void onChange(int itemPosition, View itemLabel) {
                Toast.makeText(ViewHolderActivity.this,itemPosition+" -"+((TextView)itemLabel).getText(),Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout.addView(swipeLoopViewPager);
        return linearLayout;
    }


}
