package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

public class AskQuestion {

    @SerializedName("id_article")
    public String id_article;

    @SerializedName("content")
    public String content;

    public AskQuestion(String id, String content) {
        this.id_article = id;
        this.content = content;
    }
}
