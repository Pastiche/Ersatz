package com.example.android.ersatz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.ersatz.screens.auth.SigninActivity;
import com.example.android.ersatz.navigation.SimpleFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Denis on 19.09.2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final int TWO_BACK_PRESSES_INTERVAL = 2000; // # milliseconds
    private long mLastBackPressTime;

    SimpleFragmentPagerAdapter mAdapter;

    @Bind(R.id.viewpager)
    ViewPager _viewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout _tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String token = loadToken();

        if (token == null)
            startSigninActivity();
        else {
            setTabSwipeNavigation();
        }
    }

    private String loadToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("authorization", MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
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

    //------------UI profile---------------//
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
