package locsapp.locsapp.fragment;

import android.app.Activity;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import locsapp.locsapp.Location.GetPositionFromAddressName;
import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.adapter.ArticlesOverviewAdapter;
import locsapp.locsapp.interfaces.ArticleOverviewAdapterCallback;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.IdArticle;
import locsapp.locsapp.models.SearchResults;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.network.InfosArticle;

/**
 * Created by Damien on 2/3/2015.
 */

public class SearchResult extends android.support.v4.app.Fragment
        implements MyCallback, ArticleOverviewAdapterCallback, GetPositionFromAddressName.LocationCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    private HomeActivity mActivity;
    private int layout;
    private FragmentManager fragmentManager;
    private StaticCollections staticCollections;
    private SearchResults searchResults;
    private ListView content;
    private ArticlesOverviewAdapter articlesOverviewAdapter;

    public SearchResult() {

    }

    public static SearchResult newInstance(int sectionNumber) {
        SearchResult fragment = new SearchResult();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.search_articles);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        section = sectionNumber;
        fragment.setArguments(args);
        return fragment;
    }

    public void getPosition(String address) {
        GetPositionFromAddressName getPositionFromAddressName = new GetPositionFromAddressName(this);
        getPositionFromAddressName.execute(address);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();
        staticCollections = mActivity.staticCollections;
        searchResults = mActivity.mArticles;

        getPosition("3 rue de thann 68700 Cernay");
        if (searchResults != null && searchResults.articles != null && searchResults.articles.size() > 0) {
            content = (ListView) view.findViewById(R.id.list_results);
            articlesOverviewAdapter = new ArticlesOverviewAdapter(getActivity(), searchResults.articles, this);
            content.setAdapter(articlesOverviewAdapter);
            content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("POSITION ", "" + position + " " + id);
                }
            });
        }
        else {
            view.findViewById(R.id.no_search_results).setVisibility(View.VISIBLE);
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
            case "addFavorite":
                mActivity.refreshFav();
                articlesOverviewAdapter.notifyDataSetChanged();
                break;
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
    public void showArticle(Article article) {
        Log.d("showArticle", "");
        mActivity.mArticle = article;
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountArticles.newInstance(section))
                .addToBackStack("searchresult")
                .commit();
    }

    @Override
    public void addFavorite(String id) {
        InfosArticle infosArticle = new InfosArticle(mActivity, this);
        infosArticle.addFavorite(mActivity.mToken, new IdArticle(id));
    }

    @Override
    public void location(Address address) {
        Log.d("location: ", address.toString());
    }
}
