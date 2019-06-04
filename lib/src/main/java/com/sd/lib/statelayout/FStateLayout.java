package com.sd.lib.statelayout;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

public class FStateLayout extends FrameLayout
{
    public FStateLayout(Context context)
    {
        super(context);
        init(null);
    }

    public FStateLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public FStateLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private View mContentView;
    private FStateView mErrorView;
    private FStateView mEmptyView;

    private boolean mContentTop = true;

    private BaseAdapter mBaseAdapter;
    private DataSetObserver mBaseAdapterDataSetObserver;

    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.AdapterDataObserver mRecyclerAdapterDataSetObserver;

    private void init(AttributeSet attrs)
    {

    }

    /**
     * 显示内容
     */
    public void showContent()
    {
        showView(getContentView());

        hideView(mErrorView);
        hideView(mEmptyView);
    }

    /**
     * 显示错误
     */
    public void showError()
    {
        showView(getErrorView());
        showView(getContentView());

        if (mContentTop)
        {
            bringChildToFront(getContentView());
        } else
        {
            bringChildToFront(getErrorView());
        }

        hideView(mEmptyView);
    }

    /**
     * 显示无内容
     */
    public void showEmpty()
    {
        showView(getEmptyView());
        showView(getContentView());

        if (mContentTop)
        {
            bringChildToFront(getContentView());
        } else
        {
            bringChildToFront(getEmptyView());
        }

        hideView(mErrorView);
    }

    private View getContentView()
    {
        return mContentView;
    }

    public FStateView getErrorView()
    {
        if (mErrorView == null)
        {
            mErrorView = new FStateView(getContext());
            addView(mErrorView);
            hideView(mErrorView);
        }
        return mErrorView;
    }

    public FStateView getEmptyView()
    {
        if (mEmptyView == null)
        {
            mEmptyView = new FStateView(getContext());
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
        view.setVisibility(View.GONE);
    }

    private void showView(View view)
    {
        if (view == null)
        {
            return;
        }
        view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        if (getChildCount() > 1)
            throw new RuntimeException(FStateLayout.class.getSimpleName() + " can only add one child");

        setContentView(getChildAt(0));
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);
        if (getChildCount() > 1)
        {
            if (child != mEmptyView && child != mErrorView)
                throw new RuntimeException("Illegal child: " + child);
        }
    }

    /**
     * 更新状态view
     *
     * @param dataCount 数据数量大于0，显示内容；数据数量小于等于0，显示空内容
     */
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

    public boolean isContentTop()
    {
        return mContentTop;
    }

    /**
     * 设置内容view是否在最顶层，默认true
     *
     * @param contentTop true-内容view在最顶层
     */
    public void setContentTop(boolean contentTop)
    {
        mContentTop = contentTop;
    }

    //---------- BaseAdapter start ----------

    /**
     * 设置要监听的适配器
     *
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter)
    {
        if (mBaseAdapter != adapter)
        {
            if (mBaseAdapter != null)
                mBaseAdapter.unregisterDataSetObserver(getBaseAdapterDataSetObserver());

            mBaseAdapter = adapter;

            if (adapter != null)
            {
                adapter.registerDataSetObserver(getBaseAdapterDataSetObserver());
            } else
            {
                mBaseAdapterDataSetObserver = null;
            }
        }
    }

    private DataSetObserver getBaseAdapterDataSetObserver()
    {
        if (mBaseAdapterDataSetObserver == null)
        {
            mBaseAdapterDataSetObserver = new DataSetObserver()
            {
                @Override
                public void onChanged()
                {
                    super.onChanged();
                    if (mBaseAdapter != null)
                        updateState(mBaseAdapter.getCount());
                }

                @Override
                public void onInvalidated()
                {
                    super.onInvalidated();
                }
            };
        }
        return mBaseAdapterDataSetObserver;
    }

    //---------- BaseAdapter end ----------


    //---------- RecyclerAdapter start ----------

    /**
     * 设置要监听的适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter)
    {
        if (mRecyclerAdapter != adapter)
        {
            if (mRecyclerAdapter != null)
                mRecyclerAdapter.unregisterAdapterDataObserver(getRecyclerAdapterDataSetObserver());

            mRecyclerAdapter = adapter;

            if (adapter != null)
            {
                adapter.registerAdapterDataObserver(getRecyclerAdapterDataSetObserver());
            } else
            {
                mRecyclerAdapterDataSetObserver = null;
            }
        }
    }

    private RecyclerView.AdapterDataObserver getRecyclerAdapterDataSetObserver()
    {
        if (mRecyclerAdapterDataSetObserver == null)
        {
            mRecyclerAdapterDataSetObserver = new RecyclerView.AdapterDataObserver()
            {
                @Override
                public void onChanged()
                {
                    super.onChanged();
                    if (mRecyclerAdapter != null)
                        updateState(mRecyclerAdapter.getItemCount());
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount)
                {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    if (mRecyclerAdapter != null)
                        updateState(mRecyclerAdapter.getItemCount());
                }
            };
        }
        return mRecyclerAdapterDataSetObserver;
    }

    //---------- RecyclerAdapter end ----------


}
