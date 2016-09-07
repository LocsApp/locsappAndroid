package locsapp.locsapp.network;

import android.app.Activity;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import locsapp.locsapp.App;
import locsapp.locsapp.R;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class ServiceGenerator extends Activity {

    private static Retrofit mRetrofit = null;

    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(App.getContext().getString(R.string.api_url))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        httpClient.setReadTimeout(60, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(60, TimeUnit.SECONDS);
        mRetrofit = builder.client(httpClient).build();
        return mRetrofit.create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }
}