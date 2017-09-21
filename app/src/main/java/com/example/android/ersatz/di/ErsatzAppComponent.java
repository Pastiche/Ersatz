package com.example.android.ersatz.di;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;
import com.example.android.ersatz.di.modules.ItWeekServiceModule;
import com.example.android.ersatz.network.ErsatzApp;
import com.example.android.ersatz.network.ItWeekService;

import dagger.Component;

@ErsatzAppScope
@Component(modules = ItWeekServiceModule.class)
public interface ErsatzAppComponent {

    void injectErsatzApp(ErsatzApp ersatzApp);

    ItWeekService getItWeekService();

}
