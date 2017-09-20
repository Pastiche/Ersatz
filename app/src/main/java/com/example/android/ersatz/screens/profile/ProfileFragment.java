package com.example.android.ersatz.screens.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.screens.profile.view.ProfileView;
import com.example.android.ersatz.screens.profile.view.ProfileViewImpl;

import java.util.ArrayList;
import java.util.List;

// TODO: ask, what the fuck is 2 ids??
// TODO: what to do if my token got wrecked?
// TODO: make menu part of the view

public class ProfileFragment extends Fragment implements
        ProfileView.ProfileViewListener,
        NetworkProfileManager.NetworkProfileManagerListener {

    /**
     * This constant should be used as a key in a Bundle passed to this fragment as an argument
     * at creation time. This key should correspond to the ID of the particular profile
     * which details will be shown in this fragment
     */
    public static final String ARG_PROFILE_ID = "arg_profile_id";

    private NetworkProfileManager mNetworkManager;
    private ProfileViewImpl mView;

    private String mProfileId;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // actually we should get rid of this dependency using dagger 2
        mNetworkManager = new NetworkProfileManager(this.getContext());

        // Instantiate MVC view associated with this fragment
        mView = new ProfileViewImpl(inflater, container);
        mView.setListener(this);

        // Return the root view of the associated MVC view
        return mView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mNetworkManager.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mNetworkManager.unregisterListener(this);
    }

    @Override
    public void onShowQrCodeClick() {
        mNetworkManager.fetchMyProfile();
    }

    @Override
    public void onContactUrlClick(Contact contact) {

    }

    @Override
    public void onProfilesFetched(Profile profile) {
        mView.bindProfile(profile);
    }

    @Override
    public void onErrorOccured(String message) {

    }


    //<editor-fold desc="dummy profile">
    private Profile makeDummyProfile() {

        Contact contact1 = new Contact(Contact.CONTACT_TYPE_EMAIL, "ivashkovdv@gmail.com");
        Contact contact2 = new Contact(Contact.CONTACT_TYPE_VK, "lazzyrex");
        Contact contact3 = new Contact(Contact.CONTACT_TYPE_FB, "ivashkov.denis");
        Contact contact4 = new Contact(Contact.CONTACT_TYPE_GITHUB, "Pastiche");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        String firstName = "Denis";
        String middleName = "Viktorovich";
        String lastName = "Ivashkov";
        String pageUrl = "https://itweekandroiddemo.herokuapp.com/public/kj18coa1t2f6";
        String pageId = "kj18coa1t2f6";
        String userId = "59c04fe13354fe0011dac2c4";

        return new Profile(userId, firstName, lastName, middleName, contacts, pageId, pageUrl);
    }
    //</editor-fold>
}
