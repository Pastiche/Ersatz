package com.example.android.ersatz.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenBody {

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("error")
    @Expose
    String error;

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
