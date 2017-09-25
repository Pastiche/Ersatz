package com.example.android.ersatz.screens.search.view;

import android.graphics.Bitmap;

import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.views.BaseView;

public interface SearchMvcView extends BaseView {

    interface SearchViewListener {
        void onAddClick();

        void onContactUrlClick(Contact contact);

        void onShowQrCodeBtnClick();
    }

    void bindProfile(Profile profile);

    void setListener(SearchViewListener listener);

    void showQrCode(Bitmap qrCodeImage);

}
