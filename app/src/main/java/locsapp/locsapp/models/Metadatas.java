package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Damien on 11/27/15.
 */
public class Metadatas {
    @SerializedName("total_pages")
    public Integer mTotalPages;

    @SerializedName("total_items")
    public Integer mTotalItems;

    @SerializedName("page_number")
    public Integer mPageNuber;
}
