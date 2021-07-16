package com.sd.lib.statelayout;

import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

abstract class FViewListener<V extends View> {
    private WeakReference<V> mView;

    /**
     * 返回设置的view
     */
    public final V getView() {
        return mView == null ? null : mView.get();
    }

    /**
     * 设置要监听的view
     */
    public final void setView(V view) {
        final V old = getView();
        if (old != view) {
            stop();

            mView = view == null ? null : new WeakReference<>(view);
            onViewChanged(old, view);

            start();
        }
    }

    /**
     * 开始监听
     */
    public final void start() {
        final View view = getView();
        if (view == null) {
            return;
        }

        registerAttachStateChangeListener(view, true);
        registerViewTreeObserver(view, true);
    }

    /**
     * 停止监听
     */
    public final void stop() {
        final View view = getView();
        if (view == null) {
            return;
        }

        registerAttachStateChangeListener(view, false);
        registerViewTreeObserver(view, false);
    }

    private void registerAttachStateChangeListener(View view, boolean register) {
        view.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
        if (register) {
            view.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
        }
    }

    private void registerViewTreeObserver(View view, boolean register) {
        final ViewTreeObserver observer = view.getViewTreeObserver();
        if (observer.isAlive()) {
            observer.removeOnPreDrawListener(mOnPreDrawListener);
            if (register) {
                observer.addOnPreDrawListener(mOnPreDrawListener);
            }
        }
    }

    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            registerViewTreeObserver(v, true);
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            registerViewTreeObserver(v, false);
        }
    };

    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            onUpdate(getView());
            return true;
        }
    };

    protected void onViewChanged(V oldView, V newView) {
    }

    /**
     * 手动触发一次通知
     */
    public void update() {
    }

    protected abstract void onUpdate(V view);
}
