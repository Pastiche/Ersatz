package com.example.android.ersatz.di.modules;

import com.example.android.ersatz.ErsatzApp;

import android.net.ConnectivityManager;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = ErsatzAppModule.class)
public class NetworkModule {

    @Provides
    @ErsatzAppScope
    public ConnectivityManager connectivityManager(ErsatzApp ersatzApp) {
        return (ConnectivityManager) ersatzApp.getSystemService(ErsatzApp.CONNECTIVITY_SERVICE);
    }

    @Provides
    @ErsatzAppScope
    public File file(ErsatzApp ersatzApp) {
        return new File(ersatzApp.getCacheDir(), "okHttp_cache");
    }

    @Provides
    @ErsatzAppScope
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024); // 10Mb cache
    }

    @Provides
    @ErsatzAppScope
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @ErsatzAppScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor logger, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .cache(cache)
                .build();
    }

}
