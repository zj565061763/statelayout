package com.sd.lib.statelayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

class SimpleStateView extends FrameLayout implements IStateView
{
    public SimpleStateView(Context context)
    {
        super(context);
    }

    @Override
    public void setContentView(int layoutId)
    {
        final View view = layoutId == 0 ? null : LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        setContentView(view);
    }

    @Override
    public void setContentView(View contentView)
    {
        removeAllViews();
        if (contentView != null)
            addView(contentView);
    }
}
