package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

public class UserFB {

    @SerializedName("facebook_token")
    String mToken;

    @SerializedName("username")
    String mUsername;

    public UserFB(String token, String username) {
        mToken = token;
        mUsername = username;
    }
}
