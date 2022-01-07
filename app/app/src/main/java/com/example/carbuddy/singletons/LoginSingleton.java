package com.example.carbuddy.singletons;

import static com.example.carbuddy.utils.Json_Objects_Convertor.IP;
import static com.example.carbuddy.utils.Json_Objects_Convertor.isInternetConnection;
import static com.example.carbuddy.utils.Json_Objects_Convertor.objectjsonConvert;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.controllers.AccountFragment;
import com.example.carbuddy.controllers.MainActivity;
import com.example.carbuddy.listeners.LoginListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.Login;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.utils.Json_Objects_Convertor;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginSingleton {
    Login login;
    private ModeloBDHelper database;
    private static LoginSingleton instancia = null;

    // volley
    private static RequestQueue volleyQueue = null;

    // listener
    private LoginListener loginListener = null;
    private LoginListener AccountFragment = null;
    private LoginListener loginListenerAccount;

    public static synchronized LoginSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new LoginSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    public LoginSingleton(Context context) {
        /* Inicializar variaveis **/
        database = new ModeloBDHelper(context);
        if (database.getAllLogin().size() > 0) {
            login = database.getAllLogin().getFirst();
        }
    }

    public void apiLogin (Context context, final String user, final String pass) throws JSONException {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            final String URL_LOGIN = IP+"login/do";

            JSONObject loginData = new JSONObject();
            loginData.put("username", user);
            loginData.put("password", pass);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, URL_LOGIN, loginData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                login = (Login) objectjsonConvert(response.getJSONObject("user"), Login.class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            loginListener.onValidateLogin(login);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            volleyQueue.add(jsonObjectRequest);
        }
    }

    public void apiAccount(Context context) {
        /* Verificar se existe internet **/
        if (!Json_Objects_Convertor.isInternetConnection(context)) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println(IP);
            final String URL_LOGIN = IP + "user/account?access-token=" + this.getLogin().getToken();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET,
                            URL_LOGIN,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    login = (Login) objectjsonConvert(response, Login.class);
                                    loginListener.onValidateLogin(login);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            volleyQueue.add(jsonObjectRequest);
        }
    }


    public Login getLogin() {
        return login;
    }

    public void setLoginListener(MainActivity mainActivity) {
        this.loginListener = mainActivity;
    }
    public void setLoginListenerAccount(AccountFragment accountFragment) {
        this.loginListener = accountFragment;
    }

}
