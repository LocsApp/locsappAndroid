package locsapp.locsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.adapter.ShowAdrAdapter;
import locsapp.locsapp.interfaces.DeleteAddress;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.R;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.LivingAddress;
import locsapp.locsapp.models.User;
import locsapp.locsapp.network.InfosUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountInformations extends android.support.v4.app.Fragment
        implements ViewPager.OnPageChangeListener, MyCallback, DeleteAddress {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    private HomeActivity mActivity;
    private FragmentManager fragmentManager;
    private int layout = R.layout.fragment_account_infos;
    private View mView;
    private ListView billingAdr;
    private ListView livingAdr;

    public AccountInformations() {
    }

    public static AccountInformations newInstance(int sectionNumber) {
        AccountInformations fragment = new AccountInformations();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_infos);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
        this.mView = view;
        User user = mActivity.mUser;
        mActivity.accountInformations = this;

        if (user == null) {
            InfosUser infosUser = new InfosUser(mActivity, this);
            infosUser.getUser(mActivity.mToken);
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
        billingAdr = (ListView) mActivity.findViewById(R.id.billingAddress);
        livingAdr = (ListView) mActivity.findViewById(R.id.livingAddress);


        final Button bilButton = (Button) mActivity.findViewById(R.id.billButton);
        bilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (billingAdr.getVisibility() == View.GONE) {
                    billingAdr.setVisibility(View.VISIBLE);
                    bilButton.setText("Hide Billing Address");
                } else {
                    billingAdr.setVisibility(View.GONE);
                    bilButton.setText("Show Billing Address");
                }
            }
        });
        final Button livButton = (Button) mActivity.findViewById(R.id.livButton);
        livButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (livingAdr.getVisibility() == View.GONE) {
                    livingAdr.setVisibility(View.VISIBLE);
                    livButton.setText("Hide Living Address");
                } else {
                    livingAdr.setVisibility(View.GONE);
                    livButton.setText("Show Living Address");
                }
            }
        });
    }

    public void setData(User user) {
        ((TextView) mView.findViewById(R.id.username)).setText(user.mUsername);
        ((TextView) mView.findViewById(R.id.firstname)).setText(user.mFirstName);
        ((TextView) mView.findViewById(R.id.lastname)).setText(user.mLastName);
        ((TextView) mView.findViewById(R.id.email)).setText(user.mEmail);
        ((TextView) mView.findViewById(R.id.phone)).setText(user.mPhone);
        ((TextView) mView.findViewById(R.id.birthdate)).setText(user.mBirthdate);
        ListView livingAdr = ((ListView) mView.findViewById(R.id.livingAddress));
        ShowAdrAdapter livingAdapter = new ShowAdrAdapter(getActivity(), user.mLivingAddress, this, null);
        livingAdr.setAdapter(livingAdapter);
        setListViewHeightBasedOnChildren(livingAdr);
        ListView billingAdr = ((ListView) mView.findViewById(R.id.billingAddress));
        ShowAdrAdapter billingAdapter = new ShowAdrAdapter(getActivity(), user.mBillingAddress, this, null);
        billingAdr.setAdapter(billingAdapter);
        setListViewHeightBasedOnChildren(billingAdr);
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
            InfosUser infosUser = new InfosUser(mActivity, this);
            infosUser.getUser(mActivity.mToken);
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
        switch (tag) {
            case "getUser":
                mActivity.mUser = (User) val;
                setData((User) val);
                Log.d("fsdfsdfsdfsf", "successCallback: CA MARCHE");
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(getActivity(), "CA CHANGE " + position,
                Toast.LENGTH_LONG).show();
        Log.e("CHANGE", " " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void deleteLivingAddress(LivingAddress adr) {
    }
    @Override
    public void deleteBillingAddress(BillingAddress adr) {
    }
}
