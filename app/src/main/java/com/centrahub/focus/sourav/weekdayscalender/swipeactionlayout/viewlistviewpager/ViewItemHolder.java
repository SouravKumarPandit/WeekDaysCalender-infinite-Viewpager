package com.limitless.sourav.swipeactionlayout.viewlistviewpager;

import android.view.View;

/**
 * Created by sourav on 29-Jan-18.
 */

public class ViewItemHolder
{

    private int itemPosition;
    private View viewItem;
    private String viewTag;
    public ViewItemHolder(View preValue, int iPosition)
    {
        this.setViewItem(preValue);
        this.setItemPosition(iPosition);
        String viewTag="";
        if (preValue.getTag()!=null)
            viewTag=preValue.getTag().toString();
        this.setViewTag(viewTag);

    }

    public View getViewItem()
    {
        return viewItem;
    }

    public int getItemPosition()
    {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition)
    {
        this.itemPosition = itemPosition;
    }

    public String getViewTag()
    {
        return viewTag;
    }

    public void setViewTag(String viewTag)
    {
        this.viewTag = viewTag;
    }

    public void setViewItem(View viewItem)
    {
        this.viewItem = viewItem;
    }

}