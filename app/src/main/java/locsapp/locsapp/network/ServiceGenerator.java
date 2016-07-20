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

    public final String API_BASE_URL = "https://vxadvxkawm.localtunnel.me"; //"https://xjgxgfyhyq.localtunnel.me/"; // https://locsapp.sylflo.fr";//
    private Retrofit mRetrofit = null;

    private OkHttpClient httpClient = new OkHttpClient();
    private Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(getApplication().getResources().getString(R.string.api_url))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public <S> S createService(Class<S> serviceClass) {
        mRetrofit = builder.client(httpClient).build();
        return mRetrofit.create(serviceClass);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}

