package com.example.android.ersatz.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts = null;
    @SerializedName("page_id")
    @Expose
    private String pageId;
    @SerializedName("page_url")
    @Expose
    private String pageUrl;

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public String getPageId() {
        return pageId;
    }

    public String getPageUrl() {
        return pageUrl;
    }


}