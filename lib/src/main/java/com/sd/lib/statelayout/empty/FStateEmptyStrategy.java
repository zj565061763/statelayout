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
        Empty,
        Content,
        None,
    }
}
