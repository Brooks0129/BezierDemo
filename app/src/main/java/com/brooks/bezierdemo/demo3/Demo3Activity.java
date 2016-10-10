
package com.brooks.bezierdemo.demo3;

import java.util.List;

import com.brooks.bezierdemo.R;
import com.google.common.collect.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class Demo3Activity extends AppCompatActivity {

    ScrollerViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);


        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);

    }

    private List<String> getTitles() {
        return Lists.newArrayList("1", "2", "3", "4");
    }

    private List<Integer> getBgRes() {
        return Lists.newArrayList(
                R.drawable.idp
                , R.drawable.idq
                , R.drawable.idr
                , R.drawable.ids);
    }

}
