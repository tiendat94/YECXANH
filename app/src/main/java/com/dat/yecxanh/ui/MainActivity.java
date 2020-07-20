package com.dat.yecxanh.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dat.yecxanh.R;
import com.dat.yecxanh.base.BaseActivity;
import com.dat.yecxanh.fragment.FragmentTest;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout menu;
    private ImageView menuImageview;
    private DrawerLayout drawer;

    @Override
    protected String getTitleScreen() {
        return null;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_drawer_main;
    }

    @Override
    protected void initView() {
        menu = (LinearLayout) findViewById(R.id.menu);
        menuImageview = (ImageView) findViewById(R.id.menuImageview);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_right_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    @Override
    protected void afteriInitView() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initListener() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(R.id.root_layout, new FragmentTest(), "");
            }
        });
        menuImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START, false);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
