package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Damien on 6/27/2016.
 */
public class SCStates {

    @SerializedName("clothe_states")
    public List<StaticCollection> mStates;

    public SCStates() {

    }
}
