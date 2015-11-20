package locsapp.locsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Damien on 10/17/2015.
 */

public class RegisterActivity extends FragmentActivity
        implements DatePickerFragment.DateListener, Connection.RequestCallback {

    EditText mFirstname;
    EditText mLastname;
    EditText mBirthDate;
    EditText mEmail;
    EditText mPhone;
    EditText mLivingAddr;
    EditText mBillAddr;
    EditText mUsername;
    EditText mPassword;
    EditText mPasswordConfirm;
    TextView mSameAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mFirstname = (EditText) findViewById(R.id.reg_firstname);
        mLastname = (EditText) findViewById(R.id.reg_lastname);
        mBirthDate = (EditText) findViewById(R.id.reg_birthdate);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPhone = (EditText) findViewById(R.id.reg_phone);
        mLivingAddr = (EditText) findViewById(R.id.reg_livingAdr);
        mBillAddr = (EditText) findViewById(R.id.reg_BillingAdr);
        mUsername = (EditText) findViewById(R.id.reg_login);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mPasswordConfirm = (EditText) findViewById(R.id.reg_password2);
        mSameAddr = (TextView) findViewById(R.id.reg_sameAddr);

        mSameAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillAddr.setText(mLivingAddr.getText().toString());
            }
        });

        mLastname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "Birthdate");
                    return true;
                }
                return false;
            }
        });
        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Birthdate");
            }
        });
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
        mFirstname.setError(null);
        mLastname.setError(null);
        mBirthDate.setError(null);
        mEmail.setError(null);
        mPhone.setError(null);
        mLivingAddr.setError(null);
        mBillAddr.setError(null);
        mUsername.setError(null);
        mPassword.setError(null);
        mPasswordConfirm.setError(null);

        String firstname = mFirstname.getText().toString();
        String lastname = mLastname.getText().toString();
        String birthdate = mBirthDate.getText().toString();
        String email = mEmail.getText().toString();
        String phone = mPhone.getText().toString();
        String livingAddr = mLivingAddr.getText().toString();
        String billAddr = mBillAddr.getText().toString();
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String password2 = mPasswordConfirm.getText().toString();

        Boolean cancel = false;
        View focusView = null;

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
        if (cancel) {
            focusView.requestFocus();
        }
        else {
            Log.d("Connect", "OK");
            Connection co = new Connection(this);
            co.registerUser(email, firstname, lastname, username, password, password2, birthdate, phone, livingAddr, billAddr, "blabla", true);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    @Override
    public void returnDate(String date) {
        mBirthDate.setText(date);
        mEmail.requestFocus();
    }

    @Override
    public void successCallback(Object result) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("login", mUsername.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void errorCallback(String error) {

    }
}
