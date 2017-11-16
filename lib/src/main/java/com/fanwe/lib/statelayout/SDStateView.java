package com.fanwe.lib.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhengjun on 2017/9/5.
 */
public class SDStateView extends FrameLayout
{
    public SDStateView(Context context)
    {
        super(context);
    }

    public SDStateView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDStateView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public SDStateView setContentView(int layoutId)
    {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        return setContentView(view);
    }

    public SDStateView setContentView(View contentView)
    {
        removeAllViews();
        if (contentView != null)
        {
            addView(contentView);
        }
        return this;
    }
}
