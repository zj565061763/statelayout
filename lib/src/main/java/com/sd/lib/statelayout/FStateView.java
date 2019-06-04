package com.sd.lib.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class FStateView extends FrameLayout
{
    public FStateView(Context context)
    {
        super(context);
    }

    public FStateView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FStateView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置显示内容
     *
     * @param layoutId
     * @return
     */
    public FStateView setContentView(int layoutId)
    {
        final View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        return setContentView(view);
    }

    /**
     * 设置显示内容
     *
     * @param contentView
     * @return
     */
    public FStateView setContentView(View contentView)
    {
        removeAllViews();
        if (contentView != null)
            addView(contentView);

        return this;
    }
}
