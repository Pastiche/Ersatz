package com.example.android.ersatz.screens.edit.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.controllers.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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

    String firstName;
    String lastName;
    String email;

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

        // TODO: is it ok to send empty bodies??
        // TODO: Refactor this mess

        firstName = _firstName.getText().toString();
        lastName = _lastName.getText().toString();
        email = _email.getText().toString();
        String middleName = _middleName.getText().toString();
        String skype = _skype.getText().toString();
        String github = _github.getText().toString();
        String vk = _vk.getText().toString();
        String fb = _fb.getText().toString();
        String twitter = _twitter.getText().toString();
        String instagram = _instagram.getText().toString();
        String linkedin = _linkedin.getText().toString();

        if (!validate())
            Toast.makeText(mActivity, R.string.check_credentials, Toast.LENGTH_SHORT).show();
        else {

            List<Contact> contacts = new ArrayList<>();
            contacts.add(new Contact(Contact.CONTACT_TYPE_EMAIL, email));
            contacts.add(new Contact(Contact.CONTACT_TYPE_SKYPE, skype));
            contacts.add(new Contact(Contact.CONTACT_TYPE_GITHUB, github));
            contacts.add(new Contact(Contact.CONTACT_TYPE_VK, vk));
            contacts.add(new Contact(Contact.CONTACT_TYPE_FB, fb));
            contacts.add(new Contact(Contact.CONTACT_TYPE_TWITTER, twitter));
            contacts.add(new Contact(Contact.CONTACT_TYPE_INSTAGRAM, instagram));
            contacts.add(new Contact(Contact.CONTACT_TYPE_LINKEDIN, linkedin));

            Profile updatedProfile = new Profile(firstName, middleName, lastName, contacts);

            if (mListener != null) {
                mListener.onSaveClick(updatedProfile);
            }
        }
    }

    private boolean validate() {
        return validateFirstName() &&
                validateLastName() &&
                validateEmail();
    }

    private boolean validateFirstName() {
        boolean valid = true;

        if (firstName.isEmpty() || firstName.length() < 3) {
            _firstName.setError(mActivity.getString(R.string.short_name_message));
            valid = false;
        } else {
            _firstName.setError(null);
        }
        return valid;
    }

    private boolean validateLastName() {
        boolean valid = true;

        if (lastName.isEmpty() || lastName.length() < 3) {
            _lastName.setError(mActivity.getString(R.string.short_name_message));
            valid = false;
        } else {
            _lastName.setError(null);
        }
        return valid;
    }

    private boolean validateEmail() {
        boolean valid = true;

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError(mActivity.getString(R.string.enter_valid_email_message));
            valid = false;
        } else {
            _email.setError(null);
        }
        return valid;
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

