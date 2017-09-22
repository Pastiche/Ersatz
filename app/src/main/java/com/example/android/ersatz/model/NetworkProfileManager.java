package com.example.android.ersatz.model;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.R;
import com.example.android.ersatz.entities.Profile;
import com.example.android.ersatz.network.ItWeekService;
import com.example.android.ersatz.screens.common.BaseActivity;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
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

    private ItWeekService mItWeekService;
    private ErsatzApp mErsatzApp;
    private FragmentActivity mActivity;
    private SharedPreferences mSharedPreferences;

    @BindString(R.string.unknown_error_message)
    String unknownErrorMessage;
    @BindString(R.string.server_error_message)
    String serverErrorMEssage;
    @BindString(R.string.no_internet_message)
    String noInternetMessage;

    @Inject
    public NetworkProfileManager(ItWeekService itWeekService,
                                 SharedPreferences sharedPreferences, FragmentActivity activity) {
        mItWeekService = itWeekService;
        mSharedPreferences = sharedPreferences;
        ButterKnife.bind(this, activity);
    }

/*    public void fetchProfileById(final long id) {

        List<Profile> result = extractSmsMessagesFromCursor(cursor);
        notifySmsMessagesFetched(result);

    }*/

    //-------- fetching data --------//

    public void fetchMyProfile() {
        String token = loadToken();
        mItWeekService.getMyProfile(token).enqueue(new Callback<Profile>() {
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
                notifyError(serverErrorMEssage);
                break;
            default:
                notifyError(unknownErrorMessage);
        }
    }

    private void handleFailure() {
        String message = unknownErrorMessage;

        if (!mErsatzApp.isNetworkConnected())
            message = noInternetMessage;

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

    protected String loadToken() {
        return mSharedPreferences.getString("token", null);
    }
}
