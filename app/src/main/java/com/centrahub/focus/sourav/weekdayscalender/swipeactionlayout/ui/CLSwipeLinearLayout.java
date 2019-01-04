package com.limitless.sourav.swipeactionlayout.ui;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Sourav on 2018
 */
public class CLSwipeLinearLayout extends LinearLayout
{
    private float x1,x2,y1,y2;
    private int swipeDistance = 100;
    private SwipeToDirection onSwipeToDirection;
    public  static final int TOP_BOTTOM=575;
    public  static final int BOTTOM_TOP=576;
    public  static final int LEFT_RIGHT=577;
    public  static final int RIGHT_LEFT=578;



    public CLSwipeLinearLayout(Context context)
    {
        super(context);
        setOrientation(LinearLayout.VERTICAL);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;
                if (deltaX > swipeDistance)
                {
                    if (onSwipeToDirection!=null){
                        onSwipeToDirection.onActionSwipedDirection(LEFT_RIGHT,event);
                    }
//                    swipeLeftToRight();
                }
                else if( Math.abs(deltaX) > swipeDistance)
                {
                    if (onSwipeToDirection!=null){
                        onSwipeToDirection.onActionSwipedDirection(RIGHT_LEFT,event);
                    }
//                    swipeRightToLeft();
                }
                else if(deltaY > swipeDistance){

                    if (onSwipeToDirection!=null){
                        onSwipeToDirection.onActionSwipedDirection(TOP_BOTTOM,event);
                    }
//                    swipeTopToBottom();
                }
                else if( Math.abs(deltaY) > swipeDistance){

                    if (onSwipeToDirection!=null){
                        onSwipeToDirection.onActionSwipedDirection(BOTTOM_TOP,event);
                    }
//                    swipeBottopmToTop();
                }

                break;
        }
        return true;
    }

    public interface SwipeToDirection
    {

        public void  onActionSwipedDirection(int swipeDirection,MotionEvent motionEvent);
    }


    public void setOnSwipeToDirection(SwipeToDirection onSwipeToDirection)
    {
        this.onSwipeToDirection = onSwipeToDirection;
    }



    public void setSwipeDistance(int swipeDistance)
    {
        this.swipeDistance= swipeDistance;
    }


}
