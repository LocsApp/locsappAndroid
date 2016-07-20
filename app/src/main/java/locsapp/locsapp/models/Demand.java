package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Damien on 11/27/15.
 */
public class Demand {

    @SerializedName("id_article")
    public String idArticle;

    @SerializedName("article_url_thumbnail")
    public String thumbnail;

    @SerializedName("author_notation")
    public Integer notation;

    @SerializedName("article_name")
    public String articleName;

    @SerializedName("availibility_start")
    public String dateStart;

    @SerializedName("availibility_end")
    public String dateEnd;

    @SerializedName("author_name")
    public String authorName;

    @SerializedName("id_target")
    public Integer idTarget;

    @SerializedName("name_target")
    public String nameTarget;

    public Demand(String articleId, String articleName, String thumbnail, String authorName, String dateStart, String dateEnd, Integer idTarget, String nameTarget, Integer notation) {
        this.idArticle = articleId;
        this.articleName = articleName;
        this.thumbnail = thumbnail;
        this.notation = notation;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.authorName = authorName;
        this.idTarget = idTarget;
        this.nameTarget = nameTarget;
    }
}

