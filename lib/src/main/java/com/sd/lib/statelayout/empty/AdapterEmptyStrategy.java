package com.sd.lib.statelayout.empty;

import android.widget.Adapter;

import androidx.annotation.NonNull;

public class AdapterEmptyStrategy extends SourceCountEmptyStrategy<Adapter> {
    public AdapterEmptyStrategy(@NonNull Adapter source) {
        super(source);
    }

    public AdapterEmptyStrategy(@NonNull Adapter source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount(@NonNull Adapter source) {
        return source.getCount();
    }
}
