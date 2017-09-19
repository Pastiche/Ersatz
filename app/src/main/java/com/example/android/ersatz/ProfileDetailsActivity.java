package com.example.android.ersatz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Denis on 18.09.2017.
 */

public class ProfileDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

/*    @Bind(R.id.input_account_name)
    EditText _accountNameText;
    @Bind(R.id.input_first_name)
    EditText _firstNameText;
    @Bind(R.id.input_middle_name)
    EditText _middleNameText;
    @Bind(R.id.input_last_name)
    EditText _lastNameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_skype)
    EditText _skypeText;
    @Bind(R.id.input_github)
    EditText _githubText;
    @Bind(R.id.input_vk)
    EditText _vkText;
    @Bind(R.id.input_fb)
    EditText _fbText;
    @Bind(R.id.input_twitter)
    EditText _twitterText;
    @Bind(R.id.input_instagram)
    EditText _instagramText;
    @Bind(R.id.input_linkedin)
    EditText _linkedinText;

    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_signin)
    TextView _signinLink;*/

/*    private boolean validateFirstName() {
        String firstName = collectFirstName();

        boolean valid = true;

        if (firstName.isEmpty() || firstName.length() < 3) {
            _firstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _firstNameText.setError(null);
        }
        return valid;
    }

    private boolean validateLastName() {
        String lastName = _lastNameText.getText().toString();

        boolean valid = true;

        if (lastName.isEmpty() || lastName.length() < 3) {
            _lastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lastNameText.setError(null);
        }
        return valid;
    }

    private boolean validateEmail() {
        String email = _emailText.getText().toString();

        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        return valid;
    }
    */
}
