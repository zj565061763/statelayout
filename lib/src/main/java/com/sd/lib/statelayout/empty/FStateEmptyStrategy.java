package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;

/**
 * 无内容状态策略接口
 */
public interface FStateEmptyStrategy {
    boolean isDestroyed();

    @NonNull
    Result getResult();

    enum Result {
        /** 显示无内容 */
        Empty,
        /** 显示内容 */
        Content,
        /** 未知 */
        None,
    }
}
