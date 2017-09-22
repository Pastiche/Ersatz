package com.example.android.ersatz.screens.common;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.di.ControllerComponent;
import com.example.android.ersatz.di.modules.ControllerModule;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    protected static final int TWO_BACK_PRESSES_INTERVAL = 2000; // # milliseconds

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor preferencesEditor;

    protected ControllerComponent buildComponent() {
        return ((ErsatzApp) getApplication())
                .getAppComponent()
                .newControllerComponent(new ControllerModule(this));
    }

    protected void storeToken(String token) {
        preferencesEditor.putString("token", token);
        preferencesEditor.apply();
    }

    protected String loadToken() {
        return sharedPreferences.getString("token", null);
    }

    protected void clearPreferences() {
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

}
