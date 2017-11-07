package com.example.android.ersatz.screens.edit;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.screens.common.controllers.BaseActivity;
import com.example.android.ersatz.screens.edit.view.EditView;
import com.example.android.ersatz.screens.edit.view.EditViewImpl;

import javax.inject.Inject;

public class EditActivity extends BaseActivity implements
        EditView.EditViewListener, NetworkProfileManager.NetworkProfileManagerListener {

    @Inject
    NetworkProfileManager mNetworkManager;

    private EditView mView;
    Profile mProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);

        mView = new EditViewImpl(this, null);
        mView.setListener(this);
        setContentView(mView.getRootView());

    }

    @Override
    public void onStart() {
        super.onStart();
        mNetworkManager.registerListener(this);
        mNetworkManager.fetchMyProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        mNetworkManager.unregisterListener(this);
    }

    @Override
    public void onSaveClick(Profile profileToUpdate) {
        mNetworkManager.updateMyProfile(profileToUpdate);
        showMessage("Saving...");
        new Handler().postDelayed(this::finish, 1000);
        showMessage("Saved");

    }

    @Override
    public void onProfileFetched(Profile profile) {
        mProfile = profile;
        mView.bindProfile(mProfile);
    }

    @Override
    public void onErrorOccurred(String message) {
        showMessage(message);
    }
}
