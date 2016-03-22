package locsapp.locsapp.network;

import com.google.gson.annotations.SerializedName;

import locsapp.locsapp.models.Login;
import locsapp.locsapp.models.LoginFB;
import locsapp.locsapp.models.Passwd;
import locsapp.locsapp.models.Token;
import locsapp.locsapp.models.User;
import locsapp.locsapp.models.UserFB;
import locsapp.locsapp.models.UserPut;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import rx.Observable;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by sylflo on 11/24/15.
 */
public interface ApiEndpointInterface {
    //--- ConnectionUser
    @POST("/api/v1/rest-auth/registration/")
    Observable<User> createUser(@Body User user);

    @POST("/api/v1/rest-auth/login/")
    Observable<Token> loginUser(@Body Login login);

    @POST("/api/v1/rest-auth/facebook-login/")
    Observable<Token> loginUserFB(@Body LoginFB login);

    @POST("/api/v1/rest-auth/facebook-register/")
    Observable<Token> createUserFB(@Body UserFB user);

    @POST("/api/v1/rest-auth/logout/")
    Observable<Void> logoutUser(@Header("Authorization") String token);

    @POST("/api/v1/rest-auth/password/change/")
    Observable<Void> changePassword(@Header("Authorization") String token, @Body Passwd passwd);

    @POST("/api/v1/rest-auth/password/reset/")
    Observable<String> resetPassword(@Body String email);

    //--- InfoUser
    @POST("/api/v1/auth/change-username/")
    Observable<Void> setUsername(@Header("Authorization") String token, @Body String username);

    @GET("/api/v1/rest-auth/user/")
    Observable<User> getUser(@Header("Authorization") String token);

    @GET("/api/v1/rest-auth/user/")
    Observable<User> checkUsername(@Header("Authorization") String token);

    @PUT("/api/v1/rest-auth/user/")
    Observable<User> updateUser(@Header("Authorization") String token, @Body UserPut user);

}
