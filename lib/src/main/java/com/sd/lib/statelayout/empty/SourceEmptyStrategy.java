package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public abstract class SourceEmptyStrategy<S> implements FStateEmptyStrategy {
    private final WeakReference<S> mSource;

    public SourceEmptyStrategy(@Nullable S source) {
        mSource = source == null ? null : new WeakReference<>(source);
    }

    @Nullable
    public final S getSource() {
        return mSource == null ? null : mSource.get();
    }

    @Override
    public boolean isDestroyed() {
        return getSource() == null;
    }

    @NonNull
    @Override
    public final Result getResult() {
        final S source = getSource();
        if (source == null) {
            return Result.None;
        }

        return getResultImpl(source);
    }

    protected abstract Result getResultImpl(@NonNull S source);
}
