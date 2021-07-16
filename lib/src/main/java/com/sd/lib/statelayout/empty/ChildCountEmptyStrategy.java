package com.sd.lib.statelayout.empty;

import android.view.ViewGroup;

public class ChildCountEmptyStrategy extends SourceCountEmptyStrategy<ViewGroup> {
    public ChildCountEmptyStrategy(ViewGroup source) {
        super(source);
    }

    public ChildCountEmptyStrategy(ViewGroup source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount() {
        return getSource().getChildCount();
    }
}
