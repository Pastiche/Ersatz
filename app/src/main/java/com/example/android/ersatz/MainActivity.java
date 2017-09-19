package com.example.android.ersatz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Denis on 19.09.2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = loadToken();

        if (token == null)
            startSigninActivity();

    }


    private String loadToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("authorization", MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    //------------Starting next Activities---------------//

    private void startSigninActivity() {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
        finish();
    }

}
