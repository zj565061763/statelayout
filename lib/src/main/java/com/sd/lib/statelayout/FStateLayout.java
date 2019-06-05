package com.sd.lib.statelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.sd.lib.statelayout.empty.AdapterEmptyStrategy;
import com.sd.lib.statelayout.empty.FStateEmptyStrategy;
import com.sd.lib.statelayout.empty.RecyclerAdapterEmptyStrategy;

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

    public enum ShowType
    {
        Empty,
        Error,
        Content,
    }

    private ShowType mShowType = ShowType.Content;

    private IStateView mEmptyView;
    private IStateView mErrorView;
    private final Set<IStateView> mStateViewHolder = new HashSet<>();

    private boolean mShowContentWhenState = true;
    private boolean mContentTop = true;

    private FStateEmptyStrategy mEmptyStrategy;

    private void init(AttributeSet attrs)
    {

    }

    /**
     * 返回当前显示的类型
     *
     * @return
     */
    public ShowType getShowType()
    {
        return mShowType;
    }

    /**
     * {@link FStateEmptyStrategy}
     *
     * @param strategy
     */
    public void setEmptyStrategy(FStateEmptyStrategy strategy)
    {
        if (mEmptyStrategy != strategy)
        {
            mEmptyStrategy = strategy;
            if (strategy != null)
                mContentListener.start();
        }
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
     * <p>
     * 内容view是当前FStateLayout的child才有效
     *
     * @param top true-内容view在最顶层
     */
    public void setContentTop(boolean top)
    {
        mContentTop = top;
    }

    /**
     * 设置显示类型
     *
     * @param showType
     */
    public void setShowType(ShowType showType)
    {
        if (showType == null)
            throw new IllegalArgumentException("showType is null");

        if (mShowType != showType)
        {
            mShowType = showType;
            updateShowTypeInternal();
        }
    }

    private void updateShowTypeInternal()
    {
        switch (mShowType)
        {
            case Content:
                showView(getContentView());
                hideStateView();
                break;
            case Empty:
                showStateView(getEmptyView());
                break;
            case Error:
                showStateView(getErrorView());
                break;
        }
    }

    private void hideStateView()
    {
        for (IStateView item : mStateViewHolder)
        {
            hideView((View) item);
        }
    }

    private void showStateView(IStateView stateView)
    {
        showView((View) stateView);

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
            bringChildToFront((View) stateView);
        }

        for (IStateView item : mStateViewHolder)
        {
            if (item != stateView)
                hideView((View) item);
        }
    }

    private View getContentView()
    {
        return mContentListener.getView();
    }

    public IStateView getErrorView()
    {
        if (mErrorView == null)
        {
            final SimpleStateView simpleStateView = new SimpleStateView(getContext());
            mErrorView = simpleStateView;

            final int layoutId = getLayoutId(getContext(), getResources().getString(R.string.lib_statelayout_error_layout));
            mErrorView.setContentView(layoutId);

            addView(simpleStateView);
            hideView(simpleStateView);
            mStateViewHolder.add(simpleStateView);
        }
        return mErrorView;
    }

    public IStateView getEmptyView()
    {
        if (mEmptyView == null)
        {
            final SimpleStateView simpleStateView = new SimpleStateView(getContext());
            mEmptyView = simpleStateView;

            final int layoutId = getLayoutId(getContext(), getResources().getString(R.string.lib_statelayout_empty_layout));
            mEmptyView.setContentView(layoutId);

            addView(simpleStateView);
            hideView(simpleStateView);
            mStateViewHolder.add(simpleStateView);
        }
        return mEmptyView;
    }

    /**
     * 设置内容View
     *
     * @param view
     */
    public void setContentView(View view)
    {
        mContentListener.setView(view);
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);

        final int count = getChildCount();
        if (count == 1)
        {
            setContentView(getChildAt(0));
        } else if (count > 1)
        {
            if (child != mEmptyView && child != mErrorView)
                throw new RuntimeException("Illegal child: " + child);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (getContentView() == child)
            setContentView(null);

        if (child instanceof IStateView)
            mStateViewHolder.remove(child);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mContentListener.start();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mContentListener.stop();
    }

    private final FViewListener<View> mContentListener = new FViewListener<View>()
    {
        @Override
        protected void onUpdate(View view)
        {
            if (mEmptyStrategy != null)
            {
                if (mEmptyStrategy.isDestroyed())
                {
                    setEmptyStrategy(null);
                    return;
                }

                final FStateEmptyStrategy.Result result = mEmptyStrategy.getResult();
                if (result == null)
                    throw new RuntimeException("Strategy result is null");

                if (result == FStateEmptyStrategy.Result.Empty)
                    setShowType(ShowType.Empty);
                else if (result == FStateEmptyStrategy.Result.Content)
                    setShowType(ShowType.Content);
            } else
            {
                mContentListener.stop();
            }
        }
    };

    /**
     * 用{@link #setShowType(ShowType)} 替代
     *
     * @param dataCount
     */
    @Deprecated
    public void updateState(int dataCount)
    {
        if (dataCount > 0)
        {
            setShowType(ShowType.Content);
        } else
        {
            setShowType(ShowType.Empty);
        }
    }

    /**
     * 用{@link #setEmptyStrategy(FStateEmptyStrategy)}替代
     *
     * @param adapter
     */
    @Deprecated
    public void setAdapter(BaseAdapter adapter)
    {
        setEmptyStrategy(new AdapterEmptyStrategy(adapter));
    }

    /**
     * 用{@link #setEmptyStrategy(FStateEmptyStrategy)}替代
     *
     * @param adapter
     */
    @Deprecated
    public void setAdapter(RecyclerView.Adapter adapter)
    {
        setEmptyStrategy(new RecyclerAdapterEmptyStrategy(adapter));
    }

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
