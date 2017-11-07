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

    public static final String VK_ENDPOINT = "http://www.vk.com/";
    public static final String FB_ENDPOINT = "https://www.facebook.com/";
    public static final String TWITTER_ENDPOINT = "https://twitter.com/";
    public static final String GITHUB_ENDPOINT = "https://github.com/";
    public static final String INSTAGRAM_ENDPOINT = "https://www.instagram.com/";
    public static final String LINKEDIN_ENDPOINT = "https://www.linkedin.com/in/";

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    private String endPoint;

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

    public String getEndpoint(String type) {
        switch (type) {
            case CONTACT_TYPE_VK:
                return VK_ENDPOINT;
            case CONTACT_TYPE_FB:
                return FB_ENDPOINT;
            case CONTACT_TYPE_TWITTER:
                return TWITTER_ENDPOINT;
            case CONTACT_TYPE_GITHUB:
                return GITHUB_ENDPOINT;
            case CONTACT_TYPE_INSTAGRAM:
                return INSTAGRAM_ENDPOINT;
            case CONTACT_TYPE_LINKEDIN:
                return LINKEDIN_ENDPOINT;
            case CONTACT_TYPE_SKYPE:
            case CONTACT_TYPE_EMAIL:
            default:
                return null;

        }
    }
}