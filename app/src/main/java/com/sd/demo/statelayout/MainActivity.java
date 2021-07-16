package com.sd.demo.statelayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.adapter.FSimpleAdapter;
import com.sd.lib.statelayout.FAutoEmptyStateLayout;

public class MainActivity extends AppCompatActivity {
    private FAutoEmptyStateLayout view_state;
    private ListView lv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_state = findViewById(R.id.view_state);
        lv_content = findViewById(R.id.lv_content);
        lv_content.setAdapter(mAdapter);

        // 设置自动空布局策略，监听ListView或者RecyclerView的内容自动展示空布局
        view_state.autoEmpty();
    }

    public void onClickBtn(View view) {
        if (mAdapter.getDataHolder().size() == 0) {
            for (int i = 0; i < 10; i++) {
                mAdapter.getDataHolder().add(String.valueOf(i));
            }
        } else {
            mAdapter.getDataHolder().setData(null);
        }
    }

    private final FSimpleAdapter<String> mAdapter = new FSimpleAdapter<String>() {
        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent) {
            return R.layout.item_content;
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, String model) {
            final TextView tv_content = get(R.id.tv_content, convertView);
            tv_content.setText(model);
        }
    };
}
