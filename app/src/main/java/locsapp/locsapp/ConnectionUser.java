package locsapp.locsapp;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sylflo on 11/27/15.
 */
public class ConnectionUser {


    public void register(String email, String username, String password1, String password2) {
        final ServiceGenerator service_test = new ServiceGenerator();

        //ApiEndpointInterface service = retrofit.create(ApiEndpointInterface.class);
        final ApiEndpointInterface service = service_test.createService(ApiEndpointInterface.class);
        User user = new User("dev.chateau@gmail.com", "sylflo", "toto42", "toto42");

        Observable<User> observable = service.createUser(user);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        // handle completed
                        Log.d("MyResult", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle error


                        if (e instanceof HttpException) {

                            Converter<ResponseBody, Error> errorConverter =
                                    service_test.retrofit.responseConverter(APIError.class, new Annotation[0]);


                            ResponseBody body = ((HttpException) e).response().errorBody();


                            try {

                                //Log.d("Myresult", body.string());
                                if (body != null) {
                                    Error error = errorConverter.convert(body);
                                    Log.d("Myresult final", error.getMessage());
                                } else {
                                    Log.d("MYresult", "error 500");
                                }


                            } catch (IOException f) {
                                Log.d("Myresult", "Catch");
                            }

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
