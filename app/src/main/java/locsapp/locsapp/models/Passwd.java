package locsapp.locsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passwd {

    @SerializedName("old_password")
    private String oldPasswd;

    @SerializedName("new_password1")
    private String newPasswd1;

    @SerializedName("new_password2")
    private String newPasswd2;

    public Passwd(String old, String new1, String new2) {
        this.oldPasswd = old;
        this.newPasswd1 = new1;
        this.newPasswd2 = new2;
    }
}
