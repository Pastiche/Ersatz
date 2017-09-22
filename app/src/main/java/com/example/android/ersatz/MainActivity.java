package com.example.android.ersatz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.ersatz.screens.auth.SigninActivity;
import com.example.android.ersatz.navigation.SimpleFragmentPagerAdapter;
import com.example.android.ersatz.screens.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

// TODO: configure animation of the signin-signout transitions - it is slow

public class MainActivity extends BaseActivity {

    private long mLastBackPressTime;

    private SharedPreferences sharedPreferences;

    SimpleFragmentPagerAdapter mAdapter;

    @Bind(R.id.viewpager)
    ViewPager _viewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout _tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String token = loadToken();

        if (token == null)
            startSigninActivity();
        else {
            setTabSwipeNavigation();
            setToolBar();
        }
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

        // set searchView this goes to fragment menu
/*        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);*/

/*        // if no results on the screen - focus on search view
        if (adapter.isEmpty())
            searchView.setIconified(false);*/

        // handle search queries
/*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                switch (_viewPager.getCurrentItem()) {
                    case 1:
                        Toast.makeText(FragmentActivity.this, "TOASTY CONTACTS!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(FragmentActivity.this, "TOASTY SEARCH!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout_button:
                clearPreferences();
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
