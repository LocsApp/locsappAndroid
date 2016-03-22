package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

public class LoginFB {

    @SerializedName("facebook_token")
    String mToken;

    public LoginFB(String token) {
        mToken = token;
    }
}
