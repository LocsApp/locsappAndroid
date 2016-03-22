package locsapp.locsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import locsapp.locsapp.R;
import locsapp.locsapp.network.InfosUser;

/**
 * Created by Damien on 10/17/2015.
 */

public class SetUsernameFBActivity extends FragmentActivity {
    private String mToken;
    private String fbToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        mToken = intent.getStringExtra("token");
        fbToken = intent.getStringExtra("fbToken");

        setContentView(R.layout.set_username);

        findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSetUsername();
            }
        });
    }

    public void attemptSetUsername() {
        InfosUser infosUser = new InfosUser(this);
        infosUser.setUsername(mToken, "BLABLA", this);
    }

    public void errorCallback(String error) {

    }

    public void successCallback(String tag, String data) {
        switch (tag) {
            case "SETUSERNAME":
                break;
            default:
                break;
        }
    }
}
