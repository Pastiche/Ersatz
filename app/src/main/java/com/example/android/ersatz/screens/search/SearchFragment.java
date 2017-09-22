package com.example.android.ersatz.screens.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ersatz.R;
import com.example.android.ersatz.screens.common.BaseFragment;


public class SearchFragment extends BaseFragment {

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.search, container, false);

        return rootView;
    }

}
