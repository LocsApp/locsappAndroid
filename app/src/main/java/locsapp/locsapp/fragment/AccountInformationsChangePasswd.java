package locsapp.locsapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.User;
import locsapp.locsapp.network.ConnectionUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountInformationsChangePasswd extends android.support.v4.app.Fragment implements MyCallback {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    TextView mOldPasswd;
    TextView mNewPasswd1;
    TextView mNewPasswd2;
    private HomeActivity mActivity;
    private int layout;
    private FragmentManager fragmentManager;

    public AccountInformationsChangePasswd() {
    }

    public static AccountInformationsChangePasswd newInstance(int sectionNumber) {
        AccountInformationsChangePasswd fragment = new AccountInformationsChangePasswd();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_change_passwd);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
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

        Button backButton = (Button) mActivity.findViewById(R.id.btnChangeBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TEST", "onClick: BACK");
                accountInfos();
            }
        });
        Button buttonChange = (Button) mActivity.findViewById(R.id.btnChangePasswd);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TEST", "onClick: Submit");
                tryChange();
            }
        });
        mOldPasswd = (TextView) view.findViewById(R.id.pass_old);
        mNewPasswd1 = (TextView) view.findViewById(R.id.pass_new1);
        mNewPasswd2 = (TextView) view.findViewById(R.id.pass_new2);
    }

    private void tryChange() {
        mOldPasswd.setError(null);
        mNewPasswd1.setError(null);
        mNewPasswd2.setError(null);

        boolean cancel = false;
        View focusView = null;

        String oldPasswd = mOldPasswd.getText().toString();
        String newPasswd1 = mNewPasswd1.getText().toString();
        String newPasswd2 = mNewPasswd2.getText().toString();

        if (TextUtils.isEmpty(newPasswd2)) {
            mNewPasswd2.setError(getString(R.string.error_field_required));
            focusView = mNewPasswd2;
            cancel = true;
        }
        if (TextUtils.isEmpty(newPasswd1)) {
            mNewPasswd2.setError(getString(R.string.error_field_required));
            focusView = mNewPasswd2;
            cancel = true;
        }
        if (TextUtils.isEmpty(oldPasswd)) {
            mOldPasswd.setError(getString(R.string.error_field_required));
            focusView = mOldPasswd;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        else {
            ConnectionUser coUser = new ConnectionUser(mActivity);
            coUser.changePassword(mActivity.mToken, oldPasswd, newPasswd1, newPasswd2, this);
        }

    }

    public void accountInfos() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, AccountInformations.newInstance(section))
                .commit();
    }

    public void successCallback(String result) {
        accountInfos();
    }

    public void errorCallback(String error){
        Log.d("Change", "errorCallback: " + error);
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

    @Override
    public void successCallback(String tag, Object val) {

    }

    @Override
    public void errorCallback(String tag, Object val) {

    }
}
