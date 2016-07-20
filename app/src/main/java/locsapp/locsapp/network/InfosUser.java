package locsapp.locsapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import locsapp.locsapp.activity.LoginActivity;
import locsapp.locsapp.interfaces.ApiEndpointInterface;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.fragment.AccountInformations;
import locsapp.locsapp.fragment.AccountInformationsUpdate;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.LivingAddress;
import locsapp.locsapp.models.User;
import locsapp.locsapp.models.UserPut;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Damien on 12/3/2015.
 */
public class InfosUser {

    Context mContext;
    MyCallback mCallback;

    public InfosUser(Context context, MyCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void checkUsername(String token, final LoginActivity activity) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<User> observable = service.checkUsername("token " + token);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(User user) {
                        Log.d("MyResult", "CHECKUSERNAME " + user.mUsername);
                        activity.successCallback("FBUser", user.mUsername);
                    }
                });
    }

    public void getUser(String token) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<User> observable = service.getUser("token " + token);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
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
                            mCallback.errorCallback("getUser", error);
                        }
                        else {
                            Log.e("ERROR", "no http: " + e);
                        }
                    }
                    @Override
                    public void onNext(User user) {
                        Log.d("MyResult", "onNext " + user.mUsername);
                        mCallback.successCallback("getUser", user);
                    }
                });
    }

    public void getLivingAddress(String token, String userId) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<String> observable = service.getLivingAddress("token " + token, userId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(String address) {
                        mCallback.successCallback("getLivvingAddress", address);
                    }
                });
    }

    public void deleteLivingAddress(String token, String userId, LivingAddress adr) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<String> observable = service.deleteLivingAddress("token " + token, userId, adr);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "Delete success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(String address) {
                        mCallback.successCallback("deleteLivingAddress", address);
                    }
                });
    }

    public void getBillingAddress(String token, String userId) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<String> observable = service.getBillingAddress("token " + token, userId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(String address) {
                        mCallback.successCallback("getBillingAddress", address);
                    }
                });
    }

    public void deleteBillingAddress(String token, String userId, BillingAddress adr) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<String> observable = service.deleteBillingAddress("token " + token, userId, adr);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "Delete success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(String address) {
                        mCallback.successCallback("deleteBillingAddress", address);
                    }
                });
    }

    public void addLivingAddress(String token, String userId, LivingAddress address) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<Void> observable = service.addLivingAddress("token " + token, userId, address);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                        Log.d("MyResult", "onError" + e.getMessage());
                    }
                    @Override
                    public void onNext(Void val) {
                        Log.e("successCallback: ", "ok3");
                        mCallback.successCallback("addLivingAddress", val);
                    }
                });
    }

    public void addBillingAddress(String token, String userId, BillingAddress address) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<Void> observable = service.addBillingAddress("token " + token, userId, address);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "getInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(Void val) {
                        mCallback.successCallback("addBillingAddress", val);
                    }
                });
    }

    public void updateUser(String token, UserPut user, final AccountInformationsUpdate fragment) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<User> observable = service.updateUser("token " + token, user);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "UpdateInfos success",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(User user) {
                        Log.d("MyResult", "onNext " + user.mUsername);
                        fragment.successCallback(user);
                    }
                });
    }
}
