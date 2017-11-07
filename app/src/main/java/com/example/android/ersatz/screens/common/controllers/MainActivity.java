package com.example.android.ersatz.screens.common.controllers;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.ersatz.R;
import com.example.android.ersatz.navigation.SimpleFragmentPagerAdapter;
import com.example.android.ersatz.screens.auth.SigninActivity;
import com.example.android.ersatz.screens.common.views.BaseViewImpl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private long mLastBackPressTime;
    private SimpleFragmentPagerAdapter mAdapter;
    private BaseViewImpl mView;

    @Bind(R.id.viewpager)
    ViewPager _viewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout _tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContent();
        ButterKnife.bind(this);


        String token = loadToken();
        if (token == null)
            startSigninActivity();
        else {
            setTabSwipeNavigation();
            setToolBar();
        }
    }

    private void setContent() {
        mView = new BaseViewImpl(this, null);
        setContentView(mView.getRootView());
    }

    private void startSigninActivity() {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
        finish();
    }

    private void setTabSwipeNavigation() {
        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        _viewPager.setAdapter(mAdapter);
        _tabLayout.setupWithViewPager(_viewPager);
    }

    private void setToolBar() {

        Toolbar toolBar = (Toolbar) findViewById(R.id.tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem signOutItem = menu.findItem(R.id.signout_button);
        signOutItem.setTitle("Sign Out");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout_button:
                clearPreferences();
                deleteCache();
                startSigninActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if (mLastBackPressTime + TWO_BACK_PRESSES_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mLastBackPressTime = System.currentTimeMillis();
    }

}
