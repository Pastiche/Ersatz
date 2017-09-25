package com.example.android.ersatz.screens.search;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.screens.common.controllers.BaseFragment;
import com.example.android.ersatz.screens.search.view.SearchMvcView;
import com.example.android.ersatz.screens.search.view.SearchMvcViewImpl;

import static com.example.android.ersatz.utils.QrUtils.*;

import javax.inject.Inject;

// TODO: make menu part of the view (?)
// TODO: implement dagger 2 deeper

public class SearchFragment extends BaseFragment implements
        SearchMvcView.SearchViewListener,
        NetworkProfileManager.NetworkProfileManagerListener {

    @Inject
    NetworkProfileManager mNetworkManager;
    @Inject
    ErsatzApp ersatzApp;

    private Profile mProfile;

    private SearchMvcViewImpl mView;

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

        mView = new SearchMvcViewImpl(inflater, container, this);
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

    //-------- view callbacks --------//

    @Override
    public void onAddClick() {
        showMessage("Adding...");
    }

    @Override
    public void onContactUrlClick(Contact contact) {

    }

    @Override
    public void onShowQrCodeBtnClick() {
        Bitmap qrCodeImage = makeBitmapQrCodeFromUrl(mProfile.getPageUrl());
        mView.showQrCode(qrCodeImage);
    }

    //-------- manager callbacks --------//

    @Override
    public void onProfileFetched(Profile profile) {
        mProfile = profile;
        mView.bindProfile(mProfile);
    }

    @Override
    public void onErrorOccurred(String message) {
        showMessage(message);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = getSearchViewFromMenu(menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String inputId) {
                if (validateQuery(inputId)) {
/*                    String pageId = searchView.getQuery().toString();*/
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

    private boolean validateQuery(String query) {
        return (query != null && !query.equals(""));
    }
}
