package locsapp.locsapp.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import locsapp.locsapp.R;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.network.ConnectionUser;

/**
 * Created by Damien on 10/17/2015.
 */

public class RegisterActivity extends FragmentActivity implements MyCallback {

    EditText mEmail;
    EditText mUsername;
    EditText mUsernameFB;
    EditText mPassword;
    EditText mPasswordConfirm;
    String TAG = "Register FB";
    CallbackManager callbackManager;
    LoginButton authButton;
    String mTokenFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mEmail = (EditText) findViewById(R.id.reg_email);
        mUsername = (EditText) findViewById(R.id.reg_login);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mPasswordConfirm = (EditText) findViewById(R.id.reg_password2);
        mUsernameFB = (EditText) findViewById(R.id.username_fb);

        callbackManager = CallbackManager.Factory.create();
        authButton = (LoginButton) findViewById(R.id.login_button);
        authButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        authButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getToken());
                mTokenFB = loginResult.getAccessToken().getToken();
                attemptFBRegister(mTokenFB);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: ");
            }

        });
/*        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Birthdate");
            }
        }); */
        mPasswordConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
        Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        loginScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        float fbIconScale = 1.45F;
        Drawable drawable = getResources().getDrawable(
                com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * fbIconScale),
                (int) (drawable.getIntrinsicHeight() * fbIconScale));
        authButton.setCompoundDrawables(drawable, null, null, null);
        authButton.setCompoundDrawablePadding(getResources().
                getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
        authButton.setPadding(
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_lr),
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_top), 0,
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_bottom));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void attemptFBRegister(String token){
        String username = mUsernameFB.getText().toString();
        ConnectionUser coUser = new ConnectionUser(this);
        coUser.createUserFB(token, username);
    }

    public void attemptRegister() {
        mEmail.setError(null);
        mUsername.setError(null);
        mPassword.setError(null);
        mPasswordConfirm.setError(null);

        String email = mEmail.getText().toString();
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String password2 = mPasswordConfirm.getText().toString();

        Boolean cancel = false;
        View focusView = null;

        if (!(password2.equals(password))) {
            mPasswordConfirm.setError(getString(R.string.error_match_password));
            focusView = mPasswordConfirm;
            cancel = true;
        }
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        else {

            ConnectionUser coUser = new ConnectionUser(this);
            coUser.register(email, username, password, password2);
        }
    }

    public void startHome(String token) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    public void startLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "register":
                startLogin();
                break;
            case "createUserFB":
                ConnectionUser coUser = new ConnectionUser(this);
                coUser.loginFB(mTokenFB);
                //startHome((String) val);
                break;
            case "loginFB":
                startHome((String) val);
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }

/*    @Override
    public void returnDate(String date) {
        mBirthDate.setText(date);
        mEmail.requestFocus();
    }*/


}
