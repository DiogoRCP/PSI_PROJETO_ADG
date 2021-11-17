package com.example.carbuddy.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginSingleton {
    Login login;

    private static LoginSingleton instancia = null;

    public static synchronized LoginSingleton getInstance(Context context, String user, String pass) {
        if (instancia == null) {
            instancia = new LoginSingleton(context, user, pass);
        }
        return instancia;
    }

    public LoginSingleton(Context context, String user, String pass){
        apiLogin(context, user, pass);
    }

    public void apiLogin(Context context, String user, String pass) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:8080/api/login/get?username=" + user + "&password=" + pass;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            login = new Login(response.getBoolean("Login"), response.getString("authkey"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public Login getLogin() {
        return login;
    }
}
