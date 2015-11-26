package locsapp.locsapp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import com.squareup.okhttp.ResponseBody;


/**
 * Created by sylflo on 11/24/15.
 */
public class ServiceGenerator {


    public static final String API_BASE_URL = "http://10.0.2.2:8000";
    public static Retrofit retrofit;

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}

