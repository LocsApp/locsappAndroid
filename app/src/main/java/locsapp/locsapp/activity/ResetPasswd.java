package locsapp.locsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import locsapp.locsapp.R;
import locsapp.locsapp.network.ConnectionUser;

/**
 * Created by Damien on 10/17/2015.
 */

public class ResetPasswd extends FragmentActivity {

    EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset);

        mEmail = (EditText) findViewById(R.id.reg_email);

        Button registerButton = (Button) findViewById(R.id.btnReset);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptReset();
            }
        });
        Button loginScreen = (Button) findViewById(R.id.btnReturn);
        loginScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    protected void attemptReset() {
        ConnectionUser coUser = new ConnectionUser(this);
        coUser.resetPassword(mEmail.getText().toString());
    }

    public void errorCallback(String error) {

    }

    public void successCallback(String result) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
