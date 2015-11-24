package locsapp.locsapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sylflo on 11/24/15.
 */
public class User {


    @SerializedName("email")
    String mEmail;

    @SerializedName("username")
    String mUsername;

    @SerializedName("password1")
    String mPassword1;

    @SerializedName("password2")
    String mPassword2;

    public User(String email, String username, String password1, String password2) {
        this.mEmail = email;
        this.mUsername = username;
        this.mPassword1 = password1;
        this.mPassword2 = password2;
    }
}
