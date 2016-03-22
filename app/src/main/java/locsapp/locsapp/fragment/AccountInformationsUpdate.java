package locsapp.locsapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.models.User;
import locsapp.locsapp.models.UserPut;
import locsapp.locsapp.network.InfosUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountInformationsUpdate extends android.support.v4.app.Fragment {
    private HomeActivity mActivity;
    private static int section;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout = R.layout.fragment_account_infos_update;
    private FragmentManager fragmentManager;

    EditText mFirstname;
    EditText mLastname;
    EditText mBirthdate;
    EditText mPhone;

    public static AccountInformationsUpdate newInstance(int sectionNumber) {
        AccountInformationsUpdate fragment = new AccountInformationsUpdate();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_infos_update);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public AccountInformationsUpdate() {
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

        mFirstname.setText(user.mFirstName);
        mLastname.setText(user.mLastName);
        mBirthdate.setText(user.mBirthdate);
        mPhone.setText(user.mPhone);

        Button changeButton = (Button) view.findViewById(R.id.btnUpdate);
        Button backButton = (Button) view.findViewById(R.id.btnBack);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfos();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountInfos();
            }
        });
    }

    private void updateInfos() {
        UserPut params = new UserPut(mFirstname.getText().toString(),
                mLastname.getText().toString(),
                mBirthdate.getText().toString(),
                mPhone.getText().toString());
        InfosUser infos = new InfosUser(mActivity);
        infos.updateUser(mActivity.mToken, params, this);
    }

    public void accountInfos() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountInformations.newInstance(section))
                .commit();
    }

/*    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }*/

    public void successCallback(User user) {
        mActivity.mUser = user;
        accountInfos();
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

}
