package com.example.android.ersatz.network;

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
    Call<TokenBody> signUp(@Body AuthBody authBody);

    @Headers("Content-type: application/json")
    @POST("signin")
    Call<TokenBody> signIn(@Body AuthBody authBody);

    @GET("profile")
    Call<Profile> getMyProfile(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("public/{page_id}")
    Call<Profile> getProfileById(@Path("page_id") String pageId,
                                 @Header("Authorisation") String token);


    // TODO: handle out of boundaries exception
    // TODO: handle no results exception
    // TODO: handle "such name exists exception"

}
