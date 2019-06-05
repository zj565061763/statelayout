package com.sd.lib.statelayout.empty;

import android.widget.Adapter;

public class AdapterEmptyStrategy extends SourceCountEmptyStrategy<Adapter>
{
    public AdapterEmptyStrategy(Adapter source)
    {
        super(source);
    }

    public AdapterEmptyStrategy(Adapter source, int emptyCount)
    {
        super(source, emptyCount);
    }

    @Override
    protected int getCount()
    {
        return getSource().getCount();
    }
}
