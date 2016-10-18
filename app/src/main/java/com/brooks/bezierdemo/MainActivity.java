package com.brooks.bezierdemo;/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */

import com.brooks.bezierdemo.demo1.Demo1Activity;
import com.brooks.bezierdemo.demo2.Demo2Activity;
import com.brooks.bezierdemo.demo3.Demo3Activity;
import com.brooks.bezierdemo.demo4.Demo4Activity;
import com.brooks.bezierdemo.demo5.Demo5Activity;
import com.brooks.bezierdemo.demo6.Demo6Activity;
import com.brooks.bezierdemo.demo7.Demo7Activity;
import com.brooks.bezierdemo.demo8.Demo8Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.demo1).setOnClickListener(this);
        findViewById(R.id.demo2).setOnClickListener(this);
        findViewById(R.id.demo3).setOnClickListener(this);
        findViewById(R.id.demo4).setOnClickListener(this);
        findViewById(R.id.demo5).setOnClickListener(this);
        findViewById(R.id.demo6).setOnClickListener(this);
        findViewById(R.id.demo7).setOnClickListener(this);
        findViewById(R.id.demo8).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.demo1:
                startActivity(new Intent(MainActivity.this, Demo1Activity.class));
                break;
            case R.id.demo2:
                startActivity(new Intent(MainActivity.this, Demo2Activity.class));
                break;
            case R.id.demo3:
                startActivity(new Intent(MainActivity.this, Demo3Activity.class));
                break;
            case R.id.demo4:
                startActivity(new Intent(MainActivity.this, Demo4Activity.class));
                break;
            case R.id.demo5:
                startActivity(new Intent(MainActivity.this, Demo5Activity.class));
                break;
            case R.id.demo6:
                startActivity(new Intent(MainActivity.this, Demo6Activity.class));
                break;
            case R.id.demo7:
                startActivity(new Intent(MainActivity.this, Demo7Activity.class));
                break;
            case R.id.demo8:
                startActivity(new Intent(MainActivity.this, Demo8Activity.class));
        }
    }
}
