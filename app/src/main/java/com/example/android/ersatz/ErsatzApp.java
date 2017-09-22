package com.example.android.ersatz;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.example.android.ersatz.di.AppComponent;
import com.example.android.ersatz.di.DaggerAppComponent;
import com.example.android.ersatz.di.modules.ErsatzAppModule;
import com.example.android.ersatz.network.ItWeekService;

import javax.inject.Inject;

public class ErsatzApp extends Application {

    @Inject
    ItWeekService itWeekService;
    @Inject
    ConnectivityManager connectivityManager;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor preferencesEditor;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        getAppComponent().inject(this);
        super.onCreate();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .ersatzAppModule(new ErsatzAppModule(this))
                    .build();
        }
        return appComponent;
    }

    //-------- utils --------//

    public boolean isNetworkConnected() {
        return connectivityManager.getActiveNetworkInfo() != null;
    }

}
