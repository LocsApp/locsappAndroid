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
    public List<String> mBaseCat = new ArrayList<>();

    @SerializedName("sub_category")
    public List<String> mSubCat = new ArrayList<>();

    @SerializedName("gender")
    public List<String> mGender = new ArrayList<>();

    @SerializedName("size")
    public List<String> mSize = new ArrayList<>();

    @SerializedName("payment_methods")
    public List<String> mPay = new ArrayList<>();

    @SerializedName("clothe_condition")
    public List<String> mState = new ArrayList<>();

    @SerializedName("_pagination")
    public Pagination mPagination;

    public Search(StaticCollections staticCollections, String title, Integer page, Integer nbItem) {
        mTitle = title;
        mPagination = new Pagination(page, nbItem);

        mBaseCat = formatFilter(staticCollections.bchkBaseCategories, staticCollections.scBaseCategories.mBaseCategories);
        mSubCat = formatFilter(staticCollections.bchkSubCategories, staticCollections.scSubCategories.mSubCategories);
        mGender = formatFilter(staticCollections.bchkGenders, staticCollections.scGenders.mGenders);
        //mColor = formatFilter(staticCollections.bchkColors, staticCollections.scColors.mColors);
        mSize = formatFilter(staticCollections.bchkSizes, staticCollections.scSizes.mSizes);
        mPay = formatFilter(staticCollections.bchkPayMethods, staticCollections.scPayMethods.mPayMethods);
        mState = formatFilter(staticCollections.bchkStates, staticCollections.scStates.mStates);
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
