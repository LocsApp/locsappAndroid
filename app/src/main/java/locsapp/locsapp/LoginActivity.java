package locsapp.locsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends Activity implements Connection.RequestCallback {

    private EditText mIdView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_login);
        mIdView = (EditText) findViewById(R.id.id_login);
        String login = intent.getStringExtra("login");
        if (login != null) {
            mIdView.setText(login);
        }
        mPasswordView = (EditText) findViewById(R.id.password);
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


        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
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


      /*  ApiEndpointInterface apiService = ServiceGenerator.createService(ApiEndpointInterface.class);
        User user = new User("dev.chateau@gmail.com", "sylflo", "toto42", "toto42");
        Call<User> call = apiService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response,
                                   Retrofit retrofit) {

                Log.d("Response42Succes", response.toString() + " " + retrofit.toString());
                User user = response.body();
               if (user != null) {
                    Log.d("Response42", "user succes");
                } else {

                   Log.d("Response42Null", "user failure");
                    Log.d("REsponse42Null", response.message());
                   Log.d("REsponse42Null", response.errorBody().toString());
                   Log.d("REsponse42Null", response.body().toString());

                }


                //Log.d("Response", response.body().toString());

            }

            @Override
            public void onFailure(Throwable t) {

                Log.d("Response42 Error", t.getMessage());

            }
        });*/
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
        }
        else {
            showProgress(true);
            Connection co = new Connection(this);
            co.connectUser(id_login, password);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    @Override
    public void successCallback(Object result) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("token", "qlsjsqc");
        startActivity(intent);
        this.finish();
    }

    @Override
    public void errorCallback(JSONObject error) {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
}



