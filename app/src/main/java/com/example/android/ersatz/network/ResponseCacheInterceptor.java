package com.example.android.ersatz.network;

import java.io.IOException;
import okhttp3.Interceptor;

/**
 * Interceptor to cache data and maintain it for a minute.
 * <p>
 * If the same network request is sent within a minute,
 * the response is retrieved from cache.
 */
public class ResponseCacheInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60)
                .build();
    }
}