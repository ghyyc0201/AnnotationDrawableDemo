package com.example.yuanyc.annotationdrawabledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yuanyc.view.MyView;

public class MainActivity extends AppCompatActivity {
    private MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view  = (MyView) findViewById(R.id.view);
        view.setBackground(view.getNaviTitleDrawable());
    }
}
