package com.example.android.ersatz.screens.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.screens.common.controllers.MainActivity;
import com.example.android.ersatz.R;
import com.example.android.ersatz.model.network.ItWeekService;
import com.example.android.ersatz.entities.AuthBody;
import com.example.android.ersatz.entities.TokenBody;
import com.example.android.ersatz.screens.common.controllers.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO: manage all constants in one class, like: response_codes, preference_strings, base_url;
// TODO: deal with the UPPER back button
// TODO: ask for the list of errors ad handle them all
// TODO: make name and pass at least 3 chars
// TODO: hugely implement Butterknife
// TODO: Trim inputName

public class SigninActivity extends BaseActivity {

    private static final String TAG = "SigninActivity";
    private long mLastBackPressTime;

    @Inject
    ItWeekService itWeekService;
    @Inject
    ProgressDialog progressDialog;
    @Inject
    ErsatzApp ersatzApp;

    @Bind(R.id.input_account_name)
    EditText _accountNameText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_signin)
    Button _signinButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    @Bind(R.id.input_account_name_wrapper)
    TextInputLayout _accountNameTextWrapper;
    @Bind(R.id.input_password_wrapper)
    TextInputLayout _passwordTextWrapper;

    @BindString(R.string.check_credentials)
    String checkCredentialsMessage;
    @BindString(R.string.success_message)
    String successMessage;
    @BindString(R.string.incorrect_name_or_pass_message)
    String incorrectNameOrPassMessage;
    @BindString(R.string.server_error_message)
    String serverErrorMessage;
    @BindString(R.string.unknown_error_message)
    String unknownErrorMessage;
    @BindString(R.string.no_internet_message)
    String noInternetMessage;
    @BindString(R.string.enter_account_name_message)
    String enterAccountNameMessage;
    @BindString(R.string.enter_pass_message)
    String enterPasswordMessage;
    @BindString(R.string.pre_exit_message)
    String preExitMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponent().inject(this);

        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);

        buildComponent();

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        _signinButton.setOnClickListener(v -> signin());
        _signupLink.setOnClickListener(v -> startSignupActivity());
    }

    private void signin() {
        progressDialog.show();

        if (!validate())
            informSigninResult(checkCredentialsMessage);
        else
            sendSigninRequest();
    }

    private void sendSigninRequest() {
        String accountName = collectAccountName();
        String password = collectPassword();
        AuthBody authBody = new AuthBody(accountName, password);

        itWeekService.signIn(authBody, false, false).enqueue(new Callback<TokenBody>() {
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
                    informSigninResult(incorrectNameOrPassMessage);
                else
                    handleSuccess(body.getToken());
                break;
            case 401:
                informSigninResult(incorrectNameOrPassMessage);
                break;
            case 500:
                informSigninResult(serverErrorMessage);
                break;
            default:
                informSigninResult(unknownErrorMessage);
        }
    }

    private void handleSuccess(String token) {
        informSigninResult(successMessage);
        storeToken(token);
        startMainActivity();
    }

    private void handleFailure() {
        String message = unknownErrorMessage;
        if (!ersatzApp.isNetworkConnected())
            message = noInternetMessage;
        informSigninResult(message);
    }

    //------------Starting next Activities---------------//

    private void startMainActivity() {
        startNextActivity(MainActivity.class);
    }

    private void startSignupActivity() {
        startNextActivity(SignupActivity.class);
        addTransition();
    }

    private void startNextActivity(Class claz) {
        Intent intent = new Intent(getApplicationContext(), claz);
        startActivity(intent);
        finish();
    }

    //------------Validation---------------//

    private boolean validate() {
        return validateAccountName() &&
                validatePassword();
    }

    private boolean validateAccountName() {
        String accountName = collectAccountName();

        boolean valid = true;

        if (accountName.isEmpty()) {
            _accountNameTextWrapper.setError(enterAccountNameMessage);
            valid = false;
        } else {
            _accountNameTextWrapper.setError(null);
        }
        return valid;
    }

    private boolean validatePassword() {
        String password = collectPassword();

        boolean valid = true;

        if (password.isEmpty()) {
            _passwordTextWrapper.setError(enterPasswordMessage);
            valid = false;
        } else {
            _passwordTextWrapper.setError(null);
        }
        return valid;
    }

    //------------UI profile_view---------------//

    private void informSigninResult(String message) {
        showMessage(message);
        progressDialog.dismiss();
    }

    private void addTransition() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onBackPressed() {
        if (hasDoubleCLick()) {
            super.onBackPressed();
            return;
        } else {
            showMessage(preExitMessage);
        }
        mLastBackPressTime = System.currentTimeMillis();
    }

    private boolean hasDoubleCLick() {
        return mLastBackPressTime + TWO_BACK_PRESSES_INTERVAL > System.currentTimeMillis();
    }

    //------------Helpers---------------//

    private String collectAccountName() {
        return _accountNameText.getText().toString();
    }

    private String collectPassword() {
        return _passwordText.getText().toString();
    }

}
