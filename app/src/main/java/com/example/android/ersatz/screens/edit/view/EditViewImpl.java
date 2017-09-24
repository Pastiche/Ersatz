package com.example.android.ersatz.screens.edit.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.controllers.BaseActivity;
import com.example.android.ersatz.screens.common.controllers.BaseFragment;

public class EditViewImpl implements EditView {
    private View mRootView;
    private BaseActivity mActivity;
    private EditViewListener mListener;

    private EditText _firstName;
    private EditText _middleName;
    private EditText _lastName;
    private EditText _email;
    private EditText _skype;
    private EditText _github;
    private EditText _vk;
    private EditText _fb;
    private EditText _twitter;
    private EditText _instagram;
    private EditText _linkedin;
    private Button _saveButton;

    public EditViewImpl(BaseActivity activity, ViewGroup container) {
        mRootView = LayoutInflater.from(activity).inflate(R.layout.edit_view, container);
        mActivity = activity;

        initialize();
        setOnClickListeners();
    }

    private void initialize() {
        _firstName = (EditText) mRootView.findViewById(R.id.input_first_name);
        _middleName = (EditText) mRootView.findViewById(R.id.input_middle_name);
        _lastName = (EditText) mRootView.findViewById(R.id.input_last_name);
        _email = (EditText) mRootView.findViewById(R.id.input_email);
        _skype = (EditText) mRootView.findViewById(R.id.input_skype);
        _github = (EditText) mRootView.findViewById(R.id.input_github);
        _vk = (EditText) mRootView.findViewById(R.id.input_vk);
        _fb = (EditText) mRootView.findViewById(R.id.input_fb);
        _twitter = (EditText) mRootView.findViewById(R.id.input_twitter);
        _instagram = (EditText) mRootView.findViewById(R.id.input_instagram);
        _linkedin = (EditText) mRootView.findViewById(R.id.input_linkedin);
        _saveButton = (Button) mRootView.findViewById(R.id.save_btn);
    }

    private void setOnClickListeners() {
        _saveButton.setOnClickListener(view -> sendUpdateRequest());
    }

    //--------Binding profile--------//

    @Override
    public void bindProfile(Profile profile) {
        bindNames(profile);
        bindContacts(profile);
    }

    private void bindNames(Profile profile) {
        _firstName.setText(profile.getFirstName());
        _lastName.setText(profile.getLastName());
        _middleName.setText(profile.getMiddleName());
    }

    private void bindContacts(Profile profile) {
        bindContactToView(profile.getFb(), _fb);
        bindContactToView(profile.getVk(), _vk);
        bindContactToView(profile.getEmail(), _email);
        bindContactToView(profile.getSkype(), _skype);
        bindContactToView(profile.getGithub(), _github);
        bindContactToView(profile.getTwitter(), _twitter);
        bindContactToView(profile.getLinkedin(), _linkedin);
        bindContactToView(profile.getInstagram(), _instagram);
    }

    private void bindContactToView(Contact contact, EditText view) {
        if (validateContact(contact)) {
            view.setText(contact.getUrl());
        }
    }

    private boolean validateContact(Contact contact) {
        return contact != null
                && contact.getUrl() != null
                && !contact.getUrl().isEmpty();
    }

    private void sendUpdateRequest() {

        // if(!validate) {error message}
        // else: gather all texts, make new profile object
        // pass new profile back to activity through callback

        if (mListener != null) {
            mListener.onSaveClick();
        }

    }

    //--------Helpers--------//

    @Override
    public void setListener(EditViewListener listener) {
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

