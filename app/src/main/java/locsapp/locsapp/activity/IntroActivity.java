package locsapp.locsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.FacebookSdk;

import locsapp.locsapp.R;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.StaticCollection;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.network.ConnectionUser;
import locsapp.locsapp.network.InfosUser;

/**
 * Created by Damien on 10/17/2015.
 */


// TODO: 7/6/2016 Save user in preference 
public class IntroActivity extends FragmentActivity implements MyCallback {

    String mToken;
    StaticCollections staticCollections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = preferences.getString(getString(R.string.saved_token), null);
        if (preferences.getString(getString(R.string.base_categories), null) == null) {
            staticCollections = new StaticCollections(this);
            staticCollections.retrieveDatas(this);
        }
        else {
            verifyToken();
        }
    }

    public void verifyToken() {
        if (mToken != null) {
            InfosUser infosUser = new InfosUser(this, this);
            infosUser.getUser(mToken);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "retrieveDatas":
                staticCollections.saveDatas();
                verifyToken();
                break;
            case "getUser":
                FacebookSdk.sdkInitialize(getApplicationContext());
                Log.d("IN STARTHOME", "startHome");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("token", mToken);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {
        Log.d("ERROR", "NO LOGGED");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
