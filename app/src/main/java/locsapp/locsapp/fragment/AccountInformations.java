package locsapp.locsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.activity.MyCallback;
import locsapp.locsapp.fragment.AccountOverviewFragment;
import locsapp.locsapp.R;
import locsapp.locsapp.models.User;
import locsapp.locsapp.network.ConnectionUser;
import locsapp.locsapp.network.InfosUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountInformations extends android.support.v4.app.Fragment implements MyCallback{
    private HomeActivity mActivity;
    private FragmentManager fragmentManager;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout = R.layout.fragment_account_infos;
    private static int section;
    private View mView;

    public static AccountInformations newInstance(int sectionNumber) {
        AccountInformations fragment = new AccountInformations();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_infos);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public AccountInformations() {
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
        //list = (ListView) view.findViewById(R.id.list);
        this.mView = view;
        User user = mActivity.mUser;



        if (user == null) {
            InfosUser infosUser = new InfosUser(mActivity);
            infosUser.getUserTest(mActivity.mToken, this);
        }
        else {
            setData(user);
        }

        Button changeButton = (Button) mActivity.findViewById(R.id.btnChange);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountChangePasswd();
            }
        });
        Button updateButton = (Button) mActivity.findViewById(R.id.btnEdit);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountUpdate();
            }
        });

    }

    private void setData(User user) {
        ((TextView) mView.findViewById(R.id.username)).setText(user.mUsername);
        ((TextView) mView.findViewById(R.id.firstname)).setText(user.mFirstName);
        ((TextView) mView.findViewById(R.id.lastname)).setText(user.mLastName);
        ((TextView) mView.findViewById(R.id.email)).setText(user.mEmail);
        ((TextView) mView.findViewById(R.id.phone)).setText(user.mPhone);
        ((TextView) mView.findViewById(R.id.birthdate)).setText(user.mBirthdate);
//        ((TextView) mView.findViewById(R.id.address)).setText(user.mLivingAddress);

/*        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

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

        list.setAdapter(adapter);*/
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

    public void accountUpdate() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountInformationsUpdate.newInstance(section))
                .commit();
    }

    public void accountChangePasswd() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountInformationsChangePasswd.newInstance(section))
                .commit();
    }

    public void successCallback(String tag, User user) {
        setData(user);
        mActivity.mUser = user;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            InfosUser infosUser = new InfosUser(mActivity);
            infosUser.getUser(mActivity.mToken, this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }*/

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public void successCallback(String tag, Object val) {
        Log.d("", "successCallback: CA MARCHE");
        Toast.makeText(getActivity(), "CA MARRRRCHE",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }
}
