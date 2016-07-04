package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sylflo on 11/24/15.
 */
public class AddressDetails {
    @SerializedName("address")
    public String address;

    @SerializedName("first_name")
    public String firstname;

    @SerializedName("last_name")
    public String lastname;

    @SerializedName("postal_code")
    public String code;

    @SerializedName("city")
    public String city;

    public AddressDetails(String address, String firstname, String lastname, String code, String city) {
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.code = code;
        this.city = city;
    }
}
