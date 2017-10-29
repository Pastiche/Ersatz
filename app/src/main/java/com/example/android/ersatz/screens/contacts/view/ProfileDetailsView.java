package com.example.android.ersatz.screens.contacts.view;

import android.graphics.Bitmap;

import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.views.BaseView;

public interface ProfileDetailsView extends BaseView {

    interface ProfileDetailsListener {

        void onContactUrlClick(Contact contact);

        void onShowQrCodeBtnClick();
    }

    void bindProfile(Profile profile);

    void setListener(ProfileDetailsListener listener);

    void showQrCode(Bitmap qrCodeImage);

}
