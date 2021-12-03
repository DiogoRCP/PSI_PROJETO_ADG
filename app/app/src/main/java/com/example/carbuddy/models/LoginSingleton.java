package com.example.carbuddy.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.controllers.Json_Objects_Convertor;

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

    public LoginSingleton(Context context, String user, String pass) {
        ModeloBDHelper database = new ModeloBDHelper(context);
        if (database.getAllLogin().size() < 1) {
            login = new Login("","","");
            apiLogin(context, database, user, pass);
        } else {
            login = database.getAllLogin().get(0);
        }
    }

    public void apiLogin(Context context, ModeloBDHelper database, String user, String pass) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:8080/api/login/get?username=" + user + "&password=" + pass;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        login = (Login) Json_Objects_Convertor.objectjsonConvert(response, Login.class);
                        database.insertLogin(login);

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

    public static void setInstancia(LoginSingleton instancia) {
        LoginSingleton.instancia = instancia;
    }
}
