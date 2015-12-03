package locsapp.locsapp.network;



import locsapp.locsapp.models.Login;
import locsapp.locsapp.models.Token;
import locsapp.locsapp.models.User;
import retrofit.http.GET;
import retrofit.http.Header;
import rx.Observable;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by sylflo on 11/24/15.
 */
public interface ApiEndpointInterface {

    @POST("/api/v1/rest-auth/registration/")
    Observable<User> createUser(@Body User user);

    @GET("/api/v1/rest-auth/user/")
    Observable<User> getUser(@Header("Authorization") String token);

    @POST("/api/v1/rest-auth/login/")
    Observable<Token> loginUser(@Body Login login);



}
