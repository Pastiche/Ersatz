package com.example.android.ersatz.di.modules;

import com.example.android.ersatz.ErsatzApp;

import android.net.ConnectivityManager;

import com.example.android.ersatz.di.Scopes.ErsatzAppScope;
import com.example.android.ersatz.model.network.OfflineResponseCacheInterceptor;
import com.example.android.ersatz.model.network.ResponseCacheInterceptor;

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
    ConnectivityManager connectivityManager(ErsatzApp ersatzApp) {
        return (ConnectivityManager) ersatzApp.getSystemService(ErsatzApp.CONNECTIVITY_SERVICE);
    }

    @Provides
    @ErsatzAppScope
    OfflineResponseCacheInterceptor offlineResponseCacheInterceptor(ConnectivityManager connectivityManager) {
        return new OfflineResponseCacheInterceptor(connectivityManager);
    }

    @Provides
    @ErsatzAppScope
    File file(ErsatzApp ersatzApp) {
        return new File(ersatzApp.getCacheDir(), "okHttp_cache");
    }

    @Provides
    @ErsatzAppScope
    Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024); // 10Mb cache
    }

    @Provides
    @ErsatzAppScope
    HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @ErsatzAppScope
    ResponseCacheInterceptor responseCacheInterceptor() {
        return new ResponseCacheInterceptor();
    }


    @Provides
    @ErsatzAppScope
    OkHttpClient okHttpClient(ResponseCacheInterceptor responseCacheInterceptor,
                              OfflineResponseCacheInterceptor offlineResponseCacheInterceptor,
                              HttpLoggingInterceptor logger, Cache cache) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(responseCacheInterceptor)
                .addInterceptor(offlineResponseCacheInterceptor)
                .addInterceptor(logger)
                .cache(cache)
                .build();
    }

}
