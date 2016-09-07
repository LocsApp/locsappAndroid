package locsapp.locsapp.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import locsapp.locsapp.R;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.network.InfosArticle;

public class StaticCollections implements MyCallback{

    public SCBaseCategories scBaseCategories = null;
    public SCSubCategories scSubCategories = null;
    public SCColors scColors = null;
    public SCGenders scGenders = null;
    public SCSizes scSizes = null;
    public SCStates scStates = null;
    public SCPayMethods scPayMethods = null;
    public CharSequence[] cSBaseCategories;
    public CharSequence[] cSSubCategories;
    public CharSequence[] cSColors;
    public CharSequence[] cSGenders;
    public CharSequence[] cSSizes;
    public CharSequence[] cSStates;
    public CharSequence[] cSPayMethods;
    public String chkBaseCategories = null;
    public String chkSubCategories = null;
    public String chkColors = null;
    public String chkGenders = null;
    public String chkSizes = null;
    public String chkStates = null;
    public String chkPayMethods = null;
    public boolean[] bchkBaseCategories = null;
    public boolean[] bchkSubCategories = null;
    public boolean[] bchkColors = null;
    public boolean[] bchkGenders = null;
    public boolean[] bchkSizes = null;
    public boolean[] bchkStates = null;
    public boolean[] bchkPayMethods = null;
    private MyCallback callback;
    private Context context;
    private InfosArticle infosArticle;

    public StaticCollections(Context context) {
        this.context = context;
    }

    public String getColorValue(String id) {
        if (scColors != null) {
            for (int i = 0; i < scColors.mColors.size(); i++) {
                if (id.equals(scColors.mColors.get(i).getId())) {
                    return (scColors.mColors.get(i).getCode());
                }
            }
        }
        return "";
    }
    public String getColorName(String id) {
        if (scColors != null) {
            for (int i = 0; i < scColors.mColors.size(); i++) {
                if (id.equals(scColors.mColors.get(i).getId())) {
                    return (scColors.mColors.get(i).getName());
                }
            }
        }
        return "";
    }
    public String getCategory(String id) {
        if (scBaseCategories != null) {
            for (int i = 0; i < scBaseCategories.mBaseCategories.size(); i++) {
                if (id.equals(scBaseCategories.mBaseCategories.get(i).getId())) {
                    return (scBaseCategories.mBaseCategories.get(i).getName());
                }
            }
        }
        return "";
    }
    public String getSubCategory(String id) {
        if (scSubCategories != null) {
            for (int i = 0; i < scSubCategories.mSubCategories.size(); i++) {
                if (id.equals(scSubCategories.mSubCategories.get(i).getId())) {
                    return (scSubCategories.mSubCategories.get(i).getName());
                }
            }
        }
        return "";
    }
    public String getGender(String id) {
        if (scGenders != null) {
            for (int i = 0; i < scGenders.mGenders.size(); i++) {
                if (id.equals(scGenders.mGenders.get(i).getId())) {
                    return (scGenders.mGenders.get(i).getName());
                }
            }
        }
        return "";
    }
    public String getSize(String id) {
        if (scSizes != null) {
            for (int i = 0; i < scSizes.mSizes.size(); i++) {
                if (id.equals(scSizes.mSizes.get(i).getId())) {
                    return (scSizes.mSizes.get(i).getName());
                }
            }
        }
        return "";
    }
    public String getState(String id) {
        if (scStates != null) {
            for (int i = 0; i < scStates.mStates.size(); i++) {
                if (id.equals(scStates.mStates.get(i).getId())) {
                    return (scStates.mStates.get(i).getName());
                }
            }
        }
        return "";
    }
    public String getPayMethods(List<String> ids) {
        String ret = null;
        if (scPayMethods != null) {
            for (int i = 0; i < scPayMethods.mPayMethods.size(); i++) {
                for (int j = 0; j < ids.size(); j++) {
                    if (ids.get(j).equals(scPayMethods.mPayMethods.get(i).getId())) {
                        if (ret == null) {
                            ret = scPayMethods.mPayMethods.get(i).getName();
                        }
                        else {
                            ret = ret + "\n" + scPayMethods.mPayMethods.get(i).getName();
                        }
                    }
                }
            }
        }
        return ret;
    }


    public void retrieveDatas(MyCallback callback) {
        this.callback = callback;
        this.infosArticle = new InfosArticle(context, this);
        infosArticle.getBaseCategories();
    }

