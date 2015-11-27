package locsapp.locsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.SyncStateContract;
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
    private String serverAddr;

    interface RequestCallback {
        void successCallback(Object result);
        void errorCallback(JSONObject error);
    }

    public Connection(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.serverAddr = context.getString(R.string.server);
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


    public void connectUser (final String login, final String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", login);
            params.put("password", password);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST, serverAddr + context.getString(R.string.login), params,
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
