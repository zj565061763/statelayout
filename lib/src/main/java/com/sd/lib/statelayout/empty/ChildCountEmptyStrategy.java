package com.sd.lib.statelayout.empty;

import android.view.ViewGroup;

import com.sd.lib.statelayout.FStateLayout;

public class ChildCountEmptyStrategy implements FStateEmptyStrategy
{
    private final ViewGroup mViewGroup;
    private final int mEmptyCount;

    public ChildCountEmptyStrategy(ViewGroup viewGroup)
    {
        this(viewGroup, 0);
    }

    public ChildCountEmptyStrategy(ViewGroup viewGroup, int emptyCount)
    {
        if (emptyCount < 0)
            emptyCount = 0;

        mViewGroup = viewGroup;
        mEmptyCount = emptyCount;
    }

    @Override
    public Result getResult(FStateLayout.ShowType showType)
    {
        if (showType == FStateLayout.ShowType.Error)
            return Result.None;

        if (mViewGroup == null)
            return Result.None;

        return mViewGroup.getChildCount() <= mEmptyCount ? Result.Empty : Result.Content;
    }
}
