package com.fanwe.lib.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by zhengjun on 2017/9/5.
 */
public class StateView extends FrameLayout
{
    public StateView(Context context)
    {
        super(context);
    }

    public StateView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public StateView setContentView(int layoutId)
    {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
        return this;
    }
}
