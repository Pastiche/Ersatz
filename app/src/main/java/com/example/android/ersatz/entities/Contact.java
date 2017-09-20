package com.example.android.ersatz.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    public static final String CONTACT_TYPE_VK = "vk";
    public static final String CONTACT_TYPE_FB = "fb";
    public static final String CONTACT_TYPE_TWITTER = "twitter";
    public static final String CONTACT_TYPE_SKYPE = "skype";
    public static final String CONTACT_TYPE_GITHUB = "github";
    public static final String CONTACT_TYPE_INSTAGRAM = "instagram";
    public static final String CONTACT_TYPE_LINKEDIN = "linkedin";
    public static final String CONTACT_TYPE_EMAIL = "email";

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

    public Contact(String type, String url) {
        this.type = type;
        this.url = url;
    }
}