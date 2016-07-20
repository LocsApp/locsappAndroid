package locsapp.locsapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.adapter.QuestionsAdapter;
import locsapp.locsapp.interfaces.DateCallback;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.AskQuestion;
import locsapp.locsapp.models.Question;
import locsapp.locsapp.models.StaticCollections;
import locsapp.locsapp.models.User;
import locsapp.locsapp.network.InfosArticle;
import locsapp.locsapp.network.InfosUser;


/**
 * Created by Damien on 2/3/2015.
 */

public class AccountArticles extends android.support.v4.app.Fragment implements MyCallback, DateCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int layout = R.layout.article_details;
    private Article article;
    private static int section;
    private HomeActivity mActivity;
    private FragmentManager fragmentManager;
    private StaticCollections staticCollections;
    private Question mQuestion;

    private TextView mTitle;
    private TextView mPay;
    private TextView mAuthor;
    private TextView mPrice;
    private TextView mCategory;
    private TextView mSubCategory;
    private TextView mGender;
    private TextView mSize;
    private TextView mColor;
    private TextView mBrand;
    private TextView mState;
    private TextView mDescription;
    private TextView mDateStart;
    private TextView mDateEnd;
    private ListView mQuestions;
    private ImageView mImage;

    private String m_Text = "";

    private Button mAskQuestion;
    private Button mButtonDemand;

    public static AccountArticles newInstance(int sectionNumber) {
        AccountArticles fragment = new AccountArticles();
        Bundle args = new Bundle();
        fragment.setLayout(R.layout.article_details);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        section = sectionNumber;
        return fragment;
    }

    public AccountArticles() {
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
        staticCollections = mActivity.staticCollections;
        article = mActivity.mArticle;

        mDateStart = (TextView) view.findViewById(R.id.dateStart);
        mDateEnd = (TextView) view.findViewById(R.id.dateEnd);
        mTitle = (TextView) view.findViewById(R.id.title);
        mAuthor = (TextView) view.findViewById(R.id.author);
        mPrice = (TextView) view.findViewById(R.id.price);
        mPay = (TextView) view.findViewById(R.id.payMethods);
        mCategory = (TextView) view.findViewById(R.id.category);
        mSubCategory = (TextView) view.findViewById(R.id.subCategory);
        mGender = (TextView) view.findViewById(R.id.gender);
        mSize = (TextView) view.findViewById(R.id.size);
        mColor = (TextView) view.findViewById(R.id.color);
        mBrand = (TextView) view.findViewById(R.id.brand);
        mState = (TextView) view.findViewById(R.id.state);
        mDescription = (TextView) view.findViewById(R.id.description);
        mButtonDemand = (Button) view.findViewById(R.id.button_demand);
        mQuestions = (ListView) view.findViewById(R.id.questions);
        mAskQuestion = (Button) view.findViewById(R.id.button_ask_question);
        mImage = (ImageView) view.findViewById(R.id.images);

        mAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Your question");

                final EditText input = new EditText(mActivity);
                input.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                builder.setView(input);

                builder.setPositiveButton("Ask", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().length() > 5) {
                            mQuestion = new Question(null, mActivity.mUser.mId, input.getText().toString(), mActivity.mUser.mUsername);
                            askQuestion(input.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        mButtonDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptDemand();
            }
        });

        mDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mDateStart.getText().toString(), "start", "Select Start");
            }
        });
        mDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mDateEnd.getText().toString(), "end", "Select End");
            }
        });

        if (article == null) {
            InfosArticle infosArticle = new InfosArticle(mActivity, this);
            infosArticle.getArticle(mActivity.mToken, mActivity.mArticleId);
        }
        else {
            setDatas();
        }
    }

    public void askQuestion(String content) {
        InfosArticle infosArticle = new InfosArticle(mActivity, this);
        infosArticle.askQuestion(mActivity.mToken, new AskQuestion(article.getId(), content));
    }

    private void attemptDemand() {
        boolean valid = true;
        if (mDateStart.getText().toString().equals("Set Start")) {
            valid = false;
        }
        if (mDateEnd.getText().toString().equals("Set End")) {
            valid = false;
        }

        if (valid) {

        }
    }

    public void showDialog(String val, String tag, String title) {
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.setListener(this, val, tag);
        dpf.setTitle(title);
        if (!mDateStart.getText().toString().equals("Set Start")){
            dpf.setMinDate(mDateStart.getText().toString());
        }
        else {
            dpf.setMinDate(article.getDateStart());
        }
        dpf.setMaxDate(article.getDateEnd());
        dpf.show(getActivity().getSupportFragmentManager(), title);
    }

    public void setDatas(){
        mTitle.setText(article.getTitle());
        mAuthor.setText(article.getAuthor().toString());
        mPrice.setText(article.getPrice().toString() + " €");
        mPay.setText(staticCollections.getPayMethods(article.getPayMethods()));
        mCategory.setText(staticCollections.getCategory(article.getCategory()));
        mSubCategory.setText(staticCollections.getSubCategory(article.getSubCategory()));
        mGender.setText(staticCollections.getGender(article.getGender()));
        mSize.setText(staticCollections.getSize(article.getSize()));
        mColor.setText(staticCollections.getColorValue(article.getColor()));
        try {
            mColor.setTextColor(Color.parseColor(staticCollections.getColorValue(article.getColor())));
        } catch (Exception e) {

        }
        mBrand.setText(article.getBrand());
        mState.setText(staticCollections.getState(article.getClotheCondition()));
        mDescription.setText(article.getDescription());
        if (article.getQuestions() != null && article.getQuestions().size() != 0) {
            mQuestions.setAdapter(new QuestionsAdapter(mActivity, article.getQuestions()));
            setListViewHeightBasedOnChildren(mQuestions);
        }
        Picasso.with(mActivity)
            .load(mActivity.getResources().getString(R.string.api_url) + article.getThumbnail())
            .placeholder(R.drawable.blank2)
            .error(R.drawable.blank2)
            .resize(300, 300)
            .into(mImage);
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


    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "getArticle":
                mActivity.mArticle = (Article) val;
                article = (Article) val;
                setDatas();
                break;
            case "askQuestion":
                if (article.questions == null) {
                    article.questions = new ArrayList<>();
                }
                article.questions.add(mQuestion);
                setDatas();
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }

    @Override
    public void dateSetCallback(String tag, String date) {
        switch (tag) {
            case "start":
                mDateStart.setText(date);
                showDialog(mDateEnd.getText().toString(), "end", "Select End");
                break;
            case "end":
                mDateEnd.setText(date);
                break;
        }
    }
}
