package com.example.android.ersatz.screens.common.controllers;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.di.ControllerComponent;
import com.example.android.ersatz.di.modules.ControllerModule;

public class BaseFragment extends Fragment {

    protected void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected ControllerComponent buildComponent() {
        return ((ErsatzApp) getActivity().getApplication())
                .getAppComponent()
                .newControllerComponent(new ControllerModule(getActivity()));
    }

}

