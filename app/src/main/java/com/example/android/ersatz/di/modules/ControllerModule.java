package com.example.android.ersatz.di.modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.R;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.network.ItWeekService;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    private final FragmentActivity mActivity;

    public ControllerModule(FragmentActivity fragmentActivity) {
        mActivity = fragmentActivity;
    }

    @Provides
    FragmentActivity activity() {
        return mActivity;
    }

    @Provides
    Context context() {
        return mActivity;
    }

    @Provides
    String progressMessage() {
        return mActivity.getString(R.string.signin_progress_message);
    }

    @Provides
    ProgressDialog progressDialog(String progressMessage) {
        ProgressDialog progressDialog = new ProgressDialog(mActivity, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(progressMessage);
        return progressDialog;
    }

    @Provides
    NetworkProfileManager networkProfileManager(ItWeekService itWeekService,
                                                SharedPreferences sharedPreferences,
                                                FragmentActivity fragmentActivity) {
        return new NetworkProfileManager(itWeekService, sharedPreferences, fragmentActivity);
    }

    // TODO: provide with different messages
}
