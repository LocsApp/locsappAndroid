package locsapp.locsapp;

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

public class HomeFragment extends android.support.v4.app.Fragment {
    private HomeActivity mActivity;
    private TextView mErrorView;
    private TextView mLoadingView;
    private TextView mFullName;
    private TextView mLogin;
    private TextView mLog;
    private ImageView mImageLogin;
    private ListView mMessageList;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout;

    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.fragment_home);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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
    }

    public void setImage(Bitmap image){
        try {
            mImageLogin.setImageBitmap(image);
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
