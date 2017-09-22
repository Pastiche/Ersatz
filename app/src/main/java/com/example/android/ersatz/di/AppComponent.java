package com.example.android.ersatz.di;

import android.content.SharedPreferences;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;
import com.example.android.ersatz.di.modules.ItWeekServiceModule;
import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.di.modules.ControllerModule;
import com.example.android.ersatz.network.ItWeekService;

import dagger.Component;

@ErsatzAppScope
@Component(modules = ItWeekServiceModule.class)
public interface AppComponent {

    void inject(ErsatzApp ersatzApp);

    ErsatzApp ersatzApp();

    ItWeekService getItWeekService();

    SharedPreferences sharedPreferences();

    ControllerComponent newControllerComponent(ControllerModule controllerModule);

}
