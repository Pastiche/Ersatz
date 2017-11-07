package com.example.android.ersatz.screens.search.view;

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

public class SearchMvcViewImpl implements SearchMvcView {

    private View mRootView;
    private Context mContext;
    private SearchViewListener mListener;

    private AlertDialog.Builder _qrDialog;
    private ImageView _qrCodeImageView;
    private FloatingActionButton _qrCodeFab;
    private FloatingActionButton _addFab;
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

    public SearchMvcViewImpl(LayoutInflater inflater, ViewGroup container, Context context) {
        mRootView = inflater.inflate(R.layout.profile_view, container, false);
        mContext = context;

        initialize();
    }

    private void initialize() {
        initializeViews();
        setOnClickListeners();
    }

    private void initializeViews() {
        _qrCodeFab = (FloatingActionButton) mRootView.findViewById(R.id.qr_fab);
        _addFab = (FloatingActionButton) mRootView.findViewById(R.id.add_fab);
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

    private void setOnClickListeners() {
        setQrCodeFabOnClickListener();
        setAddFabOnClickListener();
    }

    private void setQrCodeFabOnClickListener() {
        _qrCodeFab.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShowQrCodeBtnClick();
            }
        });
    }

    private void setAddFabOnClickListener() {
        _addFab.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onAddProfileClick();
                _addFab.setVisibility(View.GONE);
            }
        });
    }

    //--------Binding profile--------//

    @Override
    public void bindProfile(Profile profile) {
        bindNames(profile);
        bindContacts(profile);
        makeFabsVisible();
    }

    private void bindNames(Profile profile) {
        bindNameToView(profile.getFirstName(), _firstName);
        bindNameToView(profile.getMiddleName(), _middleName);
        bindNameToView(profile.getLastName(), _lastName);
    }

    private void bindNameToView(String name, TextView textView) {
        if (validateName(name)) {
            textView.setText(name);
            makeParentViewVisible(textView);
        } else
            hideParentView(textView);
    }

    private boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    private void makeParentViewVisible(TextView view) {
        ((View) view.getParent()).setVisibility(View.VISIBLE);
    }

    private void hideParentView(TextView view) {
        ((View) view.getParent()).setVisibility(View.GONE);
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
            makeParentViewVisible(view);
        } else
            hideParentView(view);
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

    private void makeFabsVisible() {
        _addFab.setVisibility(View.VISIBLE);
        _qrCodeFab.setVisibility(View.VISIBLE);
    }

    //--------QrCode--------//

    @Override
    public void showQrCode(Bitmap qrCodeImage) {
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
    public void setListener(SearchViewListener listener) {
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
