package com.fanwe.lib.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhengjun on 2017/9/5.
 */
public class SDStateLayout extends FrameLayout
{
    public SDStateLayout(Context context)
    {
        super(context);
        init(null);
    }

    public SDStateLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public SDStateLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private View mContentView;
    private SDStateView mErrorView;
    private SDStateView mEmptyView;

    private void init(AttributeSet attrs)
    {

    }

    public void showContent()
    {
        showView(getContentView());

        hideView(mErrorView);
        hideView(mEmptyView);
    }

    public void showError()
    {
        showView(getErrorView());
        showView(getContentView());

        hideView(mEmptyView);
    }

    public void showEmpty()
    {
        showView(getEmptyView());
        showView(getContentView());

        hideView(mErrorView);
    }

    private View getContentView()
    {
        return mContentView;
    }

    public SDStateView getErrorView()
    {
        if (mErrorView == null)
        {
            mErrorView = new SDStateView(getContext());
            addView(mErrorView);
            hideView(mErrorView);
        }
        return mErrorView;
    }

    public SDStateView getEmptyView()
    {
        if (mEmptyView == null)
        {
            mEmptyView = new SDStateView(getContext());
            addView(mEmptyView);
            hideView(mEmptyView);
        }
        return mEmptyView;
    }

    private void setContentView(View view)
    {
        mContentView = view;
    }

    private void hideView(View view)
    {
        if (view == null)
        {
            return;
        }
        if (view.getVisibility() != View.GONE)
        {
            view.setVisibility(View.GONE);
        }
    }

    private void showView(View view)
    {
        if (view == null)
        {
            return;
        }
        if (view.getVisibility() != View.VISIBLE)
        {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        if (getChildCount() > 1)
        {
            throw new IllegalArgumentException("SDStateLayout can only add one child");
        }
        setContentView(getChildAt(0));
    }

    public void updateState(int dataCount)
    {
        if (dataCount > 0)
        {
            showContent();
        } else
        {
            showEmpty();
        }
    }
}
