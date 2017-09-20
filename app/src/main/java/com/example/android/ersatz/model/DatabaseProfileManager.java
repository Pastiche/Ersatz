package com.example.android.ersatz.model;

import com.example.android.ersatz.entities.Profile;

import java.util.List;

/**
 * Created by Denis on 20.09.2017.
 */

public class DatabaseProfileManager extends BaseObservableManager<DatabaseProfileManager.DatabaseProfileManagerListener> {

    public interface DatabaseProfileManagerListener {
        /**
         * This method will be called on UI thread when fetching of requested profiles
         * completes.
         *
         * @param profiles a list of fetched profiles; will never be null
         */
        void onProfilesFetched(List<Profile> profiles);
    }

}
