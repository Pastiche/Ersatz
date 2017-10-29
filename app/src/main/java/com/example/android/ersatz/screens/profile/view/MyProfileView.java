package com.example.android.ersatz.screens.profile.view;

import android.graphics.Bitmap;

import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.views.BaseView;

public interface MyProfileView extends BaseView {

    interface ProfileViewListener {
        void onEditClick();

        void onContactUrlClick(Contact contact);

        void onShowQrCodeBtnClick();
    }

    void bindProfile(Profile profile);

    void setListener(ProfileViewListener listener);

    void showQrCode(Bitmap qrCodeImage);

}
