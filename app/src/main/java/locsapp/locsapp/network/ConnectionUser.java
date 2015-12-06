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
import locsapp.locsapp.models.Login;
import locsapp.locsapp.models.Token;
import locsapp.locsapp.models.User;
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
        // final ServiceGenerator serviceGenerator = new ServiceGenerator();

        //ApiEndpointInterface service = retrofit.create(ApiEndpointInterface.class);
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
                        // handle completed
                        Toast.makeText(mContext, "Success login",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle error


                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                            activity.errorCallback(error);
                        }
                    }

                    @Override
                    public void onNext(Token token) {
                        // handle response
                        Log.d("MyResult", "onNext " + token.getKey());
                        activity.successCallback(token.getKey());
                    }
                });
    }

    public void resetPassword(String email) {
        // final ServiceGenerator serviceGenerator = new ServiceGenerator();

        //ApiEndpointInterface service = retrofit.create(ApiEndpointInterface.class);
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        final ResetPasswd activity = (ResetPasswd) mContext;

        Observable<String> observable = service.resetPassword(email);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        // handle completed
                        Toast.makeText(mContext, "Success reset",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle error

                        if (e instanceof HttpException) {
                            String error = ((HttpException) e).response().errorBody().toString();
                            activity.errorCallback(error);
                        }
                    }

                    @Override
                    public void onNext(String result) {
                        // handle response
                        Log.d("MyResult", "onNext " + result);
                        activity.successCallback(result);
                    }
                });
    }

    public void logoutUser(String token) {
        // final ServiceGenerator serviceGenerator = new ServiceGenerator();

        //ApiEndpointInterface service = retrofit.create(ApiEndpointInterface.class);
        final ApiEndpointInterface service = ServiceGenerator.createService(ApiEndpointInterface.class);
        final HomeActivity activity = (HomeActivity) mContext;

        Observable<Void> observable = service.logoutUser("token " + token);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        // handle completed
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
                        // handle response
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
                        // handle completed
                        Log.d("MyResult", "onCompleted");
                        Toast.makeText(mContext, "Register success",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle error


                        if (e instanceof HttpException) {
                            ErrorLogin error = ErrorUtils.parseError(((HttpException) e).response().errorBody(), ServiceGenerator.getRetrofit());
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        // handle response
                        Log.d("MyResult", "onNext " + user.mUsername);

                    }
                });
    }
}
