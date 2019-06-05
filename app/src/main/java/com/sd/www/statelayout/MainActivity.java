package com.sd.www.statelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.lib.statelayout.FStateLayout;


public class MainActivity extends AppCompatActivity
{
    private FStateLayout mStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateLayout = findViewById(R.id.view_state);
        mStateLayout.setContentTop(false);
    }

    public void onClickBtn(View view)
    {
        mStateLayout.setShowType(FStateLayout.ShowType.Error);
    }
}
