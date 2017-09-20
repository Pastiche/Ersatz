package com.example.android.ersatz.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.ersatz.entities.AuthBody;
import com.example.android.ersatz.entities.Contact;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.entities.TokenBody;
import com.example.android.ersatz.network.ItWeekApi;
import com.example.android.ersatz.network.ItWeekService;

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
         * @param profiles a list of fetched profiles; will never be null
         */
        void onProfilesFetched(List<Profile> profiles);
    }

    private ItWeekService mClient;
    private Context mContext;

    public NetworkProfileManager(Context context) {
        mContext = context;
        mClient = ItWeekApi.getClient().create(ItWeekService.class);
}

    /**
     * Fetch an SMS message by its ID. Fetch will be done on background thread and registered
     * listeners will be notified of result on UI thread.
     *
     * @param id ID of message to fetch
     */
/*    public void fetchSmsMessageById(final long id) {

        List<Profile> result = extractSmsMessagesFromCursor(cursor);
        notifySmsMessagesFetched(result);


    }

    private void sendFetchRequest() {

        AuthBody authBody = new AuthBody(accountName, password);

        client.signIn(authBody).enqueue(new Callback<TokenBody>() {
            @Override
            public void onResponse(Call<TokenBody> call, Response<TokenBody> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<TokenBody> call, Throwable t) {
                handleFailure();
            }
        });
    }

    private void handleResponse(Response<TokenBody> response) {

        TokenBody body = response.body();
        int code = response.code();

        switch (code) {

            case 200:
                if (body.getToken() == null)
                    informSigninResult("Incorrect account name or password");
                    // TODO: ask for the list of errors ad handle them all
                else
                    handleSuccess(body.getToken());
                break;
            case 401:
                informSigninResult("Incorrect account name or password");
                break;
            case 500:
                informSigninResult("Server error");
                break;
            default:
                informSigninResult("Unknown error");

        }
    }

    private void handleSuccess(String token) {
        informSigninResult("Success!");
        storeToken(token);
        startMainActivity();
    }

    private String loadToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authorization", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    private void handleFailure() {
        String message = "Unknown error";

        if (!isNetworkConnected())
            message = "No Internet Connection";

        informSigninResult(message);
    }*/


}
