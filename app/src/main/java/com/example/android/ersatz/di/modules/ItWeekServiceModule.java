package com.example.android.ersatz.di.modules;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;
import com.example.android.ersatz.network.ItWeekService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ItWeekServiceModule {

    @Provides
    @ErsatzAppScope
    public String baseUrl() {
        // TODO: probably should extract this...
        return "https://itweekandroiddemo.herokuapp.com/";
    }

    @Provides
    @ErsatzAppScope
    public GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @ErsatzAppScope
    public Retrofit retrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient, String baseUrl) {

        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @ErsatzAppScope
    public ItWeekService itWeekService(Retrofit retrofit) {
        return retrofit.create(ItWeekService.class);
    }

}
