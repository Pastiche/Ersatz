package com.example.android.ersatz.di.modules;

import android.content.Context;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ErsatzAppScope
    public Context context() {
        return context;
    }

}
