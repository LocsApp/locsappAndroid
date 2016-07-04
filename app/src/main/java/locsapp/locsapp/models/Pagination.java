package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("page_number")
    public Integer nbPage;

    @SerializedName("items_per_page")
    public Integer nbItems;

    Pagination(Integer page, Integer nbItems){
        nbPage = page;
        this.nbItems = nbItems;
    }
}
