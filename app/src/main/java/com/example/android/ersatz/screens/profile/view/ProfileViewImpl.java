package com.example.android.ersatz.screens.profile.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;

/**
 * Created by Denis on 19.09.2017.
 */

// TODO: should I use internal chash, or it's ok to load from db every time I rotate the phone
// TODO: make initial visiblity of wrappers = GONE, and reveal them only if we have data

public class ProfileViewImpl implements ProfileView {

    private View mRootView;

    private ProfileViewListener mListener;

    private FloatingActionButton _fab;
    private TextView _firstName;
    private TextView _middleName;
    private TextView _lastName;
    private TextView _email;
    private TextView _skype;
    private TextView _github;
    private TextView _vk;
    private TextView _fb;
    private TextView _twitter;
    private TextView _instagram;
    private TextView _linkedin;


    public ProfileViewImpl(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.profile_view, container, false);

        initialize();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        _fab.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onEditClick();
            }
        });
    }

    private void initialize() {
        _fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        _firstName = (TextView) mRootView.findViewById(R.id.first_name);
        _middleName = (TextView) mRootView.findViewById(R.id.middle_name);
        _lastName = (TextView) mRootView.findViewById(R.id.last_name);
        _email = (TextView) mRootView.findViewById(R.id.email);
        _skype = (TextView) mRootView.findViewById(R.id.skype);
        _github = (TextView) mRootView.findViewById(R.id.github);
        _vk = (TextView) mRootView.findViewById(R.id.vk);
        _fb = (TextView) mRootView.findViewById(R.id.fb);
        _twitter = (TextView) mRootView.findViewById(R.id.twitter);
        _instagram = (TextView) mRootView.findViewById(R.id.instagram);
        _linkedin = (TextView) mRootView.findViewById(R.id.linkedin);
    }

    //--------Binding profile--------//

    @Override
    public void bindProfile(Profile profile) {

        _firstName.setText(profile.getFirstName());
        _lastName.setText(profile.getLastName());
        _middleName.setText(profile.getMiddleName());

        bindContactToView(profile.getFb(), _fb);
        bindContactToView(profile.getVk(), _vk);
        bindContactToView(profile.getEmail(), _email);
        bindContactToView(profile.getSkype(), _skype);
        bindContactToView(profile.getGithub(), _github);
        bindContactToView(profile.getTwitter(), _twitter);
        bindContactToView(profile.getLinkedin(), _linkedin);
        bindContactToView(profile.getInstagram(), _instagram);

    }

    private void bindContactToView(Contact contact, TextView view) {
        if (validateContact(contact)) {
            view.setText(contact.getUrl());
            setOnContactUrlClickListener(contact, view);
        } else
            hideContainer(view);
    }

    private boolean validateContact(Contact contact) {
        return contact != null
                && contact.getUrl() != null
                && !contact.getUrl().isEmpty();
    }

    private void setOnContactUrlClickListener(Contact contact, TextView view) {
        view.setOnClickListener(view1 -> {
            if (mListener != null) {
                mListener.onContactUrlClick(contact);
            }
        });
    }

    private void hideContainer(TextView view) {
        ((View) view.getParent()).setVisibility(View.GONE);
    }

    //--------Helpers--------//

    @Override
    public void setListener(ProfileViewListener listener) {
        mListener = listener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

}
