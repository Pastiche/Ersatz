package com.example.android.ersatz.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.network.ErsatzApp;
import com.example.android.ersatz.network.ItWeekService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkProfileManager extends BaseObservableManager<NetworkProfileManager.NetworkProfileManagerListener> {

    public interface NetworkProfileManagerListener {
        /**
         * This method will be called on UI thread when fetching of requested profiles
         * completes.
         *
         * @param profile a list of fetched profiles; will never be null
         */
        void onProfileFetched(Profile profile);

        void onErrorOccurred(String message);
    }

    private ItWeekService itWeekService;
    private Activity mParentActivity;

    public NetworkProfileManager(Activity parentActivity) {
        mParentActivity = parentActivity;
        itWeekService = ErsatzApp.get(parentActivity).getItWeekService();
    }

/*    public void fetchProfileById(final long id) {

        List<Profile> result = extractSmsMessagesFromCursor(cursor);
        notifySmsMessagesFetched(result);


    }*/

    //-------- fetching data --------//
    public void fetchMyProfile() {

        String token = loadToken();

        itWeekService.getMyProfile(token).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                handleFailure();
            }
        });
    }

    //-------- processing response --------//

    private void handleResponse(Response<Profile> response) {

        Profile result = response.body();
        checkResponseOrigin(response);

        int code = response.code();
        switch (code) {
            case 200:
                notifyProfileFetched(result);
                break;
            case 500:
                notifyError("Server error");
                break;
            default:
                notifyError("Unknown error");

        }
    }

    private void handleFailure() {
        String message = "Unknown error";

        if (!isNetworkConnected())
            message = "No Internet Connection";

        notifyError(message);
    }

    private void checkResponseOrigin(Response<Profile> response) {
        if (response.raw().cacheResponse() != null) {
            System.out.println("okh: response was served from cache");
        }

        if (response.raw().networkResponse() != null) {
            System.out.println("okh: response was served from server");
        }
    }

    //-------- notifying listeners --------//

    private void notifyProfileFetched(final Profile profile) {
        for (NetworkProfileManagerListener listener : getListeners()) {
            listener.onProfileFetched(profile);
        }
    }

    private void notifyError(String message) {
        for (NetworkProfileManagerListener listener : getListeners()) {
            listener.onErrorOccurred(message);
        }
    }

    //-------- Helpers --------//

    private String loadToken() {
        SharedPreferences sharedPreferences = mParentActivity.getSharedPreferences("authorization", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mParentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
