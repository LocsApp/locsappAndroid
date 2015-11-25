package locsapp.locsapp;


import rx.Observable;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by sylflo on 11/24/15.
 */
public interface ApiEndpointInterface {

   /* @POST("/api/v1/rest-auth/registration/")
    Call<User> createUser(@Body User user);*/


    @POST("/api/v1/rest-auth/registration/")
    Observable<User> createUser(@Body User user);



}
