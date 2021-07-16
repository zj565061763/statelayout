package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;

public abstract class CountEmptyStrategy implements FStateEmptyStrategy {
    private final int mEmptyCount;

    public CountEmptyStrategy(int emptyCount) {
        if (emptyCount < 0) {
            throw new IllegalArgumentException("emptyCount must >= 0");
        }

        mEmptyCount = emptyCount;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @NonNull
    @Override
    public final Result getResult() {
        final int count = getCount();
        if (count < 0) {
            return Result.None;
        } else if (count <= mEmptyCount) {
            return Result.Empty;
        } else {
            return Result.Content;
        }
    }

    protected abstract int getCount();
}
