package com.example.android.ersatz.di;

import com.example.android.ersatz.di.Scopes.SigninActivityScope;
import com.example.android.ersatz.di.modules.SigninActivityModule;
import com.example.android.ersatz.screens.auth.SigninActivity;

import dagger.Component;

@SigninActivityScope
@Component(modules = SigninActivityModule.class, dependencies = ErsatzAppComponent.class)
public interface SigninActivityComponent {

    void injectSigninActivity(SigninActivity signinActivity);

}
