package com.example.android.ersatz.di;

import com.example.android.ersatz.MainActivity;
import com.example.android.ersatz.di.modules.ControllerModule;
import com.example.android.ersatz.screens.auth.SigninActivity;
import com.example.android.ersatz.screens.auth.SignupActivity;
import com.example.android.ersatz.screens.profile.ProfileFragment;

import dagger.Subcomponent;

@Subcomponent(modules = ControllerModule.class)
public interface ControllerComponent {

    void inject(SigninActivity signinActivity);

    void inject(SignupActivity signupActivity);

    void inject(MainActivity mainActivity);

    void inject(ProfileFragment profileFragment);

}
