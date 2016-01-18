package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sylflo on 11/27/15.
 */
public class LoginFB {

    @SerializedName("access_token")
    String mToken;

    @SerializedName("code")
    String mCode;

    public LoginFB(String token, String code) {
        mToken = token;
        mCode = code;
    }
}
