package com.fanwe.lib.stateslayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by zhengjun on 2017/9/5.
 */
public class TipView extends FrameLayout
{
    public TipView(Context context)
    {
        super(context);
    }

    public TipView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TipView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public TipView setContentView(int layoutId)
    {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
        return this;
    }
}
