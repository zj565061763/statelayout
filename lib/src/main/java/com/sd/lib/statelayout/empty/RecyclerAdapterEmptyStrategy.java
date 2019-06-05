package com.sd.lib.statelayout.empty;

import android.support.v7.widget.RecyclerView;

import com.sd.lib.statelayout.FStateLayout;

public class RecyclerAdapterEmptyStrategy implements FStateEmptyStrategy
{
    private RecyclerView.Adapter mAdapter;

    public RecyclerAdapterEmptyStrategy(RecyclerView.Adapter adapter)
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

        return mAdapter.getItemCount() <= 0 ? Result.Empty : Result.Content;
    }
}
