package com.example.android.ersatz.screens.profile.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;

// TODO: make initial visiblity of wrappers = GONE, and reveal them only if we have data
// TODO: make different listener for onEmailClick event
// TODO: replace "profile" with "public" in qr-code
// TODO: make pleasantly looking style

public class MyProfileViewImpl implements MyProfileView {

    private View mRootView;
    private Context mContext;
    private ProfileViewListener mListener;

    private AlertDialog.Builder _qrDialog;
    private ImageView _qrCodeImageView;
    private FloatingActionButton _qrCodeFab;
    private FloatingActionButton _editFab;
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

    public MyProfileViewImpl(LayoutInflater inflater, ViewGroup container, Context context) {
        mRootView = inflater.inflate(R.layout.profile_view, container, false);
        mContext = context;

        initialize();
        setOnClickListeners();
    }

    private void initialize() {
        _qrCodeFab = (FloatingActionButton) mRootView.findViewById(R.id.qr_fab);
        _editFab = (FloatingActionButton) mRootView.findViewById(R.id.edit_fab);
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

        hideUnusedViews();
    }

    private void hideUnusedViews() {
        FloatingActionButton addButton = (FloatingActionButton) mRootView.findViewById(R.id.add_fab);
        addButton.setVisibility(View.GONE);
    }

    private void setOnClickListeners() {
        _qrCodeFab.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShowQrCodeBtnClick();
            }
        });

        _editFab.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onEditClick();
            }
        });
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

    @Override
    public void showQrCode(Bitmap qrCodeImage) {
        // TODO: get rid of border
        buildQrDialogWithImage(qrCodeImage);
        _qrDialog.show();
    }

    private void buildQrDialogWithImage(Bitmap qrCodeImage) {
        buildQrCodeImageView(qrCodeImage);
        buildDialog();
        _qrDialog.setView(_qrCodeImageView);
    }

    private void buildQrCodeImageView(Bitmap qrCodeImage) {
        _qrCodeImageView = new ImageView(mContext);
        _qrCodeImageView.setImageBitmap(qrCodeImage);
    }

    private void buildDialog() {
        _qrDialog = new AlertDialog.Builder(mContext, R.style.AppTheme_Dark_Dialog);
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
