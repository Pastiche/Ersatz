package com.example.android.ersatz.screens.common;

import android.support.v4.app.Fragment;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.di.ControllerComponent;
import com.example.android.ersatz.di.modules.ControllerModule;

public class BaseFragment extends Fragment {

    protected ControllerComponent buildComponent() {
        return ((ErsatzApp) getActivity().getApplication())
                .getAppComponent()
                .newControllerComponent(new ControllerModule(getActivity()));
    }

}

