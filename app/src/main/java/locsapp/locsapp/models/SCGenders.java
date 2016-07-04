package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Damien on 6/27/2016.
 */
public class SCGenders {

    @SerializedName("genders")
    public List<StaticCollection> mGenders;

    public SCGenders() {

    }
}
