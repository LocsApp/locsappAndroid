package locsapp.locsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import locsapp.locsapp.R;
import locsapp.locsapp.interfaces.MyCallback;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountNotations extends android.support.v4.app.Fragment implements MyCallback{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout = R.layout.fragment_account_notations;
    private static int section;

    public static AccountNotations newInstance(int sectionNumber) {
        AccountNotations fragment = new AccountNotations();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_notations);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public AccountNotations() {
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
