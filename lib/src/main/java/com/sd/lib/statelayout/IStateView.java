package com.sd.lib.statelayout;

import android.view.View;

import androidx.annotation.Nullable;

public interface IStateView {
    /**
     * 设置显示内容
     *
     * @param layoutId 内容View布局id
     */
    void setContentView(int layoutId);

    /**
     * 设置显示内容
     *
     * @param contentView 内容View
     */
    void setContentView(@Nullable View contentView);
}
