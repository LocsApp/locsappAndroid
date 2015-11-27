package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sylflo on 11/24/15.
 */
public class User {


    @SerializedName("email")
    public String mEmail;

    @SerializedName("username")
    public String mUsername;

    @SerializedName("password1")
    public String mPassword1;

    @SerializedName("password2")
    public String mPassword2;

    public User(String email, String username, String password1, String password2) {
        this.mEmail = email;
        this.mUsername = username;
        this.mPassword1 = password1;
        this.mPassword2 = password2;
    }

}
