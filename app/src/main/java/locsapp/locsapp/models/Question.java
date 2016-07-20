package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("id")
    public String id;

    @SerializedName("author_name")
    public String authorName;

    @SerializedName("id_owner_article")
    public Integer ownerId;

    @SerializedName("content")
    public String content;

    @SerializedName("creation_date")
    public String date;

    public Question(String id, Integer ownerId, String content, String authorName) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.authorName = authorName;
    }
}
