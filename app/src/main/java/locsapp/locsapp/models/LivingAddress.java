package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class LivingAddress {
    @SerializedName("living_address")
    public ArrayList<Object> address;

    public LivingAddress(String alias, String address, String lastname, String firstname, String code, String city) {
        this.address = new ArrayList<>();
        this.address.add(alias);
        this.address.add(new AddressDetails(address, firstname, lastname, code, city));
    }
}
