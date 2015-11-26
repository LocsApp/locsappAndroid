package locsapp.locsapp;

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

    public static APIError parseError(Response<User> response, Retrofit retrofit) {
        Converter<ResponseBody, APIError> converter =
                retrofit.responseConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
