package com.example.android.ersatz.screens.profile.view;

import android.os.Bundle;
import android.view.View;

import com.example.android.ersatz.entities.Profile;

/**
 * Created by Denis on 19.09.2017.
 */

public class ProfileViewImpl implements ProfileView {

    private View mRootView;
    private ProfileViewListener mListener;

    @Override
    public View getRootView() {
        return null;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void bindProfile(Profile profile) {

    }

    @Override
    public void setListener(ProfileViewListener listener) {

    }
}
