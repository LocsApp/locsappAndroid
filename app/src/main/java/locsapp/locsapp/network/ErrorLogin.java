package locsapp.locsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylflo on 11/27/15.
 */
public class ErrorLogin {

    @SerializedName("non_field_errors")
    @Expose
    private List<String> nonFieldErrors = new ArrayList<String>();

    /**
     * @return The nonFieldErrors
     */
    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }

    /**
     * @param nonFieldErrors The non_field_errors
     */
    public void setNonFieldErrors(List<String> nonFieldErrors) {
        this.nonFieldErrors = nonFieldErrors;
    }
}
