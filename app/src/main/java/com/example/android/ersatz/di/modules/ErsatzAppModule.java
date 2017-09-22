package com.example.android.ersatz.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.di.Scopes.ErsatzAppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ErsatzAppModule {

    private final ErsatzApp ersatzApp;

    public ErsatzAppModule(ErsatzApp ersatzApp) {
        this.ersatzApp = ersatzApp;
    }

    @Provides
    @ErsatzAppScope
    ErsatzApp ersatzApp() {
        return ersatzApp;
    }

    @Provides
    @ErsatzAppScope
    SharedPreferences sharedPreferences(ErsatzApp ersatzApp) {
        return ersatzApp.getSharedPreferences("authorization", Context.MODE_PRIVATE);
    }

    @Provides
    @ErsatzAppScope
    SharedPreferences.Editor preferencesEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

}
