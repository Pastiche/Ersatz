package com.example.android.ersatz.screens.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.screens.common.controllers.BaseFragment;
import com.example.android.ersatz.screens.edit.EditActivity;
import com.example.android.ersatz.screens.profile.view.ProfileView;
import com.example.android.ersatz.screens.profile.view.ProfileViewImpl;

import static com.example.android.ersatz.utils.QrUtils.*;

import javax.inject.Inject;

// TODO: make menu part of the view (?)
// TODO: implement dagger 2 deeper

public class ProfileFragment extends BaseFragment implements
        ProfileView.ProfileViewListener,
        NetworkProfileManager.NetworkProfileManagerListener {

    @Inject
    NetworkProfileManager mNetworkManager;
    @Inject
    ErsatzApp ersatzApp;

    Profile mProfile;

    private ProfileViewImpl mView;

    public ProfileFragment() {
    }

    //-------- lifecycle --------//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = new ProfileViewImpl(inflater, container, this);
        mView.setListener(this);
        return mView.getRootView();
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

    //-------- view callbacks --------//

    // TODO: this method should handle editing, not menu item
    @Override
    public void onEditClick() {
        startEditActivity();
    }

    @Override
    public void onContactUrlClick(Contact contact) {

    }

    @Override
    public void onShowQrCodeBtnClick() {
        Bitmap qrCodeImage = makeBitmapQrCodeFromUrl(mProfile.getPageUrl());
        mView.showQrCode(qrCodeImage);
    }

    //-------- manager callbacks --------//

    @Override
    public void onProfileFetched(Profile profile) {
        mProfile = profile;
        mView.bindProfile(mProfile);
    }

    @Override
    public void onErrorOccurred(String message) {
        showMessage(message);
    }

    private void startEditActivity() {
        Intent intent = new Intent(getContext(), EditActivity.class);
        startActivity(intent);
    }
}
