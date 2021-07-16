package com.sd.lib.statelayout;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lib.statelayout.empty.AdapterEmptyStrategy;
import com.sd.lib.statelayout.empty.FStateEmptyStrategy;
import com.sd.lib.statelayout.empty.RecyclerAdapterEmptyStrategy;

import java.util.HashSet;
import java.util.Set;

public class FStateLayout extends FrameLayout {
    public FStateLayout(Context context) {
        super(context);
        init(null);
    }

    public FStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public enum ShowType {
        /** 显示无内容 */
        Empty,
        /** 显示错误 */
        Error,
        /** 显示内容 */
        Content,
    }

    /** 当前显示类型 */
    private ShowType mShowType = ShowType.Content;

    private final Set<IStateView> mStateViewHolder = new HashSet<>();
    /** 无内容状态View */
    private IStateView mEmptyView;
    /** 错误状态View */
    private IStateView mErrorView;

    /** 显示状态View的时候是否也显示内容View */
    private boolean mShowContentWhenState = true;
    /** 内容View是否在最顶层 */
    private boolean mContentTop = true;

    /** 无内容状态策略接口 */
    private FStateEmptyStrategy mEmptyStrategy;

    private void init(AttributeSet attrs) {

    }

    /**
     * 返回当前显示的类型
     */
    public ShowType getShowType() {
        return mShowType;
    }

    /**
     * {@link FStateEmptyStrategy}
     */
    public FStateEmptyStrategy getEmptyStrategy() {
        return mEmptyStrategy;
    }

    /**
     * {@link FStateEmptyStrategy}
     */
    public void setEmptyStrategy(FStateEmptyStrategy strategy) {
        if (mEmptyStrategy == strategy) {
            return;
        }

        mEmptyStrategy = strategy;

        if (strategy != null) {
            if (isAttached(this)) {
                mContentListener.start();
            }
        } else {
            mContentListener.stop();
        }
    }

    /**
     * 设置显示状态View的时候是否也显示内容View，默认true
     */
    public void setShowContentWhenState(boolean show) {
        mShowContentWhenState = show;
    }

    /**
     * 设置内容View是否在最顶层，默认true
     * <p>
     * 内容View是当前容器的child才有效
     *
     * @param top true-内容View在最顶层
     */
    public void setContentTop(boolean top) {
        mContentTop = top;
    }

    /**
     * 设置显示类型
     */
    public void setShowType(ShowType showType) {
        if (showType == null) {
            throw new IllegalArgumentException("showType is null");
        }

        if (mShowType != showType) {
            mShowType = showType;
            updateShowTypeInternal();
        }
    }

    private void updateShowTypeInternal() {
        switch (mShowType) {
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

    private void hideStateView() {
        for (IStateView item : mStateViewHolder) {
            hideView((View) item);
        }
    }

    private void showStateView(IStateView stateView) {
        showView((View) stateView);

        if (mShowContentWhenState) {
            showView(getContentView());
        } else {
            hideView(getContentView());
        }

        if (mContentTop) {
            if (mShowContentWhenState) {
                bringChildToFront(getContentView());
            }
        } else {
            bringChildToFront((View) stateView);
        }

        for (IStateView item : mStateViewHolder) {
            if (item != stateView) {
                hideView((View) item);
            }
        }
    }

    public View getContentView() {
        return mContentListener.getView();
    }

    public IStateView getErrorView() {
        if (mErrorView == null) {
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

    public IStateView getEmptyView() {
        if (mEmptyView == null) {
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
     */
    public void setContentView(View view) {
        mContentListener.setView(view);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        final int count = getChildCount();
        if (count == 1) {
            setContentView(getChildAt(0));
        } else if (count > 1) {
            if (child != mEmptyView && child != mErrorView) {
                throw new RuntimeException("Illegal child: " + child);
            }
        }
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (getContentView() == child) {
            setContentView(null);
        }

        if (child instanceof IStateView) {
            mStateViewHolder.remove(child);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mContentListener.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContentListener.stop();
    }

    /**
     * 监听内容View
     */
    private final FViewListener<View> mContentListener = new FViewListener<View>() {
        @Override
        protected void onUpdate(@NonNull View view) {
            checkEmptyStrategy();
        }

        @Override
        protected void onViewChanged(View oldView, View newView) {
            super.onViewChanged(oldView, newView);
            onContentViewChanged(oldView, newView);
        }
    };

    private void checkEmptyStrategy() {
        if (mEmptyStrategy == null) {
            mContentListener.stop();
            return;
        }

        if (mEmptyStrategy.isDestroyed()) {
            setEmptyStrategy(null);
            return;
        }

        final FStateEmptyStrategy.Result result = mEmptyStrategy.getResult();
        switch (result) {
            case Content:
                setShowType(ShowType.Content);
                break;
            case Empty:
                if (mShowType == ShowType.Content) {
                    setShowType(ShowType.Empty);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 内容View变化
     *
     * @param oldView
     * @param newView
     */
    protected void onContentViewChanged(@Nullable View oldView, @Nullable View newView) {
    }

    /**
     * 用{@link #setShowType(ShowType)} 替代
     */
    @Deprecated
    public void updateState(int dataCount) {
        if (dataCount > 0) {
            setShowType(ShowType.Content);
        } else {
            setShowType(ShowType.Empty);
        }
    }

    /**
     * 用{@link #setEmptyStrategy(FStateEmptyStrategy)}替代
     */
    @Deprecated
    public void setAdapter(BaseAdapter adapter) {
        setEmptyStrategy(new AdapterEmptyStrategy(adapter));
    }

    /**
     * 用{@link #setEmptyStrategy(FStateEmptyStrategy)}替代
     */
    @Deprecated
    public void setAdapter(RecyclerView.Adapter adapter) {
        setEmptyStrategy(new RecyclerAdapterEmptyStrategy(adapter));
    }

    private static void hideView(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
    }

    private static void showView(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
        }
    }

    private static int getLayoutId(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            return 0;
        }

        try {
            return context.getResources().getIdentifier(name, "layout", context.getPackageName());
        } catch (Exception e) {
            return 0;
        }
    }

    private static boolean isAttached(View view) {
        if (view == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= 19) {
            return view.isAttachedToWindow();
        } else {
            return view.getWindowToken() != null;
        }
    }
}
