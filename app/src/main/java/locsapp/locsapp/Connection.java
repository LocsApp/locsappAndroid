package locsapp.locsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Connection {
    private Context context;
    private RequestQueue queue;
    private RequestCallback requestCallback;

    interface RequestCallback {
        void successCallback(Object result);
        void errorCallback(String error);
    }

    public Connection(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.requestCallback = (RequestCallback)context;
    }

    public void getImage (final String url, final HomeFragment fragment){

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap response) {
                fragment.setImage(response);
            }
        }, 0, 0, null,
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Callback Error Here;
            }
        });
        ir.setRetryPolicy(new DefaultRetryPolicy(800000, 7, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(ir);
    }

    public void registerUser (final String eMail,
                              final String firstName,
                              final String lastName,
                              final String login,
                              final String password1,
                              final String password2,
                              final String birthDate,
                              final String phone,
                              final String livingAddress,
                              final String billingAddress,
                              final String logoURL,
                              final Boolean isActive) {

        JSONObject params = new JSONObject();
        try {
            params.put("email", eMail);
            params.put("first_name", firstName);
            params.put("last_name", lastName);
            params.put("username", login);
            params.put("password1", password1);
            params.put("password2", password2);
            params.put("birthdate", birthDate);
            params.put("phone", phone);
            params.put("living_address", livingAddress);
            params.put("billing_address", billingAddress);
            params.put("logo_url", logoURL);
            params.put("is_active", isActive);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST, context.getString(R.string.registration), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("REPONSE", response.toString());
                        requestCallback.successCallback(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                });
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonRequest);
    }

    public void connectUser (final String login, final String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", login);
            params.put("password1", password);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST, context.getString(R.string.login), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestCallback.successCallback(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Error", error.toString());
                    }
                });
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(80000, 7, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonRequest);
    }
}
