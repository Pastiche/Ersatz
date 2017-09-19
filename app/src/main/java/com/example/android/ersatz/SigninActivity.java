package com.example.android.ersatz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ersatz.api.ItWeekApi;
import com.example.android.ersatz.api.ItWeekService;
import com.example.android.ersatz.entities.AuthBody;
import com.example.android.ersatz.entities.TokenBody;

import butterknife.ButterKnife;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    private static final String TAG = "SigninActivity";

    private ItWeekService client = ItWeekApi.getClient().create(ItWeekService.class);

    private static final int TWO_BACK_PRESSES_INTERVAL = 2000; // # milliseconds
    private long mLastBackPressTime;
    private ProgressDialog progressDialog;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        setOnClickListeners();
    }

    private void signin() {

        launchProgressDialog();

        if (!validate())
            informSigninResult("Check credentials");
        else
            sendSigninRequest();

    }

    private void sendSigninRequest() {
        String accountName = collectAccountName();
        String password = collectPassword();

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
            _accountNameTextWrapper.setError("enter account name");
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
            _passwordTextWrapper.setError("enter password");
            valid = false;
        } else {
            _passwordTextWrapper.setError(null);
        }
        return valid;
    }

    //------------UI details---------------//

    private void setOnClickListeners() {
        _signinButton.setOnClickListener(v -> signin());
        _signupLink.setOnClickListener(v -> startSignupActivity());
    }

    private void launchProgressDialog() {
        constructProgressDialog();
        progressDialog.show();
    }

    private void constructProgressDialog() {

        progressDialog = new ProgressDialog(SigninActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing in...");

    }

    private void informSigninResult(String message) {
        Toast.makeText(SigninActivity.this, message, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    private void addTransition() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onBackPressed() {
        if (mLastBackPressTime + TWO_BACK_PRESSES_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mLastBackPressTime = System.currentTimeMillis();
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
