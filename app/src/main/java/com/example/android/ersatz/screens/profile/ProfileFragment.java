package com.example.android.ersatz.screens.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.network.ErsatzApp;
import com.example.android.ersatz.screens.profile.view.ProfileView;
import com.example.android.ersatz.screens.profile.view.ProfileViewImpl;

import java.util.ArrayList;
import java.util.List;

// TODO: ask, what the fuck is 2 ids??
// TODO: what to do if my token got wrecked?
// TODO: make menu part of the view (?)
// TODO: implements dagger 2

public class ProfileFragment extends Fragment implements
        ProfileView.ProfileViewListener,
        NetworkProfileManager.NetworkProfileManagerListener {

    private NetworkProfileManager mNetworkManager;
    private ProfileViewImpl mView;

    public ProfileFragment() {
    }

    //-------- lifecycle --------//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // initialization
        mNetworkManager = new NetworkProfileManager(this.getActivity());
        mView = new ProfileViewImpl(inflater, container);
        // connect controller and view
        mView.setListener(this);

        return mView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mNetworkManager.registerListener(this);
        mNetworkManager.fetchMyProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        mNetworkManager.unregisterListener(this);
    }

    //-------- view callbacks --------//

    @Override
    public void onEditClick() {

    }

    @Override
    public void onContactUrlClick(Contact contact) {

    }

    //-------- manager callbacks --------//

    @Override
    public void onProfileFetched(Profile profile) {
        mView.bindProfile(profile);
    }

    @Override
    public void onErrorOccurred(String message) {
        // TODO: probably, this should also be implemented on the View side:
        showMessage(message);
    }

    //-------- menu --------//

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.qr_button:
                showMessage("QR code showing...");
                return true;
            default:
                return this.getActivity().onOptionsItemSelected(item);
        }
    }

    //-------- helpers --------//

    private void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
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
