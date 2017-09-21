package com.example.android.ersatz.di.modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.android.ersatz.R;
import com.example.android.ersatz.di.Scopes.SigninActivityScope;
import com.example.android.ersatz.screens.auth.SigninActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class SigninActivityModule {

    private final SigninActivity signinActivity;

    public SigninActivityModule(SigninActivity signinActivity) {
        this.signinActivity = signinActivity;
    }

    @Provides
    @SigninActivityScope
    public String progressMessage() {
        return signinActivity.getString(R.string.signin_progress_message);
    }

    @Provides
    @SigninActivityScope
    public int appTheme() {
        return R.style.AppTheme_Dark_Dialog;
    }

    @Provides
    @SigninActivityScope
    public ProgressDialog progressDialog(String progressMessage, int appTheme) {
        ProgressDialog progressDialog = new ProgressDialog(signinActivity, appTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(progressMessage);
        return progressDialog;
    }

    @Provides
    @SigninActivityScope
    public ConnectivityManager connectivityManager() {
        return (ConnectivityManager) signinActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
