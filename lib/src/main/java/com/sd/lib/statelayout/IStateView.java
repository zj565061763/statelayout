package com.sd.lib.statelayout;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

public interface IStateView {
    /**
     * 设置显示内容
     *
     * @param layoutId 内容view布局id
     */
    void setContentView( int layoutId);

    /**
     * 设置显示内容
     *
     * @param contentView 内容view
     */
    void setContentView(@Nullable View contentView);
}
