package com.example.android.ersatz.screens.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ersatz.screens.common.controllers.MainActivity;
import com.example.android.ersatz.R;
import com.example.android.ersatz.ErsatzApp;
import com.example.android.ersatz.network.ItWeekService;
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

public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";

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
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_signin)
    TextView _signinLink;
    @Bind(R.id.input_account_name_wrapper)
    TextInputLayout _accountNameTextWrapper;
    @Bind(R.id.input_password_wrapper)
    TextInputLayout _passwordTextWrapper;
    @Bind(R.id.input_password_re_enter_wrapper)
    TextInputLayout _rePasswordTextWrapper;

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
    @BindString(R.string.account_exists_message)
    String accountExistsMessage;
    @BindString(R.string.short_name_message)
    String atleastThreeCharsMessage;
    @BindString(R.string.short_password_message)
    String shortPassMessage;
    @BindString(R.string.pass_not_match_message)
    String passNotMatchMessage;

    // TODO: Deal with progressDialog
    // TODO: extract abstract class and interface (?) for signup and signin
    // TODO: provide DI with Dagger 2
    // TODO: deal with onBackPressedButton

    @Override
    public void onCreate(Bundle savedInstanceState) {
        buildComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        buildComponent();

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        _signupButton.setOnClickListener(v -> signup());
        _signinLink.setOnClickListener(v -> startSigninActivity());
    }

    public void signup() {
        progressDialog.show();

        if (!validate()) {
            informSignupResult(checkCredentialsMessage);
        } else
            sendSignupRequest();
    }

    private void sendSignupRequest() {
        String accountName = collectAccountName();
        String password = collectPassword();
        AuthBody authBody = new AuthBody(accountName, password);

        itWeekService.signUp(authBody, false, false).enqueue(new Callback<TokenBody>() {
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
                    informSignupResult(accountExistsMessage);
                else
                    handleSuccess(body.getToken());
                break;
            case 401:
                informSignupResult(incorrectNameOrPassMessage);
                break;
            case 500:
                informSignupResult(serverErrorMessage);
                break;
            default:
                informSignupResult(unknownErrorMessage);
        }
    }

    private void handleSuccess(String token) {
        informSignupResult(successMessage);
        storeToken(token);
        startMainActivity();
    }

    private void handleFailure() {
        String message = unknownErrorMessage;
        if (!ersatzApp.isNetworkConnected())
            message = noInternetMessage;
        informSignupResult(message);
    }

    //------------Starting next Activities---------------//

    private void startMainActivity() {
        startNextActivity(MainActivity.class);
    }

    private void startSigninActivity() {
        startNextActivity(SigninActivity.class);
        addTransition();
    }

    private void startNextActivity(Class claz) {
        Intent intent = new Intent(getApplicationContext(), claz);
        startActivity(intent);
        finish();
    }

    //--------------Validation--------------//

    public boolean validate() {
        return validateAccountName() &&
                validatePassword() &&
                validateReEnterPassword();
    }

    private boolean validateAccountName() {
        String accountName = collectAccountName();

        boolean valid = true;

        if (accountName.isEmpty() || accountName.length() < 3) {
            _accountNameTextWrapper.setError(atleastThreeCharsMessage);
            valid = false;
        } else {
            _accountNameTextWrapper.setError(null);
        }
        return valid;
    }

    private boolean validatePassword() {
        String password = _passwordText.getText().toString();

        boolean valid = true;

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordTextWrapper.setError(shortPassMessage);
            valid = false;
        } else {
            _passwordTextWrapper.setError(null);
        }
        return valid;
    }

    private boolean validateReEnterPassword() {
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String password = _passwordText.getText().toString();

        boolean valid = true;

        if (reEnterPassword.isEmpty()
                || reEnterPassword.length() < 4
                || reEnterPassword.length() > 10
                || !(reEnterPassword.equals(password))) {
            _rePasswordTextWrapper.setError(passNotMatchMessage);
            valid = false;
        } else {
            _rePasswordTextWrapper.setError(null);
        }
        return valid;
    }

    //------------UI profile_view---------------//

    public void informSignupResult(String message) {
        showMessage(message);
        progressDialog.dismiss();
    }

    private void addTransition() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    //------------Helpers---------------//

    private String collectAccountName() {
        return _accountNameText.getText().toString();
    }

    private String collectPassword() {
        return _passwordText.getText().toString();
    }

}