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
import locsapp.locsapp.adapter.SampleFragmentPagerAdapter;

/**
 * Created by Damien on 3/2/2016.
 */
public class TabhostFragment extends Fragment {

    ActionBar actionBar;
    ViewPager viewPager;
    FragmentPagerAdapter ft;
    private FragmentTabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_manager_fragment, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}