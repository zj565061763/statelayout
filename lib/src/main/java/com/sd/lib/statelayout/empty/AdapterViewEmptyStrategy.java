package com.sd.lib.statelayout.empty;

import android.widget.Adapter;
import android.widget.AdapterView;

public class AdapterViewEmptyStrategy extends SourceCountEmptyStrategy<AdapterView>
{
    public AdapterViewEmptyStrategy(AdapterView source)
    {
        super(source);
    }

    public AdapterViewEmptyStrategy(AdapterView source, int emptyCount)
    {
        super(source, emptyCount);
    }

    @Override
    protected int getCount()
    {
        final Adapter adapter = getSource().getAdapter();
        if (adapter == null)
            return -1;

        return adapter.getCount();
    }
}
