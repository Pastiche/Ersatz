package com.example.android.ersatz.screens.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ersatz.R;
import com.example.android.ersatz.screens.common.BaseFragment;


public class ContactsFragment extends BaseFragment {

    private SearchView searchView;

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.contacts, container, false);

        return rootView;
    }


}
