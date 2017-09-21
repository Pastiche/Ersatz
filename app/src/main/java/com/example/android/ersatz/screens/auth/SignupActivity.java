package com.example.android.ersatz.screens.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ersatz.MainActivity;
import com.example.android.ersatz.R;
import com.example.android.ersatz.network.ErsatzApp;
import com.example.android.ersatz.network.ItWeekService;
import com.example.android.ersatz.entities.AuthBody;
import com.example.android.ersatz.entities.TokenBody;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    ItWeekService itWeekService;
    private ProgressDialog progressDialog;

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

    // TODO: Deal with progressbar
    // TODO: extract abstract method and interface (?) for signup and signin
    // TODO: provide DI with Dagger 2
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        itWeekService = ErsatzApp.get(this).getItWeekService();
        setOnClickListeners();
    }

    public void signup() {

        launchProgressDialog();

        if (!validate()) {
            informSignupResult("Check credentials");
        } else
            sendSignupRequest();


    }

    private void sendSignupRequest() {

        String accountName = collectAccountName();
        String password = collectPassword();

        AuthBody authBody = new AuthBody(accountName, password);

        itWeekService.signUp(authBody).enqueue(new Callback<TokenBody>() {
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
                    informSignupResult("Account already exists");
                    // TODO: ask for the list of errors ad handle them all
                else
                    handleSuccess(body.getToken());
                break;
            case 401:
                informSignupResult("Incorrect account name or password");
                break;
            case 500:
                informSignupResult("Server error");
                break;
            default:
                informSignupResult("Unknown error");
        }
    }

    private void handleSuccess(String token) {
        informSignupResult("Success!");
        storeToken(token);
        startMainActivity();
    }

    private void storeToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("authorization", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private void handleFailure() {
        String message = "Unknown error";

        if (!isNetworkConnected())
            message = "No Internet Connection";

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
            _accountNameTextWrapper.setError("at least 3 characters");
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
            _passwordTextWrapper.setError("between 4 and 10 alphanumeric characters");
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

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _rePasswordTextWrapper.setError("Password does not match");
            valid = false;
        } else {
            _rePasswordTextWrapper.setError(null);
        }
        return valid;
    }

    //------------UI profile_view---------------//

    private void setOnClickListeners() {
        _signupButton.setOnClickListener(v -> signup());
        _signinLink.setOnClickListener(v -> startSigninActivity());
    }

    private void launchProgressDialog() {
        constructProgressDialog();
        progressDialog.show();
    }

    private void constructProgressDialog() {

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing up...");

    }

    public void informSignupResult(String message) {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}