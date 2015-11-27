package locsapp.locsapp.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylflo on 11/26/15.
 */
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.api.Api;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIError {


    @SerializedName("password1")
    @Expose
    private List<String> password1 = new ArrayList<String>();

    /**
     * @return The password1
     */
    public List<String> getPassword1() {
        return password1;
    }

    /**
     * @param password1 The password1
     */
    public void setPassword1(List<String> password1) {
        this.password1 = password1;
    }

    public APIError() {

    }

}