    public void convertToCharSequences() {
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < scBaseCategories.mBaseCategories.size() ; i++) {
            tmp.add((scBaseCategories.mBaseCategories.get(i)).getName());
        }
        cSBaseCategories = tmp.toArray(new CharSequence[scBaseCategories.mBaseCategories.size()]);
        tmp.clear();
        for (int i = 0; i < scSubCategories.mSubCategories.size() ; i++) {
            tmp.add((scSubCategories.mSubCategories.get(i)).getName());
        }
        cSSubCategories = tmp.toArray(new CharSequence[scSubCategories.mSubCategories.size()]);
        tmp.clear();
        for (int i = 0; i < scColors.mColors.size() ; i++) {
            tmp.add((scColors.mColors.get(i)).getName());
        }
        cSColors = tmp.toArray(new CharSequence[scColors.mColors.size()]);
        tmp.clear();
        for (int i = 0; i < scGenders.mGenders.size() ; i++) {
            tmp.add((scGenders.mGenders.get(i)).getName());
        }
        cSGenders = tmp.toArray(new CharSequence[scGenders.mGenders.size()]);
        tmp.clear();
        for (int i = 0; i < scSizes.mSizes.size() ; i++) {
            tmp.add((scSizes.mSizes.get(i)).getName());
        }
        cSSizes = tmp.toArray(new CharSequence[scSizes.mSizes.size()]);
        tmp.clear();
        for (int i = 0; i < scStates.mStates.size() ; i++) {
            tmp.add((scStates.mStates.get(i)).getName());
        }
        cSStates = tmp.toArray(new CharSequence[scStates.mStates.size()]);
        tmp.clear();
        for (int i = 0; i < scPayMethods.mPayMethods.size() ; i++) {
            tmp.add((scPayMethods.mPayMethods.get(i)).getName());
        }
        cSPayMethods = tmp.toArray(new CharSequence[scPayMethods.mPayMethods.size()]);
        tmp.clear();
    }

    public void loadCheckedItems() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        chkBaseCategories = preferences.getString(context.getString(R.string.chk_base_categories), null);
        chkSubCategories = preferences.getString(context.getString(R.string.chk_sub_categories), null);
        chkColors = preferences.getString(context.getString(R.string.chk_colors), null);
        chkGenders = preferences.getString(context.getString(R.string.chk_genders), null);
        chkSizes = preferences.getString(context.getString(R.string.chk_sizes), null);
        chkStates = preferences.getString(context.getString(R.string.chk_states), null);
        chkPayMethods = preferences.getString(context.getString(R.string.chk_pay_methods), null);
    }

    public void saveDatas() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        editor.putString(context.getString(R.string.base_categories), gson.toJson(scBaseCategories));
        editor.putString(context.getString(R.string.sub_categories), gson.toJson(scSubCategories));
        editor.putString(context.getString(R.string.colors), gson.toJson(scColors));
        editor.putString(context.getString(R.string.genders), gson.toJson(scGenders));
        editor.putString(context.getString(R.string.sizes), gson.toJson(scSizes));
        editor.putString(context.getString(R.string.states), gson.toJson(scStates));
        editor.putString(context.getString(R.string.pay_methods), gson.toJson(scPayMethods));
        editor.apply();
    }

    public void loadDatas() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        scBaseCategories = gson.fromJson(preferences.getString(context.getString(R.string.base_categories), null), SCBaseCategories.class);

        scSubCategories = gson.fromJson(preferences.getString(context.getString(R.string.sub_categories), null), SCSubCategories.class);
        scColors = gson.fromJson(preferences.getString(context.getString(R.string.colors), null), SCColors.class);
        scGenders = gson.fromJson(preferences.getString(context.getString(R.string.genders), null), SCGenders.class);
        scSizes = gson.fromJson(preferences.getString(context.getString(R.string.sizes), null), SCSizes.class);
        scStates = gson.fromJson(preferences.getString(context.getString(R.string.states), null), SCStates.class);
        scPayMethods = gson.fromJson(preferences.getString(context.getString(R.string.pay_methods), null), SCPayMethods.class);
    }

    @Override
    public void successCallback(String tag, Object val) {
        switch (tag) {
            case "getBaseCategories":
                scBaseCategories = (SCBaseCategories) val;
                infosArticle.getSubCategories();
                break;
            case "getSubCategories":
                scSubCategories = (SCSubCategories) val;
                infosArticle.getColors();
                break;
            case "getColors":
                scColors = (SCColors) val;
                infosArticle.getGenders();
                break;
            case "getGenders":
                scGenders = (SCGenders) val;
                infosArticle.getSizes();
                break;
            case "getSizes":
                scSizes = (SCSizes) val;
                infosArticle.getStates();
                break;
            case "getStates":
                scStates = (SCStates) val;
                infosArticle.getPayMethods();
                break;
            case "getPayMethods":
                scPayMethods = (SCPayMethods) val;
                callback.successCallback("retrieveDatas", null);
                break;
        }
    }

    @Override
    public void errorCallback(String tag, Object val) {

    }
}
