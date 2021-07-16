package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;

public abstract class SourceCountEmptyStrategy<S> extends SourceEmptyStrategy<S> {
    private final CountEmptyStrategy mCountEmptyStrategy;

    public SourceCountEmptyStrategy(S source) {
        this(source, 0);
    }

    public SourceCountEmptyStrategy(S source, int emptyCount) {
        super(source);
        mCountEmptyStrategy = new CountEmptyStrategy(emptyCount) {
            @Override
            protected int getCount() {
                return SourceCountEmptyStrategy.this.getCount();
            }
        };
    }

    @NonNull
    @Override
    protected final Result getResultImpl(@NonNull S source) {
        return mCountEmptyStrategy.getResult();
    }

    protected abstract int getCount();
}
