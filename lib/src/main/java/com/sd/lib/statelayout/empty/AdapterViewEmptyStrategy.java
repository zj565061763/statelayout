package com.sd.lib.statelayout.empty;

import android.widget.Adapter;
import android.widget.AdapterView;

import com.sd.lib.statelayout.FStateLayout;

public class AdapterViewEmptyStrategy implements FStateEmptyStrategy
{
    private final AdapterView mView;

    public AdapterViewEmptyStrategy(AdapterView view)
    {
        mView = view;
    }

    @Override
    public Result getResult(FStateLayout.ShowType showType)
    {
        if (showType == FStateLayout.ShowType.Error)
            return Result.None;

        if (mView == null)
            return Result.None;

        final Adapter adapter = mView.getAdapter();
        if (adapter == null)
            return Result.None;

        return adapter.getCount() <= 0 ? Result.Empty : Result.Content;
    }
}
