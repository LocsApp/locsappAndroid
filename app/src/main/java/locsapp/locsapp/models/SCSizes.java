package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Damien on 6/27/2016.
 */
public class SCSizes {

    @SerializedName("sizes")
    public List<StaticCollection> mSizes;

    public SCSizes() {

    }
}
