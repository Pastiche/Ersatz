package com.example.android.ersatz.screens.contacts.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.search.view.SearchMvcView;

import static com.example.android.ersatz.screens.search.view.SearchMvcView.*;

public class ContactsViewImpl implements ContactsView {

    private Context mContext;
    private View mRootView;
    private ListView mListView;
    private ContactsViewListener mListener;

    public ContactsViewImpl(LayoutInflater inflater, ViewGroup container, Context context) {
        mRootView = inflater.inflate(R.layout.contacts, container, false);
        mContext = context;

        initialize();
        setEmptyView();
        setOnListItemClickListener();
    }

    private void initialize() {
        mListView = (ListView) mRootView.findViewById(R.id.list);
    }

    private void setEmptyView() {
        View emptyView = mRootView.findViewById(R.id.empty_view);
        mListView.setEmptyView(emptyView);
    }

    private void setOnListItemClickListener() {
        mListView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (mListener != null)
                mListener.onListItemClick(getProfilePageId(position));
        });
    }

    private String getProfilePageId(int position) {
        return ((Profile)mListView.getItemAtPosition(position)).getPageId();
    }

    @Override
    public void onDeleteAllProfilesClick() {
        OnClickListener deleteButtonClickListener =
                (dialogInterface, i) -> mListener.onDeleteAllProfilesClick();

        showDeleteConfirmationDialog(deleteButtonClickListener);
    }

    private void showDeleteConfirmationDialog(OnClickListener deleteButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage(R.string.delete_all_contacts_confirmation)
                .setPositiveButton(R.string.delete, deleteButtonClickListener)
                .setNegativeButton(R.string.cancel, (dialog, id) -> dismissDialog(dialog))
                .create()
                .show();
    }

    private void dismissDialog(DialogInterface dialog) {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void setListener(ContactsViewListener listener) {
        mListener = listener;
    }

    @Override
    public ListView getListView() {
        return mListView;
    }
}
