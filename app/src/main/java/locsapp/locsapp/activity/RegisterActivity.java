package locsapp.locsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Damien on 10/17/2015.
 */

public class RegisterActivity extends FragmentActivity {

    EditText mEmail;
    EditText mUsername;
    EditText mPassword;
    EditText mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mEmail = (EditText) findViewById(R.id.reg_email);
        mUsername = (EditText) findViewById(R.id.reg_login);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mPasswordConfirm = (EditText) findViewById(R.id.reg_password2);


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

            ConnectionUser coUser = new ConnectionUser(getApplicationContext());
            coUser.register(email, username, password, password2);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

/*    @Override
    public void returnDate(String date) {
        mBirthDate.setText(date);
        mEmail.requestFocus();
    }*/


}
