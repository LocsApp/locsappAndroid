package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

public class StaticCollectionColor {

    @SerializedName("_id")
    private String id;
    @SerializedName("hex_code")
    private String code;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
