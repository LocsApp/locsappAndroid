package locsapp.locsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.models.User;
import locsapp.locsapp.network.InfosUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountArticles extends android.support.v4.app.Fragment implements MyCallback{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout = R.layout.article_details;
    private Article article;
    private static int section;
    private HomeActivity mActivity;
    private FragmentManager fragmentManager;
    private StaticCollections staticCollections;

    private TextView mTitle;
    private TextView mPay;
    private TextView mPrice;
    private TextView mCategory;
    private TextView mSubCategory;
    private TextView mGender;
    private TextView mSize;
    private TextView mColor;
    private TextView mBrand;
    private TextView mState;
    private TextView mDescription;

    public static AccountArticles newInstance(int sectionNumber) {
        AccountArticles fragment = new AccountArticles();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.article_details);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public AccountArticles() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(this.layout, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (HomeActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();
        staticCollections = mActivity.staticCollections;
        article = mActivity.mArticle;

        mTitle = (TextView) view.findViewById(R.id.title);
        mPrice = (TextView) view.findViewById(R.id.price);
        mPay = (TextView) view.findViewById(R.id.payMethods);
        mCategory = (TextView) view.findViewById(R.id.category);
        mSubCategory = (TextView) view.findViewById(R.id.subCategory);
        mGender = (TextView) view.findViewById(R.id.gender);
        mSize = (TextView) view.findViewById(R.id.size);
        mColor = (TextView) view.findViewById(R.id.color);
        mBrand = (TextView) view.findViewById(R.id.brand);
        mState = (TextView) view.findViewById(R.id.state);
        mDescription = (TextView) view.findViewById(R.id.description);

        mTitle.setText(article.getTitle());
        mPrice.setText(article.getPrice().toString() + " €");
        mPay.setText(staticCollections.getPayMethods(article.getPayMethods()));
        mCategory.setText(staticCollections.getCategory(article.getCategory()));
        mSubCategory.setText(staticCollections.getSubCategory(article.getSubCategory()));
        mGender.setText(staticCollections.getGender(article.getGender()));
        mSize.setText(staticCollections.getSize(article.getSize()));
        mColor.setText(staticCollections.getColorValue(article.getColor()));
        mBrand.setText(article.getBrand());
        mState.setText(staticCollections.getState(article.getClotheCondition()));
        mDescription.setText(article.getDescription());
    }


    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public void successCallback(String tag, Object val) {
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }
}
