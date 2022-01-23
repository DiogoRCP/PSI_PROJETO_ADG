package com.example.carbuddy.models;

import static com.example.carbuddy.utils.libs.ACCESSTOKEN;
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
import com.example.carbuddy.R;
import com.example.carbuddy.singletons.LoginSingleton;
import com.example.carbuddy.utils.libs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Modelo Singup, onde são definidos os getters, setters, construtores, propriedades e redefinição do método toString
 **/
public class Signup {
    String username;
    String email;
    String nif;
    String birsthday;
    String phonenumber;
    String password;

    /**
     * Construtor do modelo singup quando se faz o registo de um user
     **/
    public Signup(String username, String email, String nif, String birsthday, String phonenumber, String password) {
        this.username = username;
        this.email = email;
        this.nif = nif;
        this.birsthday = birsthday;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    /**
     * Construtor do modelo singup quando se faz o update de um user
     **/
    public Signup(String email, String password) {
        this.username = "";
        this.email = email;
        this.nif = "";
        this.birsthday = "";
        this.phonenumber = "";
        this.password = password;
    }

    //Getters e Setters
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

    /**
     * Redefinição do método toString
     **/
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

    /**
     * Método utilizado para verificar se as duas password introduzidas no registo e update são iguais
     **/
    public static boolean PasswordVerify(String password1, String password2) {
        if (password1.matches(password2)) {
            return true;
        }
        return false;
    }

    /**
     * Método que efetua o registo de um utilizador
     **/
    public void DoSignup(Context context) throws JSONException {

        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de POST do user
        else {
            // cria um request em volley
            RequestQueue queue = Volley.newRequestQueue(context);
            // indicação do URL do endpoint da API
            String url = IP + "signup/post";
            // Colocar os dados do POST no jsonObject
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", this.getUsername());
            jsonBody.put("email", this.getEmail());
            jsonBody.put("nif", this.getNif());
            jsonBody.put("phonenumber", this.getPhonenumber());
            jsonBody.put("password", this.getPassword());
            jsonBody.put("birsthday", this.getBirsthday());

            // cria um request JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        // Quando o pedido é executado corretamente
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("VOLLEY", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        // Quando o pedido não é executado corretamente (Erro)
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Adicionar pedido à fila
            queue.add(jsonObjectRequest);
        }
    }

    /**
     * Método que efetua o atualização de um utilizador
     **/
    public void updateAccount(Context context) throws JSONException {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de PUT do user
        else {
            // cria um request em volley
            RequestQueue queue = Volley.newRequestQueue(context);
            // indicação do URL do endpoint da API
            String url = IP + "user/put" + ACCESSTOKEN(context);
            // Colocar os dados do PUT no jsonObject
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", this.getEmail());
            // Colocar a password no jsonObject se a mesma na for vazia
            if (!this.getPassword().equals("")) jsonBody.put("password", this.getPassword());
            System.out.println(jsonBody.toString());
            // cria um request JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                        // Quando o pedido é executado corretamente
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("VOLLEY", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        // Quando o pedido não é executado corretamente (Erro)
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Adicionar pedido à fila
            queue.add(jsonObjectRequest);
        }
    }
}