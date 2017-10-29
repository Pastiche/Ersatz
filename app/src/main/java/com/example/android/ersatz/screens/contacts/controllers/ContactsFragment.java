package com.example.android.ersatz.screens.contacts.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.model.NetworkProfileManager;
import com.example.android.ersatz.model.NetworkProfileManager.NetworkProfileManagerListener;
import com.example.android.ersatz.model.db.ProfileContract.ProfileEntry;
import com.example.android.ersatz.screens.common.controllers.BaseFragment;
import com.example.android.ersatz.screens.contacts.adapters.ProfileAdapter;
import com.example.android.ersatz.screens.contacts.view.ContactsView;
import com.example.android.ersatz.screens.contacts.view.ContactsView.ContactsViewListener;
import com.example.android.ersatz.screens.contacts.view.ContactsViewImpl;
import com.example.android.ersatz.screens.edit.EditActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactsFragment extends BaseFragment implements LoaderCallbacks<Cursor>,
        NetworkProfileManagerListener, ContactsViewListener {

    // TODO: set up search throughout contacts
    // TODO: make representation CUTE

    private static final int PPOFILE_LOADER = 0;

    @Inject
    ProfileAdapter mAdapter;
    @Inject
    NetworkProfileManager mNetworkManager;
    @Inject
    CursorLoader mLoader;

    ContactsView mView;

    public ContactsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = new ContactsViewImpl(inflater, container, getContext());
        init();

        return mView.getRootView();
    }

    private void init() {
        getLoaderManager().initLoader(PPOFILE_LOADER, null, this);
        initListView();
    }

    private void initListView() {
        ListView profileListView = mView.getListView();
        profileListView.setAdapter(mAdapter);
        mView.setListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mLoader.forceLoad();
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mAdapter.clear();

        List<String> IdsOfPagesToLoad = getAllPageIdsFromCursor(cursor);

        for (String id : IdsOfPagesToLoad)
            mNetworkManager.fetchProfileById(id);

    }

    private List<String> getAllPageIdsFromCursor(Cursor cursor) {
        System.out.println("FETCHING...");
        List<String> pageIds = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String pageId = getPageIdFromCursor(cursor);
                pageIds.add(pageId);
            } while (cursor.moveToNext());
        }

        return pageIds;
    }

    private String getPageIdFromCursor(Cursor cursor) {
        int pageIdColumnIndex = cursor.getColumnIndex(ProfileEntry.COLUMN_PAGE_ID);
        return cursor.getString(pageIdColumnIndex);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.clear();
        System.out.println("RESET CURSOR: mAdapter.swapCursor(null);");
    }

    @Override
    public void onProfileFetched(Profile profile) {
        mAdapter.add(profile);
    }

    @Override
    public void onErrorOccurred(String message) {
        showMessage(message);
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


    //-------- menu --------//

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_contacts_item:
                mView.onDeleteAllProfilesClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //-------- view callbacks --------//

    @Override
    public void onListItemClick(String profilePageId) {
        startProfileDetailsActivity(profilePageId);
    }

    @Override
    public void onDeleteAllProfilesClick() {
        getContext().getContentResolver().delete(ProfileEntry.CONTENT_URI, null, null);
        showMessage(getString(R.string.all_profiles_deleted_message));
    }

    private void startProfileDetailsActivity(String pageId) {
        Intent intent = new Intent(getContext(), ProfileDetailsActivity.class);
        intent.putExtra("page_id", pageId);
        startActivity(intent);
    }

}
