package locsapp.locsapp.network;

import android.app.Activity;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import locsapp.locsapp.R;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import java.lang.annotation.Annotation;
import retrofit.Converter;
import com.squareup.okhttp.ResponseBody;


public class ServiceGenerator extends Activity {

    public static final String API_BASE_URL = "http://91.121.70.38"; //http://sylflo.fr:8010";//;
    private static Retrofit mRetrofit = null;

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        mRetrofit = builder.client(httpClient).build();
        return mRetrofit.create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }

}

