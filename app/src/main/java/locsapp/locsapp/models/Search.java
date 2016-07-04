package locsapp.locsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damien on 11/27/15.
 */
public class Search {

    @SerializedName("title")
    public String mTitle;

    @SerializedName("base_category")
    public List<String> mbaseCat = new ArrayList<>();

    @SerializedName("_pagination")
    public Pagination mPagination;

    public Search(StaticCollections staticCollections, String title, Integer page, Integer nbItem) {
        mTitle = title;
        mPagination = new Pagination(page, nbItem);

        mbaseCat = formatFilter(staticCollections.bchkBaseCategories, staticCollections.scBaseCategories.mBaseCategories);
    }

    private List<String> formatFilter(boolean[] checked, List<StaticCollection> collection) {
        boolean all = true;
        List<String> val = new ArrayList<>();
        if (checked != null) {
            for (int i = 0; i < checked.length; i++) {
                if (checked[i]) {
                    all = false;
                    val.add(collection.get(i).getId());
                }
            }
        }
        if (all) {
            for (int i = 0; i < collection.size(); i++) {
                val.add(collection.get(i).getId());
            }
        }
        return val;
    }
}
