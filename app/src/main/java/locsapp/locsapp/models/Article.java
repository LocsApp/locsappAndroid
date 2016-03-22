package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Damien on 11/27/15.
 */
public class Article {

    @SerializedName("title")
    String mTitle;

    @SerializedName("description")
    String mDescription;

    @SerializedName("price")
    Integer mPrice;

    @SerializedName("image")
    String mImageURL;

    public String getTitle(){
        return mTitle;
    }
    public String getDescription(){
        return mDescription;
    }
    public Integer getPrice(){
        return mPrice;
    }
    public String getImage(){
        return mImageURL;
    }

    public Article(String title, String description, Integer price, String image) {
        mTitle = title;
        mDescription = description;
        mPrice = price;
        mImageURL = image;
    }
}
