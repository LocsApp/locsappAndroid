package locsapp.locsapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.network.ConnectionUser;

/**
 * Created by Damien on 2/3/2015.
 */

public class AccountOverviewFragment extends android.support.v4.app.Fragment {
    private HomeActivity mActivity;
    private FragmentManager fragmentManager;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    private int layout;
    private ListView list;
    private static Context activity;

    public static AccountOverviewFragment newInstance(int sectionNumber, Context context) {
        AccountOverviewFragment fragment = new AccountOverviewFragment();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_overview);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        activity = context;
        return fragment;
    }

    public AccountOverviewFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(this.layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();
        list = (ListView) view.findViewById(R.id.list);
        String[] values = new String[] { "My Informations", "My Articles", "My Orders",
                "My Messages" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        accountInfos();
                        break;
                }
            }
        });
        AccountInformations infos = new AccountInformations();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void accountInfos() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountInformations.newInstance(section))
                .commit();
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

}
