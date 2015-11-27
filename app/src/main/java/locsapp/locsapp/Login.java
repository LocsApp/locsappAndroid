package locsapp.locsapp;

import com.google.gson.annotations.SerializedName;

import java.util.StringTokenizer;

/**
 * Created by sylflo on 11/27/15.
 */
public class Login {

    @SerializedName("username")
    String mUsername;

    @SerializedName("password")
    String mPassword;

    Login(String username, String password) {
        mUsername = username;
        mPassword = password;
    }
}
