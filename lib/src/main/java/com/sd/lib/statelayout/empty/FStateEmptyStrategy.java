package com.sd.lib.statelayout.empty;

/**
 * 显示无内容状态策略接口
 */
public interface FStateEmptyStrategy {
    boolean isDestroyed();

    Result getResult();

    enum Result {
        Empty,
        Content,
        None,
    }
}
