package com.example.android.ersatz.screens.contacts.view;

import android.widget.ListView;

import com.example.android.ersatz.screens.common.views.BaseView;

public interface ContactsView extends BaseView {

    interface ContactsViewListener {
        void onListItemClick(String id);

        void onDeleteAllProfilesClick();
    }

    void setListener(ContactsViewListener listener);

    void onDeleteAllProfilesClick();

    ListView getListView();

}
