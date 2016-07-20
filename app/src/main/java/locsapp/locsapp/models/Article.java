package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Damien on 11/27/15.
 */
public class Article {
    @SerializedName("questions")
    public List<Question> questions;

    @SerializedName("_id")
    String id;

    @SerializedName("id_article")
    String id_article;

    @SerializedName("url_thumbnail")
    String thumbnail;

    @SerializedName("id_author")
    Integer author;

    @SerializedName("gender")
    String gender;

    @SerializedName("color")
    String color;

    @SerializedName("payment_methods")
    List<String> payMethods;

    @SerializedName("availibility_end")
    String dateEnd;

    @SerializedName("availibility_start")
    String dateStart;

    @SerializedName("clothe_condition")
    String clotheCondition;

    @SerializedName("base_category")
    String category;

    @SerializedName("sub_category")
    String subCategory;

    @SerializedName("size")
    String size;

    @SerializedName("url_picture")
    List<String> pictures;

/*    @SerializedName("creation_date")
    String dateCreation;*/

    @SerializedName("available")
    Boolean available;

    @SerializedName("brand")
    String brand;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("price")
    Integer price;

    public List<Question> getQuestions() {
        return questions;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public Integer getAuthor() {
        return author;
    }

    public Integer getPrice() {
        return price;
    }

    public List<String> getPayMethods() {
        return payMethods;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getClotheCondition() {
        return clotheCondition;
    }

    public String getColor() {
        return color;
    }

/*    public String getDateCreation() {
        return dateCreation;
    }*/

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title.substring(0,1).toUpperCase() + title.substring(1);
    }

    public String getId_article() {
        return id_article;
    }
}
