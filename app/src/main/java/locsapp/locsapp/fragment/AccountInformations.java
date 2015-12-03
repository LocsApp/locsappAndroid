package locsapp.locsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.fragment.AccountOverviewFragment;
import locsapp.locsapp.R;
import locsapp.locsapp.models.User;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountInformations extends android.support.v4.app.Fragment {
    private HomeActivity mActivity;


    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout;
    private ListView list;

    public static AccountInformations newInstance(int sectionNumber) {
        AccountInformations fragment = new AccountInformations();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_overview);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AccountInformations() {
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

        list = (ListView) view.findViewById(R.id.list);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        User user = mActivity.mUser;

        data.add(newItem("Username", user.mUsername));
        data.add(newItem("Email", user.mEmail));
        data.add(newItem("Recovery Email", user.mEmail2));
        data.add(newItem("Firstname", user.mFirstName));
        data.add(newItem("Lastname", user.mLastName));
        data.add(newItem("Birthdate", user.mBirthdate));
        data.add(newItem("Phone", user.mPhone));
        data.add(newItem("Register Date", user.mRegisterDate));
        data.add(newItem("Last Connection", user.mLastActivity));
        data.add(newItem("Living Address", user.mLivingAddress));
        data.add(newItem("Billing Address", user.mBillingAddress));

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "value"},
                new int[] {android.R.id.text1, android.R.id.text2});

        list.setAdapter(adapter);
    }

    private Map<String, String> newItem(String title, String value) {
        if (value == null) {
            value = "Not Provided";
        }
        Map<String, String> datum = new HashMap<String, String>(2);
        datum.put("title", title);
        datum.put("value", value);
        return datum;
    }

    public void setImage(Bitmap image){
        try {
            //setImageBitmap(image);
        }
        catch (Exception e){
            Log.e(getTag(), e.getMessage());
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

}
