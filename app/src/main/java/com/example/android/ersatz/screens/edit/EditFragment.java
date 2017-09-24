/*
package com.example.android.ersatz.screens.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.screens.common.controllers.BaseFragment;
import com.example.android.ersatz.screens.edit.view.EditView;
import com.example.android.ersatz.screens.edit.view.EditViewImpl;

import javax.inject.Inject;

// TODO: implement dagger 2 deeper

public class EditFragment extends BaseFragment implements
        EditView.EditViewListener {

    @Inject
    NetworkProfileManager mNetworkManager;
    @Inject
    ErsatzApp ersatzApp;

    Profile mProfile;

    private EditViewImpl mView;

    public EditFragment() {
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

        mView = new EditViewImpl(inflater, container, this);
        mView.setListener(this);
        return mView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveClick() {

    }
}
*/
