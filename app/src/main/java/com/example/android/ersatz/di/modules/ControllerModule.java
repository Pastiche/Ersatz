package com.example.android.ersatz.di.modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.model.db.ProfileContract;
import com.example.android.ersatz.model.network.ItWeekService;
import com.example.android.ersatz.screens.contacts.adapters.ProfileAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import static com.example.android.ersatz.model.db.ProfileContract.*;

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

    @Provides
    List<Profile> profiles() {
        return new ArrayList<>();
    }

    @Provides
    ProfileAdapter profileAdapter(Context context, List<Profile> profiles) {
        return new ProfileAdapter(context, profiles);
    }

    @Provides
    String[] projection() {
        String[] projection = {
                ProfileEntry._ID,
                ProfileEntry.COLUMN_PAGE_URL,
                ProfileEntry.COLUMN_PAGE_ID};
        return projection;
    }

    @Provides
    CursorLoader cursorLoader(Context context, String[] projection) {
        return new CursorLoader(context, ProfileEntry.CONTENT_URI,
                projection, null, null, null);
    }


    // TODO: provide dialogs with different messages
}
