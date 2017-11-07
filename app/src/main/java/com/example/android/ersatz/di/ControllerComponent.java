package com.example.android.ersatz.di;

import com.example.android.ersatz.screens.common.controllers.MainActivity;
import com.example.android.ersatz.di.modules.ControllerModule;
import com.example.android.ersatz.screens.auth.SigninActivity;
import com.example.android.ersatz.screens.auth.SignupActivity;
import com.example.android.ersatz.screens.contacts.controllers.ContactsFragment;
import com.example.android.ersatz.screens.contacts.controllers.ProfileDetailsActivity;
import com.example.android.ersatz.screens.edit.EditActivity;
import com.example.android.ersatz.screens.profile.MyProfileFragment;
import com.example.android.ersatz.screens.search.SearchFragment;

import dagger.Subcomponent;

@Subcomponent(modules = ControllerModule.class)
public interface ControllerComponent {

    void inject(SigninActivity signinActivity);

    void inject(SignupActivity signupActivity);

    void inject(MainActivity mainActivity);

    void inject(MyProfileFragment myProfileFragment);

    void inject(EditActivity editActivity);

    void inject(SearchFragment searchFragment);

    void inject(ContactsFragment contactsFragment);

    void inject(ProfileDetailsActivity profileDetailsActivity);

}
