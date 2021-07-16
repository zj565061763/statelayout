package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;

public abstract class SourceCountEmptyStrategy<S> extends SourceEmptyStrategy<S> {
    private final CountEmptyStrategy mCountEmptyStrategy;

    public SourceCountEmptyStrategy(@NonNull S source) {
        this(source, 0);
    }

    public SourceCountEmptyStrategy(@NonNull S source, int emptyCount) {
        super(source);
        mCountEmptyStrategy = new CountEmptyStrategy(emptyCount) {
            @Override
            protected int getCount() {
                final S src = getSource();
                if (src == null) {
                    return -1;
                }
                return SourceCountEmptyStrategy.this.getCount(src);
            }
        };
    }

    @NonNull
    @Override
    protected final Result getResultImpl(@NonNull S source) {
        return mCountEmptyStrategy.getResult();
    }

    protected abstract int getCount(@NonNull S source);
}
