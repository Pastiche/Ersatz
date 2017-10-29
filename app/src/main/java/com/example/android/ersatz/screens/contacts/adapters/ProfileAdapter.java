package com.example.android.ersatz.screens.contacts.adapters;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Profile;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter<Profile> {

    private View mListItemView;
    private Profile mCurrentProfile;

    public ProfileAdapter(Context context, List<Profile> profiles) {
        super(context, 0, profiles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        initializeListItemView(convertView, parent);
        mCurrentProfile = getItem(position);
        setListItemContent();

        return mListItemView;
    }

    private void initializeListItemView(View convertView, ViewGroup parent) {
        mListItemView = convertView;
        if (mListItemView == null) {
            mListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
    }

    private void setListItemContent() {
        setFirstName();
        setLastName();
    }

    private void setFirstName() {
        TextView tvFirstName = (TextView) mListItemView.findViewById(R.id.first_name_list_item);
        String firstName = mCurrentProfile.getFirstName();
        if (validateString(firstName))
            tvFirstName.setText(firstName);
    }

    private void setLastName() {
        TextView tvLastName = (TextView) mListItemView.findViewById(R.id.last_name_list_item);
        String lastName = mCurrentProfile.getLastName();
        if (validateString(lastName))
            tvLastName.setText(lastName);
    }

    private boolean validateString(String firstName) {
        return ((firstName != null) && (!TextUtils.isEmpty(firstName)));
    }

}
