package locsapp.locsapp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.SCBaseCategories;
import locsapp.locsapp.models.Search;
import locsapp.locsapp.models.SearchResults;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.network.InfosArticle;

/**
 * Created by Damien on 2/3/2015.
 */

// TODO: 7/6/2016 Add other search params 

public class SearchArticlesFragment extends android.support.v4.app.Fragment implements MyCallback {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    private HomeActivity mActivity;
    private int layout;
    //Search fields items
    private TextView mKeywords;
    private Switch mOnlyTitle;
    private TextView mLabelPosition;
    private TextView mCityPosition;
    private TextView mDistance;
    private TextView mLabelDate;
    private TextView mDateStart;
    private TextView mDateEnd;
    private TextView mLabelCategory;
    private TextView mCategory;
    private TextView mLabelSubCategory;
    private TextView mSubCategory;
    private TextView mLabelBrand;
    private TextView mBrand;
    private TextView mLabelGender;
    private TextView mGender;
    private TextView mLabelSize;
    private TextView mSize;
    private TextView mLabelColor;
    private TextView mColor;
    private TextView mlabelState;
    private TextView mState;
    private TextView mLabelPay;
    private TextView mPay;
    private Switch mCaution;
    private FloatingActionButton mSearchButton;
    private FragmentManager fragmentManager;

    private StaticCollections staticCollections;

    public SearchArticlesFragment() {

    }

    public static SearchArticlesFragment newInstance(int sectionNumber) {
        SearchArticlesFragment fragment = new SearchArticlesFragment();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.search_articles);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        section = sectionNumber;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_articles, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();

        staticCollections = mActivity.staticCollections;
        if (staticCollections == null) {
            staticCollections = new StaticCollections(getContext());
            staticCollections.loadDatas();
            staticCollections.convertToCharSequences();
        }

        // Bind view items
        mKeywords           = (TextView) view.findViewById(R.id.keywords);
        mOnlyTitle          = (Switch) view.findViewById(R.id.titleOnly);
        mLabelPosition      = (TextView) view.findViewById(R.id.labelPosition);
        mCityPosition       = (TextView) view.findViewById(R.id.distanceCity);
        mDistance           = (TextView) view.findViewById(R.id.distance);
        mLabelDate          = (TextView) view.findViewById(R.id.labelDate);
        mDateStart          = (TextView) view.findViewById(R.id.dateStart);
        mDateEnd            = (TextView) view.findViewById(R.id.dateEnd);
        mLabelCategory      = (TextView) view.findViewById(R.id.labelCategory);
        mCategory           = (TextView) view.findViewById(R.id.category);
        mLabelSubCategory   = (TextView) view.findViewById(R.id.labelSubCategory);
        mSubCategory        = (TextView) view.findViewById(R.id.subCategory);
        mLabelBrand         = (TextView) view.findViewById(R.id.labelBrand);
        mBrand              = (TextView) view.findViewById(R.id.brand);
        mLabelGender        = (TextView) view.findViewById(R.id.labelGender);
        mGender             = (TextView) view.findViewById(R.id.gender);
        mLabelSize          = (TextView) view.findViewById(R.id.labelSize);
        mSize               = (TextView) view.findViewById(R.id.size);
        mLabelColor         = (TextView) view.findViewById(R.id.labelColor);
        mColor              = (TextView) view.findViewById(R.id.color);
        mlabelState         = (TextView) view.findViewById(R.id.labelState);
        mState              = (TextView) view.findViewById(R.id.state);
        mLabelPay           = (TextView) view.findViewById(R.id.labelPayMethods);
        mPay                = (TextView) view.findViewById(R.id.payMethods);
        mCaution            = (Switch) view.findViewById(R.id.caution);
        mSearchButton       = (FloatingActionButton) view.findViewById(R.id.search_button);

        mCategory.setText(countOptions(staticCollections.bchkBaseCategories));
        mSubCategory.setText(countOptions(staticCollections.bchkSubCategories));
        mGender.setText(countOptions(staticCollections.bchkGenders));
        mSize.setText(countOptions(staticCollections.bchkSizes));
        mColor.setText(countOptions(staticCollections.bchkColors));
        mState.setText(countOptions(staticCollections.bchkStates));
        mPay.setText(countOptions(staticCollections.bchkPayMethods));

        mLabelPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] items = {"Use my position"};
                showDialog("Use position", "pos", items, null);
                Toast.makeText(getContext(), "Position", Toast.LENGTH_LONG).show();
            }
        });
        mLabelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Date", Toast.LENGTH_LONG).show();
            }
        });
        mLabelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Categories", "cat", staticCollections.cSBaseCategories, staticCollections.bchkBaseCategories);
            }
        });
        mLabelSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Sub-Categories", "subcat", staticCollections.cSSubCategories, staticCollections.bchkSubCategories);
            }
        });
        mLabelBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Brand", Toast.LENGTH_LONG).show();
            }
        });
        mLabelGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Genders", "genders", staticCollections.cSGenders, staticCollections.bchkGenders);
            }
        });
        mLabelSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Sizes", "sizes", staticCollections.cSSizes, staticCollections.bchkSizes);
            }
        });
        mLabelColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Colors", "colors", staticCollections.cSColors, staticCollections.bchkColors);
            }
        });
        mlabelState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Clothe State", "state", staticCollections.cSStates, staticCollections.bchkStates);

            }
        });
        mLabelPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Payment Methods", "pay", staticCollections.cSPayMethods, staticCollections.bchkPayMethods);

            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeSearch();
            }
        });
    }

    public void makeSearch() {
        Search params = new Search(staticCollections, mKeywords.getText().toString(), 1, 20);
        InfosArticle infosArticle = new InfosArticle(getContext(), this);
        infosArticle.makeSearch(mActivity.mToken, params);
    }

    private boolean[] updateSelected(boolean[] old, List<Integer> selected, Integer size) {
        if (old == null) {
            old = new boolean[size];
        }
        for (int i = 0; i < old.length; i++) {
            if (selected.contains(i)) {
                old[i] = true;
            }
            else {
                old[i] = false;
            }
        }
        return old;
    }

    public void showDialog(String title, final String tag, final CharSequence[] items, final boolean[] checked) {
        final List<Integer> seletedItems = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        final Integer size = items.length;
        if (checked != null) {
            for (int i = 0; i < checked.length; i++) {
                if (checked[i]) {
                    seletedItems.add(i);
                }
            }
        }

        builder.setMultiChoiceItems(items, checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // indexSelected contains the index of item (of which checkbox checked)
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String value = "( " + seletedItems.size() + " )";
                        if (seletedItems.size() == 0) {
                            value = "all";
                        }
                        Log.d("onClick: ", seletedItems.toString());
                        switch (tag) {
                            case "cat":
                                staticCollections.bchkBaseCategories = updateSelected(checked, seletedItems, size);
                                mCategory.setText(value);
                                break;
                            case "subcat":
                                staticCollections.bchkSubCategories = updateSelected(checked, seletedItems, size);
                                mSubCategory.setText(value);
                                break;
                            case "genders":
                                staticCollections.bchkGenders = updateSelected(checked, seletedItems, size);
                                mGender.setText(value);
                                break;
                            case "sizes":
                                staticCollections.bchkSizes = updateSelected(checked, seletedItems, size);
                                mSize.setText(value);
                                break;
                            case "colors":
                                staticCollections.bchkColors = updateSelected(checked, seletedItems, size);
                                mColor.setText(value);
                                break;
                            case "state":
                                staticCollections.bchkStates = updateSelected(checked, seletedItems, size);
                                mState.setText(value);
                                break;
                            case "pay":
                                staticCollections.bchkPayMethods = updateSelected(checked, seletedItems, size);
                                mPay.setText(value);
                                break;
                        }
                    }
                })
                .setNegativeButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String value = "all";
                        seletedItems.clear();
                        switch (tag) {
                            case "cat":
                                staticCollections.bchkBaseCategories = updateSelected(checked, seletedItems, size);
                                mCategory.setText(value);
                                break;
                            case "subcat":
                                staticCollections.bchkSubCategories = updateSelected(checked, seletedItems, size);
                                mSubCategory.setText(value);
                                break;
                            case "genders":
                                staticCollections.bchkGenders = updateSelected(checked, seletedItems, size);
                                mGender.setText(value);
                                break;
                            case "sizes":
                                staticCollections.bchkSizes = updateSelected(checked, seletedItems, size);
                                mSize.setText(value);
                                break;
                            case "colors":
                                staticCollections.bchkColors = updateSelected(checked, seletedItems, size);
                                mColor.setText(value);
                                break;
                            case "state":
                                staticCollections.bchkStates = updateSelected(checked, seletedItems, size);
                                mState.setText(value);
                                break;
                            case "pay":
                                staticCollections.bchkPayMethods = updateSelected(checked, seletedItems, size);
                                mPay.setText(value);
                                break;
                        }
                    }
                });

        Dialog dialog = builder.create();//AlertDialog dialog; create like this outside onClick
        dialog.show();
    }

    public void showResults() {
        Log.d("showResults: ", "Start fragment");
        fragmentManager.beginTransaction()
                .replace(R.id.container, SearchResult.newInstance(section))
                .addToBackStack( "search" )
                .commit();
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
        switch (tag) {
            case "makeSearch":
                mActivity.staticCollections = staticCollections;
                mActivity.mArticles = (SearchResults) val;
                showResults();
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }

/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
    }*/

    private String countOptions(boolean[] options) {
        String ret = "all";
        Integer count = 0;
        if (options != null) {
            for (int i = 0; i < options.length; i++) {
                if (options[i]) {
                    count += 1;
                }
            }
        }
        if (count != 0) {
            ret = "( " + count.toString() + " )";
        }
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
