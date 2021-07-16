package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterEmptyStrategy extends SourceCountEmptyStrategy<RecyclerView.Adapter> {
    public RecyclerAdapterEmptyStrategy(@NonNull RecyclerView.Adapter source) {
        super(source);
    }

    public RecyclerAdapterEmptyStrategy(@NonNull RecyclerView.Adapter source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount(@NonNull RecyclerView.Adapter source) {
        return source.getItemCount();
    }
}
