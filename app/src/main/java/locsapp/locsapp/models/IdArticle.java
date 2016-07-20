package locsapp.locsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdArticle {

    @SerializedName("id_article")
    public String id;

    public IdArticle(String id) {
        this.id = id;
    }
}
