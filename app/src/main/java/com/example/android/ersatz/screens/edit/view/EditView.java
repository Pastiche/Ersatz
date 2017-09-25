package com.example.android.ersatz.screens.edit.view;


import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.screens.common.views.BaseView;

public interface EditView extends BaseView {

    interface EditViewListener {
        void onSaveClick(Profile profile);
    }

    void bindProfile(Profile profile);

    void setListener(EditViewListener listener);

}
