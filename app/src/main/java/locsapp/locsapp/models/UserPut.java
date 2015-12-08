package locsapp.locsapp.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Damien on 12/07/15.
 */
public class UserPut {
    @SerializedName("first_name")
    public String mFirstName;

    @SerializedName("last_name")
    public String mLastName;

    @SerializedName("birthdate")
    public String mBirthdate;

    @SerializedName("phone")
    public String mPhone;

    public UserPut(String firstName, String lastName, String birthdate, String phone) {
        mFirstName = null;
        mLastName = null;
        mBirthdate = null;
        mPhone = null;

        if (!TextUtils.isEmpty(firstName))
            this.mFirstName = firstName;
        if (!TextUtils.isEmpty(lastName))
            this.mLastName = lastName;
        if (!TextUtils.isEmpty(birthdate))
            this.mBirthdate = birthdate;
        if (!TextUtils.isEmpty(phone))
            this.mPhone = phone;
    }

}
