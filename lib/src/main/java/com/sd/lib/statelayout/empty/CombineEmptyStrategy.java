package com.sd.lib.statelayout.empty;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CombineEmptyStrategy implements FStateEmptyStrategy {
    private final Map<FStateEmptyStrategy, String> mHolder = new ConcurrentHashMap<>();

    public CombineEmptyStrategy(@NonNull FStateEmptyStrategy... strategies) {
        for (FStateEmptyStrategy item : strategies) {
            if (item == null) {
                throw new IllegalArgumentException("strategies item is null");
            }
            mHolder.put(item, "");
        }
    }

    @Override
    public boolean isDestroyed() {
        return mHolder.isEmpty();
    }

    @NonNull
    @Override
    public Result getResult() {
        for (FStateEmptyStrategy item : mHolder.keySet()) {
            if (item.isDestroyed()) {
                mHolder.remove(item);
            } else {
                final Result itemResult = item.getResult();
                if (itemResult == Result.Content) {
                    return Result.Content;
                } else if (itemResult == Result.None) {
                    return Result.None;
                }
            }
        }

        if (mHolder.isEmpty()) {
            return Result.None;
        }

        return Result.Empty;
    }
}
