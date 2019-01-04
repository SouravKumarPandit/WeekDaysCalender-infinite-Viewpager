package com.limitless.sourav.swipeactionlayout.viewlistviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class SwipeLoopViewPager extends ViewPager
{
    private ViewItemHolder[] viewItemHolders;
    private View[] tempViewHolder;
    private View[] viewArrayList;

    public SwipeLoopViewPager(Context context){
        super(context);
    }
    public SwipeLoopViewPager(Context context, View[] stringArray)
    {
        super(context);
        this.viewArrayList = stringArray;
        init();
    }

    private void init()
    {
        initAttrs();
        initCalenderView();
        setAdapter(new PagerAdapter()
        {
            @Override
            public int getCount()
            {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
            {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position)
            {
                position = position % 3;
                View calenderItemView = tempViewHolder[position];
                if (calenderItemView.getParent() != null)
                {
                    container.removeView(calenderItemView);
                }
                container.addView(calenderItemView);
                return calenderItemView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
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

                setPositionCalender(viewItemHolders[position % 3], position);
                if (getAdapter()!=null)
                getAdapter().notifyDataSetChanged();
                if (onCalenderPageChangeListener != null)
                {
                    onCalenderPageChangeListener.onChange(getCurrentCalender().getItemPosition(), getCurrentCalender().getViewItem());
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

    @SuppressLint("CustomViewStyleable")
    private void initAttrs()
    {
        //todo handle custom value for views
    }

    private void initCalenderView()
    {

        viewItemHolders = new ViewItemHolder[]{ViewSwipeUtil.getItemList(viewArrayList[0], 0),
                ViewSwipeUtil.nextUnit(viewArrayList[1], 1),
                ViewSwipeUtil.previousUnit(viewArrayList[viewArrayList.length - 1], viewArrayList.length - 1)};
        tempViewHolder = new View[viewItemHolders.length];
        for (int i = 0; i < tempViewHolder.length; i++)
        {
            View selectionItem = viewItemHolders[i].getViewItem();
            tempViewHolder[i] = selectionItem;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //this view only respect first child param and set param according to that
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (getAdapter() != null)
        {
            View calenderItemView = getChildAt(0);
            if (calenderItemView != null)
            {
                height = calenderItemView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    private void setPositionCalender(ViewItemHolder calenderWeekBean, int position)
    {
        position = position % 3;
        viewItemHolders[position] = calenderWeekBean;
        int viewPosition = calenderWeekBean.getItemPosition();
        viewPosition++;
        if (viewPosition == viewArrayList.length)
        {
            viewPosition = 0;
        }
        viewItemHolders[(position + 1) % 3] = ViewSwipeUtil.nextUnit(viewArrayList[viewPosition], viewPosition);
        viewPosition = calenderWeekBean.getItemPosition();
        viewPosition--;
        if (viewPosition < 0)
            viewPosition = viewArrayList.length - 1;
        viewItemHolders[(position - 1 + 3) % 3] = ViewSwipeUtil.previousUnit(viewArrayList[viewPosition], viewPosition);
        for (int i = 0; i < viewItemHolders.length; i++)
        {
            tempViewHolder[i] = viewItemHolders[i].getViewItem();
        }
    }

    public ViewItemHolder getCurrentCalender()
    {
        return viewItemHolders[getCurrentItem() % 3];
    }

    private OnCalenderPageChangeListener onCalenderPageChangeListener;

    public void setOnCalenderPageChangeListener(OnCalenderPageChangeListener onCalenderPageChangeListener)
    {
        this.onCalenderPageChangeListener = onCalenderPageChangeListener;
    }

    public void setViewArrayList(View[] viewArrayList)
    {
        this.viewArrayList = viewArrayList;
        invalidate();
    }

    public interface OnCalenderPageChangeListener
    {
        void onChange(int itemPosition, View viewItem);
    }
    static public class ViewSwipeUtil
    {
        static ViewItemHolder getItemList(View view, int iPosition) {
            return new ViewItemHolder(view, iPosition);
        }

        static ViewItemHolder previousUnit(View view, int iPosition) {

            return getItemList(view, iPosition);

        }
        static ViewItemHolder nextUnit(View view, int iPosition) {
            return getItemList(view, iPosition);
        }
    }


}
