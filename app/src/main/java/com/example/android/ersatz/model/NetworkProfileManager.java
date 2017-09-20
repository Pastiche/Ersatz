package com.example.android.ersatz.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.example.android.ersatz.entities.AuthBody;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.entities.TokenBody;
import com.example.android.ersatz.network.ItWeekApi;
import com.example.android.ersatz.network.ItWeekService;

import java.util.ArrayList;
import java.util.List;

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
        void onProfilesFetched(Profile profile);

        void onErrorOccured(String message);
    }

    private ItWeekService mClient;
    private Context mContext;

    public NetworkProfileManager(Context context) {
        mContext = context;
        mClient = ItWeekApi.getClient().create(ItWeekService.class);
    }

/*    public void fetchProfileById(final long id) {

        List<Profile> result = extractSmsMessagesFromCursor(cursor);
        notifySmsMessagesFetched(result);


    }*/

    //-------- fetching data --------//
    public void fetchMyProfile() {

        String token = loadToken();

        mClient.getMyProfile(token).enqueue(new Callback<Profile>() {
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

    //-------- notifying listeners --------//

    private void notifyProfileFetched(final Profile profile) {
        for (NetworkProfileManagerListener listener : getListeners()) {
            listener.onProfilesFetched(profile);
        }
    }

    private void notifyError(String message) {
        for (NetworkProfileManagerListener listener : getListeners()) {
            listener.onErrorOccured(message);
        }
    }

    //-------- Helpers --------//

    private String loadToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authorization", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
