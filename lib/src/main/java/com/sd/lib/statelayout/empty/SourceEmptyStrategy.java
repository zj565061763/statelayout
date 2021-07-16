package com.sd.lib.statelayout.empty;

import java.lang.ref.WeakReference;

public abstract class SourceEmptyStrategy<S> implements FStateEmptyStrategy {
    private final WeakReference<S> mSource;

    public SourceEmptyStrategy(S source) {
        mSource = source == null ? null : new WeakReference<S>(source);
    }

    public final S getSource() {
        return mSource == null ? null : mSource.get();
    }

    @Override
    public boolean isDestroyed() {
        return getSource() == null;
    }

    @Override
    public final Result getResult() {
        final S source = getSource();
        if (source == null)
            return Result.None;

        return getResultImpl(source);
    }

    protected abstract Result getResultImpl(S source);
}
