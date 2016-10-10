
package com.brooks.bezierdemo.demo4;

import com.brooks.bezierdemo.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Demo4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo4);
        Button sendHeart = (Button) findViewById(R.id.start_anim);
        final HeartLayout heart = (HeartLayout) findViewById(R.id.heart);
        sendHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.addHeart();
            }
        });
    }
}
