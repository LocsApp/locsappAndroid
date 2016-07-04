package locsapp.locsapp.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.adapter.ShowAdrAdapter;
import locsapp.locsapp.interfaces.DeleteAddress;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.LivingAddress;
import locsapp.locsapp.models.SCBaseCategories;
import locsapp.locsapp.models.User;
import locsapp.locsapp.models.UserPut;
import locsapp.locsapp.network.InfosUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountInformationsUpdate extends android.support.v4.app.Fragment implements MyCallback, DeleteAddress, DatePickerFragment.DateListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    EditText mFirstname;
    EditText mLastname;
    EditText mBirthdate;
    EditText mPhone;
    ListView editbillingAdr;
    ListView editlivingAdr;
    private HomeActivity mActivity;
    private int layout = R.layout.fragment_account_infos_update;
    private FragmentManager fragmentManager;


    public AccountInformationsUpdate() {
    }

    public static AccountInformationsUpdate newInstance(int sectionNumber) {
        AccountInformationsUpdate fragment = new AccountInformationsUpdate();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_infos_update);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();
        User user = mActivity.mUser;

        mFirstname = (EditText) view.findViewById(R.id.firstname);
        mLastname = (EditText) view.findViewById(R.id.lastname);
        mBirthdate = (EditText) view.findViewById(R.id.birthdate);
        mPhone = (EditText) view.findViewById(R.id.phone);

        editlivingAdr = (ListView) view.findViewById(R.id.editlivingAddress);
        editbillingAdr = (ListView) view.findViewById(R.id.editbillingAddress);

        mFirstname.setText(user.mFirstName);
        mLastname.setText(user.mLastName);
        mBirthdate.setText(user.mBirthdate);
        mPhone.setText(user.mPhone);

        mBirthdate.setKeyListener(null);
        ListView livingAdr = ((ListView) view.findViewById(R.id.editlivingAddress));
        ShowAdrAdapter livingAdapter = new ShowAdrAdapter(getActivity(), user.mLivingAddress, this, "living_address");
        livingAdr.setAdapter(livingAdapter);
        setListViewHeightBasedOnChildren(livingAdr);
        ListView billingAdr = ((ListView) view.findViewById(R.id.editbillingAddress));
        ShowAdrAdapter billingAdapter = new ShowAdrAdapter(getActivity(), user.mBillingAddress, this, "billing_address");
        billingAdr.setAdapter(billingAdapter);
        setListViewHeightBasedOnChildren(billingAdr);


        final Button editbilButton = (Button) mActivity.findViewById(R.id.editbillButton);
        editbilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editbillingAdr.getVisibility() == View.GONE) {
                    editbillingAdr.setVisibility(View.VISIBLE);
                    editbilButton.setText("Hide Billing Address");
                } else {
                    editbillingAdr.setVisibility(View.GONE);
                    editbilButton.setText("Show Billing Address");
                }
            }
        });
        final Button editlivButton = (Button) mActivity.findViewById(R.id.editlivButton);
        editlivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editlivingAdr.getVisibility() == View.GONE) {
                    editlivingAdr.setVisibility(View.VISIBLE);
                    editlivButton.setText("Hide Living Address");
                } else {
                    editlivingAdr.setVisibility(View.GONE);
                    editlivButton.setText("Show Living Address");
                }
            }
        });

        final Button addAddressBtn = (Button) mActivity.findViewById(R.id.addAddress);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout addAddressForm = (LinearLayout) mActivity.findViewById(R.id.formAddAddress);
                if (addAddressForm.getVisibility() == View.GONE){
                    addAddressForm.setVisibility(View.VISIBLE);
                    addAddressBtn.setText("Hide Form");
                    (mActivity.findViewById(R.id.addressAlias)).requestFocus();
                }
                else{
                    addAddressForm.setVisibility(View.GONE);
                    addAddressBtn.setText("Add an Address");
                }
            }
        });

        final Button addAddress = (Button) mActivity.findViewById(R.id.btnAddAddress);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnAddress();
            }
        });

        Button changeButton = (Button) view.findViewById(R.id.btnUpdate);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfos();
            }
        });

        mBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }

        });
    }

    public void showDialog() {
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.setListener(this, mBirthdate.getText().toString());
        dpf.setTitle("Select Birthdate");
        dpf.show(getActivity().getSupportFragmentManager(), "Birthdate");

    }

    public void addAnAddress() {
        String firstname = ((EditText) mActivity.findViewById(R.id.addressFirstname)).getText().toString();
        String lastname = ((EditText) mActivity.findViewById(R.id.addressLastname)).getText().toString();
        String alias = ((EditText) mActivity.findViewById(R.id.addressAlias)).getText().toString();
        String address = ((EditText) mActivity.findViewById(R.id.addressAdress)).getText().toString();
        String code = ((EditText) mActivity.findViewById(R.id.addressCode)).getText().toString();
        String city = ((EditText) mActivity.findViewById(R.id.addressCity)).getText().toString();
        CheckBox livingCheck = (CheckBox) mActivity.findViewById(R.id.addressIsLiving);
        CheckBox billingCheck = (CheckBox) mActivity.findViewById(R.id.addressIsBilling);
        InfosUser infosUser = new InfosUser(mActivity, this);
        if (livingCheck.isChecked()) {
            LivingAddress livingAddress = new LivingAddress(alias, address, lastname, firstname, code, city);
            infosUser.addLivingAddress(mActivity.mToken, mActivity.mUser.mId.toString(), livingAddress);
        }
        if (billingCheck.isChecked()) {
            BillingAddress billingAddress = new BillingAddress(alias, address, lastname, firstname, code, city);
            infosUser.addBillingAddress(mActivity.mToken, mActivity.mUser.mId.toString(), billingAddress);
        }
    }

    private void updateInfos() {
        UserPut params = new UserPut(mFirstname.getText().toString(),
                mLastname.getText().toString(),
                mBirthdate.getText().toString(),
                mPhone.getText().toString());
        InfosUser infos = new InfosUser(mActivity, this);
        infos.updateUser(mActivity.mToken, params, this);
    }

    public void successCallback(User user) {
        mActivity.mUser = user;
        mActivity.accountInformations.setData(user);
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void updateUser() {
        InfosUser infosUser = new InfosUser(mActivity, this);
        infosUser.getUser(mActivity.mToken);
        Log.e("successCallback: ", "ok2");
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "deleteLivingAddress":
                updateUser();
                break;
            case "deleteBillingAddress":
                updateUser();
                break;
            case "addLivingAddress":
                Log.e("successCallback: ", "ok1");
                updateUser();
                break;
            case "addBillingAddress":
                updateUser();
                break;
            case "getUser":
                mActivity.mUser = (User) val;
                mActivity.accountInformations.setData((User) val);
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }

    @Override
    public void deleteLivingAddress(LivingAddress adr) {
        InfosUser infos = new InfosUser(mActivity, this);
        infos.deleteLivingAddress(mActivity.mToken, mActivity.mUser.mId.toString(), adr);
    }

    @Override
    public void deleteBillingAddress(BillingAddress adr) {
        InfosUser infos = new InfosUser(mActivity, this);
        infos.deleteBillingAddress(mActivity.mToken, mActivity.mUser.mId.toString(), adr);
    }

    @Override
    public void returnDate(String date) {
        Log.d("returnDate: ", date);
        mBirthdate.setText(date);
    }
}
