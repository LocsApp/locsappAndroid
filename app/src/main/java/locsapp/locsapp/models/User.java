package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sylflo on 11/24/15.
 */
public class User {
    @SerializedName("id")
    public Integer mId;

    @SerializedName("username")
    public String mUsername;

    @SerializedName("email")
    public String mEmail;

    @SerializedName("secondary_emails")
    public String mEmail2;

    @SerializedName("first_name")
    public String mFirstName;

    @SerializedName("last_name")
    public String mLastName;

    @SerializedName("birthdate")
    public String mBirthdate;

    @SerializedName("phone")
    public String mPhone;

    @SerializedName("living_address")
    public ArrayList<ArrayList<String>> mLivingAddress;

    @SerializedName("registered_date")
    public String mRegisterDate;

    @SerializedName("last_activity_date")
    public String mLastActivity;

    @SerializedName("billing_address")
    public ArrayList<ArrayList<String>> mBillingAddress;

    @SerializedName("password1")
    public String mPassword1;

    @SerializedName("password2")
    public String mPassword2;

    @SerializedName("logo_url")
    public String mLogoUrl;

    /*@SerializedName("is_active")
    public Boolean mActive;
*/
    @SerializedName("role")
    public String mRole;

    public User(String email, String username, String password1, String password2) {
        this.mEmail = email;
        this.mUsername = username;
        this.mPassword1 = password1;
        this.mPassword2 = password2;
    }

}
