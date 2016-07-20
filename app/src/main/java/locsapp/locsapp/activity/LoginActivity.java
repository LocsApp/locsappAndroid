package locsapp.locsapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.AppEventsLogger;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import locsapp.locsapp.R;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.network.ConnectionUser;
import locsapp.locsapp.network.ErrorLogin;
import locsapp.locsapp.network.InfosUser;

// TODO: 7/6/2016 Add error login 

public class LoginActivity extends Activity implements MyCallback {

    LoginButton authButton;
    CallbackManager callbackManager;
    String TAG = "Fb login";
    private EditText mIdView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mSignin;
    private String fbToken;
    private String mToken;
    //private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        mIdView = (EditText) findViewById(R.id.id_login);
        String login = intent.getStringExtra("login");
        if (login != null) {
            mIdView.setText(login);
        }
        mPasswordView = (EditText) findViewById(R.id.password);

        callbackManager = CallbackManager.Factory.create();

        authButton = (LoginButton) findViewById(R.id.login_button);

        authButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        authButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getToken());
                attemptFBLogin(loginResult.getAccessToken().getToken());
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

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignin = (Button) findViewById(R.id.sign_in_button);
        mSignin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.id_login_form);
        mProgressView = findViewById(R.id.login_progress);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        TextView resetPasswd = (TextView) findViewById(R.id.link_to_reset);
        resetPasswd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ResetPasswd.class);
                startActivity(i);
            }
        });

        // Generate keyhash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "locsapp.locsapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++" + e.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++" + e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        float fbIconScale = 1.45F;
        Drawable drawable = getResources().getDrawable(
                com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*fbIconScale),
                (int)(drawable.getIntrinsicHeight()*fbIconScale));
        authButton.setCompoundDrawables(drawable, null, null, null);
        authButton.setCompoundDrawablePadding(getResources().
                getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
        authButton.setPadding(
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_lr),
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_top), 0,
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_bottom));
    }

    public void attemptFBLogin(String token){
        fbToken = token;
        ConnectionUser coUser = new ConnectionUser(this);
        coUser.loginFB(token);
    }

    public void attemptLogin() {

        mIdView.setError(null);
        mPasswordView.setError(null);

        String id_login = mIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(id_login)) {
            mIdView.setError(getString(R.string.error_field_required));
            focusView = mIdView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            ConnectionUser coUser = new ConnectionUser(this);
            coUser.login(id_login, password);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignin.setEnabled(show ? false : true);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void startHome(final String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.saved_token), token);
        editor.apply();

        Log.d("IN STARTHOME", "startHome");
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    public void errorCallback(ErrorLogin error) {
        showProgress(false);
    }

    @Override
    protected void onDestroy() {
      /*  this.subscription.unsubscribe();*/
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        //AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        //AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "login":
                startHome((String) val);
                break;
            case "loginFB":
                startHome((String) val);
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {
        switch (tag) {
            case "login":
                showProgress(false);
                mPasswordView.setError(null);
                mIdView.setError(null);
                Toast.makeText(this, "Wrong username/password",
                        Toast.LENGTH_LONG).show();
        }
    }
}



