package com.example.android.ersatz.model.network;

import com.example.android.ersatz.entities.AuthBody;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.entities.TokenBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItWeekService {

    @Headers("Content-type: application/json")
    @POST("signup")
    Call<TokenBody> signUp(@Body AuthBody authBody,
                           @Header("ApplyOfflineCache") boolean offlineCache,
                           @Header("ApplyResponseCache") boolean responseCache);

    @Headers("Content-type: application/json")
    @POST("signin")
    Call<TokenBody> signIn(@Body AuthBody authBody,
                           @Header("ApplyOfflineCache") boolean offlineCache,
                           @Header("ApplyResponseCache") boolean responseCache);

    @GET("profile")
    Call<Profile> getMyProfile(@Header("Authorization") String token,
                               @Header("ApplyOfflineCache") boolean offlineCache,
                               @Header("ApplyResponseCache") boolean responseCache);

    @Headers("Content-type: application/json")
    @GET("profile/{page_id}")
    Call<Profile> getProfileById(@Path("page_id") String pageId,
                                 @Header("Authorization") String token,
                                 @Header("ApplyOfflineCache") boolean offlineCache,
                                 @Header("ApplyResponseCache") boolean responseCache);

    @Headers("Content-type: application/json")
    @POST("profile")
    Call<Profile> updateProfile(@Body Profile profile,
                                @Header("Authorization") String token,
                                @Header("ApplyOfflineCache") boolean offlineCache,
                                @Header("ApplyResponseCache") boolean responseCache);


    // TODO: handle out of boundaries exception
    // TODO: handle no results exception
    // TODO: get rid of headers duplication

}
