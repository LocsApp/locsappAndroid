package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Damien on 11/27/15.
 */
public class SearchResults {
    @SerializedName("metadatas")
    public Metadatas metadatas;

    @SerializedName("articles")
    public List<Article> articles;
}
