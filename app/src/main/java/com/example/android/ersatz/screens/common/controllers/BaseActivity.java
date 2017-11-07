package com.example.android.ersatz.screens.common.controllers;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.di.ControllerComponent;
import com.example.android.ersatz.di.modules.ControllerModule;

import java.io.File;

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

    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void deleteCache() {
        try {
            File dir = this.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
