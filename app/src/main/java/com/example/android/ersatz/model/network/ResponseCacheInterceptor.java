package com.example.android.ersatz.model.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Interceptor to cache data and maintain it for a minute.
 * <p>
 * If the same network request is sent within a minute,
 * the response is retrieved from cache.
 */
public class ResponseCacheInterceptor implements Interceptor {

    public ResponseCacheInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (Boolean.valueOf(request.header("ApplyResponseCache"))) {
            Timber.i("Response cache applied");
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader("ApplyResponseCache")
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        } else {
            Timber.i("Response cache not applied (okhttp)");
            return chain.proceed(chain.request());
        }
    }
}