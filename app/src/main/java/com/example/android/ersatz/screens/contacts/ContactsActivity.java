package com.example.android.ersatz.screens.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.ersatz.R;

/**
 * Created by Denis on 19.09.2017.
 */

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
/*        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ContactsFragment())
                .commit();*/
    }

}
