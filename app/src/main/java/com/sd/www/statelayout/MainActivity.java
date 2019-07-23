package com.sd.www.statelayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.adapter.FSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView lv_content;
    private final List<String> mListModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_content = findViewById(R.id.lv_content);
        lv_content.setAdapter(mAdapter);
    }

    public void onClickBtn(View view)
    {
        if (mListModel.isEmpty())
        {
            for (int i = 0; i < 10; i++)
            {
                mListModel.add(String.valueOf(i));
            }
        } else
        {
            mListModel.clear();
        }
        mAdapter.getDataHolder().setData(mListModel);
    }

    private final FSimpleAdapter<String> mAdapter = new FSimpleAdapter<String>()
    {
        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_content;
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, String model)
        {
            final TextView tv_content = get(R.id.tv_content, convertView);
            tv_content.setText(model);
        }
    };
}
