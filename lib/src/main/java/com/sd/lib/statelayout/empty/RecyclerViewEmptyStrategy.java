package com.sd.lib.statelayout.empty;

import android.support.v7.widget.RecyclerView;

public class RecyclerViewEmptyStrategy extends SourceCountEmptyStrategy<RecyclerView>
{
    public RecyclerViewEmptyStrategy(RecyclerView source)
    {
        super(source);
    }

    public RecyclerViewEmptyStrategy(RecyclerView source, int emptyCount)
    {
        super(source, emptyCount);
    }

    @Override
    protected int getCount()
    {
        final RecyclerView.Adapter adapter = getSource().getAdapter();
        if (adapter == null)
            return -1;

        return adapter.getItemCount();
    }
}
