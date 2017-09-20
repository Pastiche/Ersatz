package com.example.android.ersatz.screens.profile.view;

import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.CommonView;

public interface ProfileView extends CommonView {

    interface ProfileViewListener {
        void onEditClick();
    }

    void bindProfile(Profile profile);

    void setListener(ProfileViewListener listener);

}
