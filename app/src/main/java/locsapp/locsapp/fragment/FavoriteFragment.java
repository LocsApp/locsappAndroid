package locsapp.locsapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.adapter.ArticlesOverviewAdapter;
import locsapp.locsapp.adapter.FavoritesOverviewAdapter;
import locsapp.locsapp.interfaces.ArticleOverviewAdapterCallback;
import locsapp.locsapp.interfaces.FavoritesOverviewAdapterCallback;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.Favorites;
import locsapp.locsapp.models.IdArticle;
import locsapp.locsapp.models.SearchResults;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.network.InfosArticle;

/**
 * Created by Damien on 2/3/2015.
 */

public class FavoriteFragment extends android.support.v4.app.Fragment
        implements MyCallback, FavoritesOverviewAdapterCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    private HomeActivity mActivity;
    private int layout;
    private FragmentManager fragmentManager;
    private StaticCollections staticCollections;
    private ListView content;
    private Favorites favorites;

    public FavoriteFragment() {

    }

    public static FavoriteFragment newInstance(int sectionNumber) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_favorites);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        section = sectionNumber;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();
        staticCollections = mActivity.staticCollections;
        favorites = mActivity.mFavorites;
        if (favorites != null && favorites.articles != null && favorites.articles.size() > 0) {
            content = (ListView) view.findViewById(R.id.list_results);
            FavoritesOverviewAdapter favoritesOverviewAdapter = new FavoritesOverviewAdapter(getActivity(), favorites.articles, this);
            content.setAdapter(favoritesOverviewAdapter);
        }
        else {
            view.findViewById(R.id.no_favorites).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            default:
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showArticle(String id) {
        mActivity.mArticle = null;
        mActivity.mArticleId = id;
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountArticles.newInstance(section))
                .commit();
    }

    @Override
    public void deleteFavorite(String id) {
        InfosArticle infosArticle = new InfosArticle(mActivity, this);
        infosArticle.deleteFavorite(mActivity.mToken, new IdArticle(id));
    }
}
