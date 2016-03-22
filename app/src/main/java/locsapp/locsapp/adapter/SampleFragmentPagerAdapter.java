package locsapp.locsapp.adapter;

/**
 * Created by Damien on 3/3/2016.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import locsapp.locsapp.fragment.AccountInformations;
import locsapp.locsapp.fragment.AccountInformationsUpdate;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"Profile", "Edit Profile", "Acticles", "Notation"};
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
                return new AccountInformations();
            case 3:
                return new AccountInformationsUpdate();
            default:
                break;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}