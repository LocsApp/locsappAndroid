package locsapp.locsapp.interfaces;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.AskQuestion;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.Favorites;
import locsapp.locsapp.models.IdArticle;
import locsapp.locsapp.models.LivingAddress;
import locsapp.locsapp.models.Login;
import locsapp.locsapp.models.LoginFB;
import locsapp.locsapp.models.Passwd;
import locsapp.locsapp.models.SCBaseCategories;
import locsapp.locsapp.models.SCColors;
import locsapp.locsapp.models.SCGenders;
import locsapp.locsapp.models.SCPayMethods;
import locsapp.locsapp.models.SCSizes;
import locsapp.locsapp.models.SCStates;
import locsapp.locsapp.models.SCSubCategories;
import locsapp.locsapp.models.Search;
import locsapp.locsapp.models.SearchResults;
import locsapp.locsapp.models.Token;
import locsapp.locsapp.models.User;
import locsapp.locsapp.models.UserFB;
import locsapp.locsapp.models.UserPut;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;
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

    @POST("/api/v1/auth/facebook-login/")
    Observable<Token> loginUserFB(@Body LoginFB login);

    @POST("/api/v1/auth/facebook-register/")
    Observable<Token> createUserFB(@Body UserFB user);

    @POST("/api/v1/rest-auth/logout/")
    Observable<Void> logoutUser(@Header("Authorization") String token);

    @POST("/api/v1/rest-auth/password/change/")
    Observable<Void> changePassword(@Header("Authorization") String token, @Body Passwd passwd);

    @POST("/api/v1/rest-auth/password/reset/")
    Observable<String> resetPassword(@Body String email);

    //--- InfoUser
    @GET("/api/v1/rest-auth/user/")
    Observable<User> getUser(@Header("Authorization") String token);

    @GET("/api/v1/rest-auth/user/")
    Observable<User> checkUsername(@Header("Authorization") String token);

    @PUT("/api/v1/rest-auth/user/")
    Observable<User> updateUser(@Header("Authorization") String token, @Body UserPut user);

    @GET("/api/v1/user/{id}/living_addresses/")
    Observable<String> getLivingAddress(@Header("Authorization") String token, @Path("id") String userId);

    @POST("/api/v1/user/{id}/living_addresses/")
    Observable<Void> addLivingAddress(@Header("Authorization") String token, @Path("id") String userId, @Body LivingAddress address);

    @POST("/api/v1/user/{id}/living_addresses/delete/")
    Observable<String> deleteLivingAddress(@Header("Authorization") String token, @Path("id") String userId, @Body LivingAddress living_address);

    @GET("/api/v1/user/{id}/billing_addresses/")
    Observable<String> getBillingAddress(@Header("Authorization") String token, @Path("id") String userId);

    @POST("/api/v1/user/{id}/billing_addresses/")
    Observable<Void> addBillingAddress(@Header("Authorization") String token, @Path("id") String userId, @Body BillingAddress address);

    @POST("/api/v1/user/{id}/billing_addresses/delete/")
    Observable<String> deleteBillingAddress(@Header("Authorization") String token, @Path("id") String userId, @Body BillingAddress address);

    //--- Articles
    @POST("/api/v1/search/articles/")
    Observable<SearchResults> searchArticles(@Header("Authorization") String token, @Body Search searchParams);

    @GET("/api/v1/articles/{id}/")
    Observable<Article> getArticle(@Header("Authorization") String token, @Path("id") String articleId);

    //--- Questions
    @POST("/api/v1/articles/questions/")
    Observable<Void> askQuestion(@Header("Authorization") String token, @Body AskQuestion question);

    //--- Favorites
    @GET("/api/v1/favorites/articles/")
    Observable<Favorites> getFavorites(@Header("Authorization") String token);

    @POST("/api/v1/favorites/articles/")
    Observable<Article> addFavorite(@Header("Authorization") String token, @Body IdArticle id);

    @POST("/api/v1/favorites/delete-articles/")
    Observable<Article> deleteFavorite(@Header("Authorization") String token, @Body IdArticle id);

    //---InfosArticle Static Collections
    @GET("/api/v1/static-collections/sub-categories/")
    Observable<SCSubCategories> getSubCategories();

    @GET("/api/v1/static-collections/base-categories/")
    Observable<SCBaseCategories> getCategories();

    @GET("/api/v1/static-collections/genders/")
    Observable<SCGenders> getGenders();

    @GET("/api/v1/static-collections/sizes/")
    Observable<SCSizes> getSizes();

    @GET("/api/v1/static-collections/clothe-colors/")
    Observable<SCColors> getColors();

    @GET("/api/v1/static-collections/clothe-states/")
    Observable<SCStates> getStates();

    @GET("/api/v1/static-collections/payment-methods/")
    Observable<SCPayMethods> getPayMethods();

}
