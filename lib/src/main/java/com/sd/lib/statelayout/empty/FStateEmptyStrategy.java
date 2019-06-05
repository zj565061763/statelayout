package com.sd.lib.statelayout.empty;

import com.sd.lib.statelayout.FStateLayout;

/**
 * 显示无内容状态策略接口
 */
public interface FStateEmptyStrategy
{
    Result getResult(FStateLayout.ShowType showType);

    enum Result
    {
        Empty,
        Content,
        None,
    }
}
