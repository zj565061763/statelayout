package com.fanwe.lib.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhengjun on 2017/9/5.
 */
public class SDStateLayout extends FrameLayout implements View.OnClickListener
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
    private StateView mErrorView;
    private StateView mEmptyView;

    private Callback mCallback;

    private void init(AttributeSet attrs)
    {

    }

    public SDStateLayout setCallback(Callback callback)
    {
        mCallback = callback;
        return this;
    }

    public void showContent()
    {
        showView(getContentView());
        hideView(mErrorView);
        hideView(mEmptyView);
    }

    public StateView showError()
    {
        showView(getErrorView());
        hideView(mContentView);
        hideView(mEmptyView);
        return mEmptyView;
    }

    public StateView showEmpty()
    {
        showView(getEmptyView());
        hideView(mContentView);
        hideView(mErrorView);
        return mErrorView;
    }

    private View getContentView()
    {
        if (mContentView != null && mContentView.getParent() != this)
        {
            addView(mContentView);
        }
        return mContentView;
    }

    public StateView getErrorView()
    {
        if (mErrorView == null)
        {
            mErrorView = new StateView(getContext());
            addView(mErrorView);
            hideView(mErrorView);
        }
        mErrorView.setOnClickListener(this);
        return mErrorView;
    }

    public StateView getEmptyView()
    {
        if (mEmptyView == null)
        {
            mEmptyView = new StateView(getContext());
            addView(mEmptyView);
            hideView(mEmptyView);
        }
        mEmptyView.setOnClickListener(this);
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
    public void onClick(View v)
    {
        if (v == mErrorView)
        {
            getCallback().onClickErrorView(v);
        } else if (v == mEmptyView)
        {
            getCallback().onClickEmptyView(v);
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

    private Callback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new Callback()
            {
                @Override
                public void onClickErrorView(View view)
                {
                }

                @Override
                public void onClickEmptyView(View view)
                {
                }
            };
        }
        return mCallback;
    }

    public interface Callback
    {
        void onClickErrorView(View view);

        void onClickEmptyView(View view);
    }
}
