package com.sd.lib.statelayout.empty;

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

    @Override
    protected final Result getResultImpl(S source) {
        return mCountEmptyStrategy.getResult();
    }

    protected abstract int getCount();
}
