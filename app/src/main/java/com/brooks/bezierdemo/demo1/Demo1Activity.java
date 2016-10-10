
package com.brooks.bezierdemo.demo1;

import com.brooks.bezierdemo.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Demo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        TipBubble tipBubble = (TipBubble) findViewById(R.id.tip_buddle);
        tipBubble.setNumberText("78");
    }
}
