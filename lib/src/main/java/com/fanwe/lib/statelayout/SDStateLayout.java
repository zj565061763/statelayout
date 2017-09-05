package com.fanwe.lib.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
    private TipView mErrorView;
    private TipView mEmptyView;

    private Callback mCallback;

    private void init(AttributeSet attrs)
    {

    }

    public static SDStateLayout wrap(View view)
    {
        if (view == null)
        {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent == null || !(parent instanceof ViewGroup))
        {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        int index = viewGroup.indexOfChild(view);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        viewGroup.removeView(view);

        SDStateLayout statesLayout = new SDStateLayout(view.getContext());
        statesLayout.setContentView(view);
        statesLayout.showContent();
        viewGroup.addView(statesLayout, index, params);
        return statesLayout;
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

    public TipView showError()
    {
        showView(getErrorView());
        hideView(mContentView);
        hideView(mEmptyView);
        return mEmptyView;
    }

    public TipView showEmpty()
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

    public TipView getErrorView()
    {
        if (mErrorView == null)
        {
            mErrorView = new TipView(getContext());
            addView(mErrorView);
            hideView(mErrorView);
        }
        mErrorView.setOnClickListener(this);
        return mErrorView;
    }

    public TipView getEmptyView()
    {
        if (mEmptyView == null)
        {
            mEmptyView = new TipView(getContext());
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
