package locsapp.locsapp.adapter;

/**
 * Created by Damien on 3/3/2016.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import locsapp.locsapp.fragment.AccountArticles;
import locsapp.locsapp.fragment.AccountInformations;
import locsapp.locsapp.fragment.AccountInformationsUpdate;
import locsapp.locsapp.fragment.AccountNotations;


public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SampleFragmentPagerAdapter(FragmentManager fm,int numTabs) {
        super(fm);
        this.mNumOfTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AccountInformations();
            case 1:
                return new AccountInformationsUpdate();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

/*
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Profile", "Edit Profile", "InfosArticle", "Notation"};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AccountInformations();
            case 1:
                return new AccountInformationsUpdate();
            case 2:
                return new AccountArticles();
            case 3:
                return new AccountNotations();
            default:
                break;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}*/