package locsapp.locsapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import locsapp.locsapp.activity.LoginActivity;
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
