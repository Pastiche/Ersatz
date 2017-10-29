package com.example.android.ersatz.di.modules;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;
import com.example.android.ersatz.model.network.ItWeekService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ItWeekServiceModule {

    @Provides
    @ErsatzAppScope
    public GsonConverterFactory gsonFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @ErsatzAppScope
    public Retrofit retrofit(GsonConverterFactory factory, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(factory)
                .client(client)
                .baseUrl("https://itweekandroiddemo.herokuapp.com/")
                .build();
    }

    @Provides
    @ErsatzAppScope
    public ItWeekService itWeekService(Retrofit retrofit) {
        return retrofit.create(ItWeekService.class);
    }

}
