package locsapp.locsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Damien on 2/3/2015.
 */

public class AccountOverviewFragment extends android.support.v4.app.Fragment {
    private HomeActivity mActivity;


    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout;
    private ListView list;

    public static AccountOverviewFragment newInstance(int sectionNumber) {
        AccountOverviewFragment fragment = new AccountOverviewFragment();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_account_overview);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
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
        list = (ListView) view.findViewById(R.id.list);
        String[] values = new String[] { "My Informations", "My Articles", "My Orders",
                "My Messages" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
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
