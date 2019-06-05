package com.sd.lib.statelayout.empty;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CombineEmptyStrategy implements FStateEmptyStrategy
{
    private final List<FStateEmptyStrategy> mList = new CopyOnWriteArrayList<>();

    public CombineEmptyStrategy(FStateEmptyStrategy... strategies)
    {
        if (strategies != null)
        {
            for (FStateEmptyStrategy item : strategies)
            {
                if (item == null)
                    throw new IllegalArgumentException("strategies item is null");

                mList.add(item);
            }
        }
    }

    @Override
    public boolean isDestroyed()
    {
        return mList.isEmpty();
    }

    @Override
    public Result getResult()
    {
        for (FStateEmptyStrategy item : mList)
        {
            if (item.isDestroyed())
            {
                mList.remove(item);
            } else
            {
                final Result itemResult = item.getResult();
                if (itemResult == Result.Content)
                    return Result.Content;
                else if (itemResult == Result.None)
                    return Result.None;
            }
        }

        if (mList.isEmpty())
            return Result.None;

        return Result.Empty;
    }
}
