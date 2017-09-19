package com.example.android.ersatz.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Denis on 12.09.2017.
 */

public class ItWeekApi {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logger);
//            okHttpClientBuilder.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//
//                    Request request = chain.request();
//                    Request.Builder newRequest = request.newBuilder().header("Authorisation", "secret-key");
//                    chain.proceed(newRequest.build());
//                    return null;
//                }
//            });

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .baseUrl("https://itweekandroiddemo.herokuapp.com/")
                    .build();
        }
        return retrofit;
    }


}
