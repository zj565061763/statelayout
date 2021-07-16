package com.sd.lib.statelayout;

import android.view.View;

public interface IStateView {
    /**
     * 设置显示内容
     *
     * @param layoutId 内容view布局id
     */
    void setContentView(int layoutId);

    /**
     * 设置显示内容
     *
     * @param contentView 内容view
     */
    void setContentView(View contentView);
}
