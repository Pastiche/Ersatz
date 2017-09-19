package com.example.android.ersatz.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

}