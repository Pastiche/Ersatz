package com.example.android.ersatz.screens.contacts.controllers;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.model.NetworkProfileManager.NetworkProfileManagerListener;
import com.example.android.ersatz.screens.common.controllers.BaseActivity;
import com.example.android.ersatz.screens.contacts.view.ProfileDetailsView;
import com.example.android.ersatz.screens.contacts.view.ProfileDetailsViewImpl;
import com.example.android.ersatz.screens.profile.view.MyProfileView.ProfileViewListener;
import com.example.android.ersatz.screens.profile.view.MyProfileViewImpl;

import javax.inject.Inject;

import static com.example.android.ersatz.screens.contacts.view.ProfileDetailsView.*;
import static com.example.android.ersatz.utils.QrUtils.makeBitmapQrCodeFromUrl;

public class ProfileDetailsActivity extends BaseActivity implements ProfileDetailsListener,
        NetworkProfileManagerListener {

    @Inject
    NetworkProfileManager mNetworkManager;

    Profile mProfile;

    private ProfileDetailsView mView;

    public ProfileDetailsActivity() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);
        mView = new ProfileDetailsViewImpl(getLayoutInflater(), null, this);
        setContentView(mView.getRootView());
        mView.setListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String pageId = intent.getStringExtra("page_id");
        if (pageId == null) {
            finish();
            showMessage("Failed to read page id");
        } else {
            System.out.println("okhttp: " + pageId);
            mNetworkManager.registerListener(this);
            mNetworkManager.fetchProfileById(pageId);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        mNetworkManager.unregisterListener(this);
    }

    @Override
    public void onContactUrlClick(Contact contact) {

    }

    @Override
    public void onShowQrCodeBtnClick() {
        Bitmap qrCodeImage = makeBitmapQrCodeFromUrl(mProfile.getPageId());
        mView.showQrCode(qrCodeImage);
    }

    @Override
    public void onProfileFetched(Profile profile) {
        mProfile = profile;
        System.out.println("Okhttp: " + profile);
        mView.bindProfile(mProfile);
    }

    @Override
    public void onErrorOccurred(String message) {
        showMessage(message);
    }
}
