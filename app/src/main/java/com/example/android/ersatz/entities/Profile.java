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
    private List<Contact> contacts;
    @SerializedName("page_id")
    @Expose
    private String pageId;
    @SerializedName("page_url")
    @Expose
    private String pageUrl;

    //<editor-fold desc="regular getters setters">
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
    //</editor-fold>


    //-------- getting specific contact url--------//

    private Contact getSpecificContact(String contactType) {

        if (contacts == null)
            return null;

        for (Contact contact : contacts) {
            if (contact.getType().equalsIgnoreCase(contactType))
                return contact;
        }

        return null;

    }

    public Contact getVk() {
        return getSpecificContact(Contact.CONTACT_TYPE_VK);
    }

    public Contact getFb() {
        return getSpecificContact(Contact.CONTACT_TYPE_FB);
    }

    public Contact getTwitter() {
        return getSpecificContact(Contact.CONTACT_TYPE_TWITTER);
    }

    public Contact getSkype() {
        return getSpecificContact(Contact.CONTACT_TYPE_SKYPE);
    }

    public Contact getGithub() {
        return getSpecificContact(Contact.CONTACT_TYPE_GITHUB);
    }

    public Contact getInstagram() {
        return getSpecificContact(Contact.CONTACT_TYPE_INSTAGRAM);
    }

    public Contact getLinkedin() {
        return getSpecificContact(Contact.CONTACT_TYPE_LINKEDIN);
    }

    public Contact getEmail() {
        return getSpecificContact(Contact.CONTACT_TYPE_EMAIL);
    }

    public Profile(String firstName, String lastName, String middleName, List<Contact> contacts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", contacts=" + contacts +
                ", pageId='" + pageId + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                '}';
    }
}