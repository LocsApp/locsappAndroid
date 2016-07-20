package locsapp.locsapp.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.activity.LoginActivity;
import locsapp.locsapp.interfaces.ApiEndpointInterface;
import locsapp.locsapp.interfaces.MyCallback;
import locsapp.locsapp.activity.ResetPasswd;
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

public class ConnectionUser {

    Context mContext;
    MyCallback mCallback;

    public ConnectionUser(Context context) {
        mContext = context;
        mCallback = (MyCallback) context;
    }

    public void login(String username, String password) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        Login login = new Login(username, password);

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
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("login", error);
                        }
                        else {
                            Log.e("ERROR", "ERREUR pas http" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Token token) {
                        Log.d("MyResult", "onNext " + token.getKey());
                        mCallback.successCallback("login", token.getKey());
                    }
                });
    }

    public void loginFB(String token) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        LoginFB login = new LoginFB(token);

        Observable<Token> observable = service.loginUserFB(login);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
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
                            mCallback.errorCallback("loginFB", error);
                        }
                        else {
                            Log.e("ERROR", "ERREUR pas http" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Token token) {
                        Log.d("MyResult", "onNext " + token.getKey());
                        mCallback.successCallback("loginFB", token.getKey());
                    }
                });
    }

    public void createUserFB(String token, String username) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
        UserFB login = new UserFB(token, username);

        Observable<Token> observable = service.createUserFB(login);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "Success register", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("REGISTER FB", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("createUserFB", error);
                        }
                    }
                    @Override
                    public void onNext(Token token) {
                        Log.d("MyResult", "onNext " + token);
                        mCallback.successCallback("createUserFB", token.getKey());
                    }
                });
    }

    public void resetPassword(String email) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
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
                            mCallback.errorCallback("resetPassword", error);
                        }
                    }
                    @Override
                    public void onNext(String result) {
                        Log.d("MyResult", "onNext " + result);
                        mCallback.successCallback("resetPassword", result);
                    }
                });
    }

    public void changePassword(String token, String old, String new1, String new2, final AccountInformationsChangePasswd fragment) {
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
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
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
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
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class, mContext);
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
                            try {
                                String test = new String(((HttpException) e).response().errorBody().bytes());
                                Log.d("ERROR", "onError: " + test);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            mCallback.errorCallback("register", error);
                        }
                        else {
                            Log.e("ERROR", "ERREUR pas http" + e.getMessage());
                        }
                    }
                    @Override
                    public void onNext(User user) {
                        Log.d("MyResult", "onNext " + user.mUsername);
                        mCallback.successCallback("register", user);
                    }
                });
    }
}
