package locsapp.locsapp.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.adapter.SampleFragmentPagerAdapter;
import locsapp.locsapp.interfaces.MyCallback;

/**
 * Created by Damien on 3/3/2016.
 */

public class TabhostFragment extends Fragment implements MyCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;

    public static TabhostFragment newInstance(int sectionNumber) {
        TabhostFragment fragment = new TabhostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_manager_fragment, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Edit Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount()));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void successCallback(String tag, Object val) {

    }

    @Override
    public void errorCallback(String tag, Object val) {

    }
}