package locsapp.locsapp.network;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.activity.LoginActivity;
import locsapp.locsapp.activity.ResetPasswd;
import locsapp.locsapp.fragment.AccountInformations;
import locsapp.locsapp.fragment.AccountInformationsChangePasswd;
import locsapp.locsapp.models.Login;
import locsapp.locsapp.models.LoginFB;
import locsapp.locsapp.models.Passwd;
import locsapp.locsapp.models.Token;
import locsapp.locsapp.models.User;
import locsapp.locsapp.models.UserFB;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sylflo on 11/27/15.
 */

public class ConnectionUser {

    Context mContext;

    public ConnectionUser(Context context) {
        mContext = context;
    }

    public void login(String username, String password) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        Login login = new Login(username, password);
        final LoginActivity activity = (LoginActivity) mContext;

        Observable<Token> observable = service.loginUser(login);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Success login",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            activity.errorCallback(error);
                        }
                    }

                    @Override
                    public void onNext(Token token) {
                        Log.d("MyResult", "onNext " + token.getKey());
                        activity.successCallback(token.getKey());
                    }
                });
    }

    public void loginFB(String token, String code) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        LoginFB login = new LoginFB(token, code);
        final LoginActivity activity = (LoginActivity) mContext;

        Observable<String> observable = service.loginUserFB(login);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Success login", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            activity.errorCallback(error);
                        }
                    }

                    @Override
                    public void onNext(String token) {
                        Log.d("MyResult", "onNext " + token);
                        //activity.successCallback(token);
                    }
                });
    }

    public void createUserFB(String token, String code, String username) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        UserFB login = new UserFB(token, code, username);
        final LoginActivity activity = (LoginActivity) mContext;

        Observable<String> observable = service.createUserFB(login);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Success login", Toast.LENGTH_LONG).show();
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
                            activity.errorCallback(error);
                        }
                    }

                    @Override
                    public void onNext(String token) {
                        Log.d("MyResult", "onNext " + token);
                        //activity.successCallback(token);
                    }
                });
    }

    public void resetPassword(String email) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        final ResetPasswd activity = (ResetPasswd) mContext;

        Observable<String> observable = service.resetPassword(email);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Success reset",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            String error = ((HttpException) e).response().message();
                            activity.errorCallback(error);
                        }
                    }
                    @Override
                    public void onNext(String result) {
                        Log.d("MyResult", "onNext " + result);
                        activity.successCallback(result);
                    }
                });
    }

    public void changePassword(String token, String old, String new1, String new2, final AccountInformationsChangePasswd fragment) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        final HomeActivity activity = (HomeActivity) mContext;
        Passwd passwd = new Passwd(old, new1, new2);

        Observable<Void> observable = service.changePassword("token " + token, passwd);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Successfully Changed",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            String error = ((HttpException) e).response().errorBody().toString();
                            fragment.errorCallback(error);
                        }
                    }
                    @Override
                    public void onNext(Void result) {
                        Log.d("SuccessChange", "onNext " + result);
                        fragment.successCallback(null);
                    }
                });
    }

    public void logoutUser(String token) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        final HomeActivity activity = (HomeActivity) mContext;

        Observable<Void> observable = service.logoutUser("token " + token);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Success Logout",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("LOGOUT", "OnError " + e);
                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            Log.e("LOGOUT", String.valueOf(((HttpException) e).response().code()));
                        }
                    }
                    @Override
                    public void onNext(Void result) {
                        Log.d("LOGOUT", "dqsdqd");
                        Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
    }

    public void register(String email, String username, String password1, String password2) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        User user = new User(email, username, password1, password2);

        Observable<User> observable = service.createUser(user);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "Register success",
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
                    }
                });
    }
}
