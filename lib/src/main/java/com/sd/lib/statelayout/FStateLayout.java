package com.sd.lib.statelayout;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import java.util.HashSet;
import java.util.Set;

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

    private FStateView mEmptyView;
    private FStateView mErrorView;
    private final Set<FStateView> mStateViewHolder = new HashSet<>();

    private boolean mShowContentWhenState = true;
    private boolean mContentTop = true;

    private BaseAdapter mBaseAdapter;
    private DataSetObserver mBaseAdapterDataSetObserver;

    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.AdapterDataObserver mRecyclerAdapterDataSetObserver;

    private void init(AttributeSet attrs)
    {

    }

    /**
     * 设置显示状态view的时候是否也显示内容view，默认true-显示
     *
     * @param show
     */
    public void setShowContentWhenState(boolean show)
    {
        mShowContentWhenState = show;
    }

    /**
     * 设置内容view是否在最顶层，默认true
     *
     * @param top true-内容view在最顶层
     */
    public void setContentTop(boolean top)
    {
        mContentTop = top;
    }

    /**
     * 显示内容
     */
    public void showContent()
    {
        showView(getContentView());

        for (FStateView item : mStateViewHolder)
        {
            hideView(item);
        }
    }

    /**
     * 显示无内容
     */
    public void showEmpty()
    {
        showStateView(getEmptyView());
    }

    /**
     * 显示错误
     */
    public void showError()
    {
        showStateView(getErrorView());
    }

    private void showStateView(FStateView stateView)
    {
        showView(stateView);

        if (mShowContentWhenState)
            showView(getContentView());
        else
            hideView(getContentView());

        if (mContentTop)
        {
            if (mShowContentWhenState)
                bringChildToFront(getContentView());
        } else
        {
            bringChildToFront(stateView);
        }

        for (FStateView item : mStateViewHolder)
        {
            if (item != stateView)
                hideView(item);
        }
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

            final String layoutName = getResources().getString(R.string.lib_statelayout_error_layout);
            final int layoutId = getLayoutId(getContext(), layoutName);
            if (layoutId != 0)
                mErrorView.setContentView(layoutId);
        }
        return mErrorView;
    }

    public FStateView getEmptyView()
    {
        if (mEmptyView == null)
        {
            mEmptyView = new FStateView(getContext());
            addView(mEmptyView);

            final String layoutName = getResources().getString(R.string.lib_statelayout_empty_layout);
            final int layoutId = getLayoutId(getContext(), layoutName);
            if (layoutId != 0)
                mEmptyView.setContentView(layoutId);
        }
        return mEmptyView;
    }

    private void setContentView(View view)
    {
        mContentView = view;
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

        if (child instanceof FStateView)
        {
            mStateViewHolder.add((FStateView) child);
            hideView(child);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (child instanceof FStateView)
            mStateViewHolder.remove(child);
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

    private static void hideView(View view)
    {
        if (view != null && view.getVisibility() != GONE)
            view.setVisibility(GONE);
    }

    private static void showView(View view)
    {
        if (view != null && view.getVisibility() != VISIBLE)
            view.setVisibility(VISIBLE);
    }

    private static int getLayoutId(Context context, String name)
    {
        if (TextUtils.isEmpty(name))
            return 0;

        try
        {
            return context.getResources().getIdentifier(name, "layout", context.getPackageName());
        } catch (Exception e)
        {
            return 0;
        }
    }
}
