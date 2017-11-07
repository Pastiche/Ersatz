package com.example.android.ersatz.screens.search;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.model.db.ProfileContract.ProfileEntry;
import com.example.android.ersatz.screens.common.controllers.BaseFragment;
import com.example.android.ersatz.screens.search.view.SearchMvcView;
import com.example.android.ersatz.screens.search.view.SearchMvcViewImpl;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

import static com.example.android.ersatz.utils.QrUtils.makeBitmapQrCodeFromUrl;

public class SearchFragment extends BaseFragment implements
        SearchMvcView.SearchViewListener,
        NetworkProfileManager.NetworkProfileManagerListener {

    @Inject
    NetworkProfileManager mNetworkManager;
    @Inject
    ErsatzApp ersatzApp;

    private Profile mProfile;

    private SearchMvcView mView;

    public SearchFragment() {
    }

    //-------- lifecycle --------//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = new SearchMvcViewImpl(inflater, container, getContext());
        mView.setListener(this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null)
                showMessage("You cancelled the scanning");
            else if (validateQuery(result.getContents())) {
                mNetworkManager.fetchProfileById(result.getContents());
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    //-------- view callbacks --------//

    @Override
    public void onAddProfileClick() {
        if (mProfile == null)
            return;
        if (!validateProfilePageUrl())
            return;
        if (!validateProfilePageId())
            return;

        ContentValues values = fillValues();

        Uri newRowUri = getContext().getContentResolver().insert(ProfileEntry.CONTENT_URI, values);
        reportInsertResult(newRowUri);
    }

    private ContentValues fillValues() {
        ContentValues values = new ContentValues();
        values.put(ProfileEntry.COLUMN_PAGE_URL, mProfile.getPageUrl());
        values.put(ProfileEntry.COLUMN_PAGE_ID, mProfile.getPageId());
        return values;
    }

    private void reportInsertResult(Uri newRowUri) {
        String message;
        if (newRowUri == null)
            message = getString(R.string.error_saving_profile);
        else
            message = getString(R.string.profile_saved);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean validateProfilePageUrl() {
        String pageUrl = mProfile.getPageUrl();
        return !TextUtils.isEmpty(pageUrl);
    }

    private boolean validateProfilePageId() {
        String pageId = mProfile.getPageId();
        return !TextUtils.isEmpty(pageId);
    }

    @Override
    public void onContactUrlClick(Contact contact) {

        if (contact.getType().equals(Contact.CONTACT_TYPE_SKYPE))
            showMessage("Skype intent not developed yet...");

        else if (contact.getType().equals(Contact.CONTACT_TYPE_EMAIL))
            startSendEmailIntent(contact.getUrl());

        else startOpenInBrowserIntent(contact);
    }

    private void startOpenInBrowserIntent(Contact contact) {
        String path = contact.getUrl();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contact.getEndpoint(contact.getType()) + path));
        startActivity(browserIntent);
    }

    private void startSendEmailIntent(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    public void onShowQrCodeBtnClick() {
        Bitmap qrCodeImage = makeBitmapQrCodeFromUrl(mProfile.getPageId());
        mView.showQrCode(qrCodeImage);
    }

    //-------- manager callbacks --------//

    @Override
    public void onProfileFetched(Profile profile) {
        if (profile.getPageUrl() != null) {
            mProfile = profile;
            mView.bindProfile(mProfile);
        } else showMessage(getString(R.string.no_results_message));
    }

    @Override
    public void onErrorOccurred(String message) {
        showMessage(message);
    }

    //-------- menu --------//

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = getSearchViewFromMenu(menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String inputId) {
                if (validateQuery(inputId)) {
                    mNetworkManager.fetchProfileById(inputId);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private SearchView getSearchViewFromMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        return (SearchView) MenuItemCompat.getActionView(searchItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_qr_code:
                IntentIntegrator.forSupportFragment(this)
                        .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                        .setPrompt("Scan")
                        .setCameraId(0)
                        .setBeepEnabled(false)
                        .setBarcodeImageEnabled(false)
                        .initiateScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateQuery(String query) {
        return (query != null && !query.equals(""));
    }


}
