package com.sd.lib.statelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sd.lib.statelayout.empty.AdapterViewEmptyStrategy;
import com.sd.lib.statelayout.empty.CombineEmptyStrategy;
import com.sd.lib.statelayout.empty.FStateEmptyStrategy;
import com.sd.lib.statelayout.empty.RecyclerViewEmptyStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FAutoStateLayout extends FStateLayout
{
    public FAutoStateLayout(Context context)
    {
        super(context);
    }

    public FAutoStateLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FAutoStateLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onContentViewChanged(View oldView, View newView)
    {
        super.onContentViewChanged(oldView, newView);
        autoEmptyStrategy(newView);
    }

    private void autoEmptyStrategy(View view)
    {
        if (view == null)
            return;

        final List<FStateEmptyStrategy> listStrategy = new LinkedList<>();

        final List<View> list = getAllViews(view);
        for (View item : list)
        {
            if (item instanceof AdapterView)
            {
                listStrategy.add(new AdapterViewEmptyStrategy((AdapterView) item));
            } else if (item instanceof RecyclerView)
            {
                listStrategy.add(new RecyclerViewEmptyStrategy((RecyclerView) item));
            }
        }

        final int count = listStrategy.size();
        if (count <= 0)
            return;

        if (count == 1)
        {
            setEmptyStrategy(listStrategy.get(0));
        } else
        {
            final FStateEmptyStrategy[] array = new FStateEmptyStrategy[count];
            setEmptyStrategy(new CombineEmptyStrategy(listStrategy.toArray(array)));
        }
    }

    private static List<View> getAllViews(View view)
    {
        if (view == null)
            throw new IllegalArgumentException("view is null when getAllViews()");

        final List<View> list = new ArrayList<>();

        list.add(view);
        if (view instanceof ViewGroup)
        {
            final ViewGroup viewGroup = (ViewGroup) view;
            final int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++)
            {
                final View child = viewGroup.getChildAt(i);
                if (child != null)
                    list.addAll(getAllViews(child));
            }
        }
        return list;
    }
}
