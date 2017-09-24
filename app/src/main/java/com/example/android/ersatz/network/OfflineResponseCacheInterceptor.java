package com.example.android.ersatz.network;

import android.net.ConnectivityManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Interceptor to cache data and maintain it for four weeks.
 * <p>
 * If the device is offline, stale (at most four weeks old)
 * response is fetched from the cache.
 */
public class OfflineResponseCacheInterceptor implements Interceptor {

    private ConnectivityManager mConnectivityManager;

    public OfflineResponseCacheInterceptor(ConnectivityManager connectivityManager) {
        mConnectivityManager = connectivityManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (Boolean.valueOf(request.header("ApplyOfflineCache"))) {
            Timber.i("Offline cache applied (okhttp)");
            if (!isNetworkConnected()) {
                request = request.newBuilder()
                        .removeHeader("ApplyOfflineCache")
                        .header("Cache-Control",
                                "public, only-if-cached," +
                                        "max-stale=" + 2419200)
                        .build();
            }
        } else
            Timber.i("Offline cache not applied (okhttp)");
        return chain.proceed(request);
    }


    private boolean isNetworkConnected() {
        return mConnectivityManager.getActiveNetworkInfo() != null;
    }
}