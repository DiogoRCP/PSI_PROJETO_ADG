package com.example.carbuddy.models;

import static com.example.carbuddy.utils.libs.IP;
import static com.example.carbuddy.utils.libs.isInternetConnection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.singletons.LoginSingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup {
    String username;
    String email;
    String nif;
    String birsthday;
    String phonenumber;
    String password;

    public Signup(String username, String email, String nif, String birsthday, String phonenumber, String password) {
        this.username = username;
        this.email = email;
        this.nif = nif;
        this.birsthday = birsthday;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public Signup(String email, String password) {
        this.username = "";
        this.email = email;
        this.nif = "";
        this.birsthday = "";
        this.phonenumber = "";
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getBirsthday() {
        return birsthday;
    }

    public void setBirsthday(String birsthday) {
        this.birsthday = birsthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Signup{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nif='" + nif + '\'' +
                ", birsthday='" + birsthday + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static boolean PasswordVerify(String password1, String password2) {
        if (password1.matches(password2)) {
            return true;
        }
        return false;
    }

    public void DoSignup(Context context) throws JSONException {

        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "signup/post";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", this.getUsername());
            jsonBody.put("email", this.getEmail());
            jsonBody.put("nif", this.getNif());
            jsonBody.put("phonenumber", this.getPhonenumber());
            jsonBody.put("password", this.getPassword());
            jsonBody.put("birsthday", this.getBirsthday());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("VOLLEY", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public void updateAccount(Context context) throws JSONException {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "user/put?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", this.getEmail());
            if (!this.getPassword().equals("")) jsonBody.put("password", this.getPassword());
            System.out.println(jsonBody.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("VOLLEY", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }
}