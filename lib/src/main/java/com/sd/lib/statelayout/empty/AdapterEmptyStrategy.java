package com.sd.lib.statelayout.empty;

import android.widget.Adapter;

import com.sd.lib.statelayout.FStateLayout;

public class AdapterEmptyStrategy implements FStateEmptyStrategy
{
    private final Adapter mAdapter;

    public AdapterEmptyStrategy(Adapter adapter)
    {
        mAdapter = adapter;
    }

    @Override
    public Result getResult(FStateLayout.ShowType showType)
    {
        if (showType == FStateLayout.ShowType.Error)
            return Result.None;

        if (mAdapter == null)
            return Result.None;

        return mAdapter.getCount() <= 0 ? Result.Empty : Result.Content;
    }
}
