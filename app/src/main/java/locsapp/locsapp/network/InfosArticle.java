package locsapp.locsapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import locsapp.locsapp.activity.LoginActivity;
import locsapp.locsapp.fragment.AccountInformationsUpdate;
import locsapp.locsapp.interfaces.ApiEndpointInterface;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.models.AddressDetails;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.AskQuestion;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.Favorites;
import locsapp.locsapp.models.IdArticle;
import locsapp.locsapp.models.LivingAddress;
import locsapp.locsapp.models.SCBaseCategories;
import locsapp.locsapp.models.SCColors;
import locsapp.locsapp.models.SCGenders;
import locsapp.locsapp.models.SCPayMethods;
import locsapp.locsapp.models.SCSizes;
import locsapp.locsapp.models.SCStates;
import locsapp.locsapp.models.SCSubCategories;
import locsapp.locsapp.models.Search;
import locsapp.locsapp.models.SearchResults;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Damien on 12/3/2015.
 */
public class InfosArticle {

    Context mContext;
    MyCallback mCallback;

    public InfosArticle(Context context, MyCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void makeSearch(String token, Search params) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SearchResults> observable = service.searchArticles("token " + token, params);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResults>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getBaseCategories", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e.toString());
                        }
                    }
                    @Override
                    public void onNext(SearchResults articles) {
                        Log.d("onNext: ", articles.metadatas.mTotalItems.toString());
                        mCallback.successCallback("makeSearch", articles);
                    }
                });
    }
    public void getArticle(String token, String id) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<Article> observable = service.getArticle("token " + token, id);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getArticle success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getArticle", error);
                        }
                        else {
                            Log.e("ERROR", "getArticle no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(Article article) {
                        mCallback.successCallback("getArticle", article);
                        Log.d("MyResult", "ONnEXT");
                    }
                });
    }

    public void askQuestion(String token, AskQuestion question) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<Void> observable = service.askQuestion("token " + token, question);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Toast.makeText(mContext, "Failed to send question",
                                    Toast.LENGTH_LONG).show();
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("askQuestion", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(Void val) {
                        mCallback.successCallback("askQuestion", val);
                    }
                });
    }

    public void getFavorites(String token) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<Favorites> observable = service.getFavorites("token " + token);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Favorites>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getFavorite success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getFavorites", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(Favorites favorites) {
                        mCallback.successCallback("getFavorites", favorites);
                    }
                });
    }
    public void addFavorite(String token, IdArticle id) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<Article> observable = service.addFavorite("token " + token, id);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "addFavorite success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("addFavorite", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(Article article) {
                        mCallback.successCallback("addFavorite", article);
                    }
                });
    }
    public void deleteFavorite(String token, IdArticle id) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<Article> observable = service.deleteFavorite("token " + token, id);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "deleteFavorite success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("deleteFavorite", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(Article article) {
                        mCallback.successCallback("deleteFavorite", article);
                    }
                });
    }

    public void getBaseCategories() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCBaseCategories> observable = service.getCategories();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCBaseCategories>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getBaseCategories", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCBaseCategories categories) {
                        mCallback.successCallback("getBaseCategories", categories);
                    }
                });
    }
    public void getSubCategories() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCSubCategories> observable = service.getSubCategories();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCSubCategories>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getSubCategories", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCSubCategories categories) {
                        mCallback.successCallback("getSubCategories", categories);
                    }
                });
    }
    public void getColors() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCColors> observable = service.getColors();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCColors>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getColors", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCColors colors) {
                        mCallback.successCallback("getColors", colors);
                    }
                });
    }
    public void getGenders() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCGenders> observable = service.getGenders();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCGenders>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getGenders", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCGenders genders) {
                        mCallback.successCallback("getGenders", genders);
                    }
                });
    }
    public void getSizes() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCSizes> observable = service.getSizes();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCSizes>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getSizes", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCSizes sizes) {
                        mCallback.successCallback("getSizes", sizes);
                    }
                });
    }
    public void getStates() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCStates> observable = service.getStates();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCStates>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getStates", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCStates states) {
                        mCallback.successCallback("getStates", states);
                    }
                });
    }
    public void getPayMethods() {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Observable<SCPayMethods> observable = service.getPayMethods();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SCPayMethods>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("getPayMethods", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(SCPayMethods methods) {
                        mCallback.successCallback("getPayMethods", methods);
                    }
                });
    }
}
