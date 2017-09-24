package com.example.android.ersatz.di;

import com.example.android.ersatz.screens.common.controllers.MainActivity;
import com.example.android.ersatz.di.modules.ControllerModule;
import com.example.android.ersatz.screens.auth.SigninActivity;
import com.example.android.ersatz.screens.auth.SignupActivity;
import com.example.android.ersatz.screens.edit.EditActivity;
import com.example.android.ersatz.screens.profile.ProfileFragment;

import dagger.Subcomponent;

@Subcomponent(modules = ControllerModule.class)
public interface ControllerComponent {

    void inject(SigninActivity signinActivity);

    void inject(SignupActivity signupActivity);

    void inject(MainActivity mainActivity);

    void inject(ProfileFragment profileFragment);

    void inject(EditActivity editActivity);

}
