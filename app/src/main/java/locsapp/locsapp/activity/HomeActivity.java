package locsapp.locsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import locsapp.locsapp.fragment.AccountInformations;
import locsapp.locsapp.fragment.AccountOverviewFragment;
import locsapp.locsapp.fragment.FavoriteFragment;
import locsapp.locsapp.fragment.SearchArticlesFragment;
import locsapp.locsapp.fragment.NavigationDrawerFragment;
import locsapp.locsapp.R;
import locsapp.locsapp.fragment.TabhostFragment;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.Favorites;
import locsapp.locsapp.models.SearchResults;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.models.User;
import locsapp.locsapp.network.InfosArticle;
import locsapp.locsapp.network.InfosUser;

// TODO: 7/6/2016 Get user from preference
public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, MyCallback {

    public String mToken;
    public User mUser;
    public StaticCollections staticCollections;
    public SearchResults mArticles;
    public Article mArticle;
    public String mArticleId;
    public Favorites mFavorites;
    public AccountInformations accountInformations;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        mToken = intent.getStringExtra("token");

        mUser = null;
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        InfosUser infosUser = new InfosUser(this, this);
        infosUser.getUser(mToken);

        InfosArticle infosArticle = new InfosArticle(this, this);
        infosArticle.getFavorites(mToken);

        if (staticCollections == null) {
            staticCollections = new StaticCollections(this);
            staticCollections.loadDatas();
            staticCollections.convertToCharSequences();
        }


        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void refreshFav() {
        InfosArticle infosArticle = new InfosArticle(this, this);
        infosArticle.getFavorites(mToken);
    }

    private void clearBackStack(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SearchArticlesFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                clearBackStack(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TabhostFragment.newInstance(position + 1))
                        .commit();
                break;
            case 2:
                clearBackStack(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FavoriteFragment.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

/* To handle Back button

    @Override
    public void onBackPressed() {

    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "getUser":
                mUser = (User) val;
                break;
            case "getFavorites":
                mFavorites = (Favorites) val;
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }
}
