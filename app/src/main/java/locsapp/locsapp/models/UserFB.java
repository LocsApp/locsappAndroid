package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sylflo on 11/27/15.
 */
public class UserFB {

    @SerializedName("access_token")
    String mToken;

    @SerializedName("code")
    String mCode;

    @SerializedName("username")
    String mUsername;

    public UserFB(String token, String code, String username) {
        mToken = token;
        mCode = code;
        mUsername = username;
    }
}
