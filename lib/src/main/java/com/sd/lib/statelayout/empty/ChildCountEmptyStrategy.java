package com.sd.lib.statelayout.empty;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class ChildCountEmptyStrategy extends SourceCountEmptyStrategy<ViewGroup> {
    public ChildCountEmptyStrategy(@NonNull ViewGroup source) {
        super(source);
    }

    public ChildCountEmptyStrategy(@NonNull ViewGroup source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount(@NonNull ViewGroup source) {
        return source.getChildCount();
    }
}
