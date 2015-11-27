package locsapp.locsapp;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sylflo on 11/26/15.
 */
public class ErrorUtils {

    public static APIError parseError(ResponseBody response, Retrofit retrofit) {
        Converter<ResponseBody, APIError> converter =
                retrofit.responseConverter(APIError.class, new Annotation[0]);

        APIError error;


        try {
            Log.d("ResponseErrorutils = ", response.string());
            error = converter.convert(response);

            if (error != null) {
                Log.d("ResponseErrorutils = ", "Not null");
            } else {
                Log.d("ResponseErrorutils = ", "Problem");
            }

        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
