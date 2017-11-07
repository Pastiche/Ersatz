package com.example.android.ersatz.screens.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.example.android.ersatz.screens.profile.view.MyProfileView;
import com.example.android.ersatz.screens.profile.view.MyProfileViewImpl;

import javax.inject.Inject;

import static com.example.android.ersatz.utils.QrUtils.makeBitmapQrCodeFromUrl;

public class MyProfileFragment extends BaseFragment implements
        MyProfileView.ProfileViewListener,
        NetworkProfileManager.NetworkProfileManagerListener {

    @Inject
    ErsatzApp ersatzApp;

    @Inject
    NetworkProfileManager mNetworkManager;

    Profile mProfile;

    private MyProfileView mView;

    public MyProfileFragment() {
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

        mView = new MyProfileViewImpl(inflater, container, this.getContext());
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

    @Override
    public void onEditClick() {
        if (ersatzApp.isNetworkConnected())
            startEditActivity();
    }

    @Override
    public void onContactUrlClick(Contact contact) {

        if (contact.getType().equals(Contact.CONTACT_TYPE_SKYPE))
            showMessage("Skype intent not developed yet...");

        else if (contact.getType().equals(Contact.CONTACT_TYPE_EMAIL))
            startSendEmailIntent(contact.getUrl());

        else startOpenInBrowserIntent(contact);
    }

    private void startOpenInBrowserIntent(Contact contact) {
        String path = contact.getUrl();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contact.getEndpoint(contact.getType()) + path));
        startActivity(browserIntent);
    }

    private void startSendEmailIntent(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    public void onShowQrCodeBtnClick() {
        Bitmap qrCodeImage = makeBitmapQrCodeFromUrl(mProfile.getPageId());
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
