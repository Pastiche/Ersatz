package com.example.android.ersatz.network;

import android.app.Activity;
import android.app.Application;

import com.example.android.ersatz.di.DaggerErsatzAppComponent;
import com.example.android.ersatz.di.ErsatzAppComponent;
import com.example.android.ersatz.di.modules.ContextModule;

import javax.inject.Inject;

public class ErsatzApp extends Application {

    @Inject
    ItWeekService itWeekService;
    private ErsatzAppComponent ersatzAppComponent;

    public static ErsatzApp get(Activity activity) {

        System.out.println("activity == null " + (activity.getApplication() == null));
        return (ErsatzApp) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        buildComponent();
        ersatzAppComponent.injectErsatzApp(this);
        System.out.println("Service == null " + (itWeekService == null));
    }

    public void buildComponent() {
        ersatzAppComponent = DaggerErsatzAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ErsatzAppComponent getComponent() {
        return ersatzAppComponent;
    }

    public ItWeekService getItWeekService() {
        System.out.println("Service in getter == null " + (itWeekService == null));
        return itWeekService;
    }

}
