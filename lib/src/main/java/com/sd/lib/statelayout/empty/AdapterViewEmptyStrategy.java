package com.sd.lib.statelayout.empty;

import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

public class AdapterViewEmptyStrategy extends SourceCountEmptyStrategy<AdapterView> {
    public AdapterViewEmptyStrategy(@NonNull AdapterView source) {
        super(source);
    }

    public AdapterViewEmptyStrategy(@NonNull AdapterView source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount(@NonNull AdapterView source) {
        final Adapter adapter = source.getAdapter();
        if (adapter == null) {
            return -1;
        }
        return adapter.getCount();
    }
}
