package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewEmptyStrategy extends SourceCountEmptyStrategy<RecyclerView> {
    public RecyclerViewEmptyStrategy(@NonNull RecyclerView source) {
        super(source);
    }

    public RecyclerViewEmptyStrategy(@NonNull RecyclerView source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount(@NonNull RecyclerView source) {
        final RecyclerView.Adapter adapter = source.getAdapter();
        if (adapter == null) {
            return -1;
        }
        return adapter.getItemCount();
    }
}
