package com.dat.yecxanh;

import android.view.View;
import android.widget.LinearLayout;

import com.dat.yecxanh.base.BaseActivity;
import com.dat.yecxanh.fragment.FragmentTest;

public class MainActivity extends BaseActivity {
    private LinearLayout menu;

    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        menu = (LinearLayout) findViewById(R.id.menu);
    }

    @Override
    protected void bindData() {

    }

    @Override
    protected void afteriInitView() {
        getSupportActionBar().hide();
    }

    @Override
    protected void initListener() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(R.id.root_layout, new FragmentTest(), "");
            }
        });
    }

}
