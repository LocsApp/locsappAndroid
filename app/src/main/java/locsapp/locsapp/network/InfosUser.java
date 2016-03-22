package locsapp.locsapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.activity.LoginActivity;
import locsapp.locsapp.activity.MyCallback;
import locsapp.locsapp.activity.SetUsernameFBActivity;
import locsapp.locsapp.fragment.AccountInformations;
import locsapp.locsapp.fragment.AccountInformationsUpdate;
import locsapp.locsapp.models.Login;
import locsapp.locsapp.models.Token;
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

    public InfosUser(Context context) {
        mContext = context;
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

    public void getUserTest(String token, final MyCallback callback) {
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
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(User user) {
                        Log.d("MyResult", "onNext " + user.mBillingAddress + " " + user.mUsername);
                        callback.successCallback("getUser", user);
                    }
                });
    }

    public void getUser(String token, final AccountInformations fragment) {
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
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }
                    @Override
                    public void onNext(User user) {
                        Log.d("MyResult", "onNext " + user.mBillingAddress + " " + user.mUsername);
                        fragment.successCallback("infos", user);
                    }
                });
    }

    public void setUsername(final String token, final String username, final SetUsernameFBActivity activity) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Observable<Void> observable = service.setUsername("token " + token, username);
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
                    public void onNext(Void aVoid) {
                        Log.d("MyResult", "onNext ");
                        activity.successCallback("SETUSERNAME", null);
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
                        Log.d("MyResult", "onNext " + user.mBillingAddress + " " + user.mUsername);
                        fragment.successCallback(user);
                    }
                });
    }
}
