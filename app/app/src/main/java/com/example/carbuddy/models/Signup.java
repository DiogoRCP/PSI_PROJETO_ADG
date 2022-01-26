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
import com.example.carbuddy.controllers.SignupActivity;
import com.example.carbuddy.listeners.SignupListener;
import com.example.carbuddy.singletons.LoginSingleton;
import com.example.carbuddy.utils.libs;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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
                            SignupListener signupListener = (SignupListener) context;
                            signupListener.onSignup(true);
                        }
                    }, new Response.ErrorListener() {
                        // Quando o pedido não é executado corretamente (Erro)
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Verifica se o status de erro é 409 que significa Conflit
                            if (error.networkResponse.statusCode == 409) {
                                try {
                                    //Recebe a informação em json da mensagem, code, status...
                                    final String errorMessage = new String(error.networkResponse.data, "UTF-8");

                                    //Cria uma lista com a informação do erro dividindo por ","
                                    String[] errorMessageData = errorMessage.split(",");

                                    //Divide a chave:valor do code numa lista em que a posição 0 é
                                    // a chave e a posição 1 é o número correspondente ao code
                                    String[] errorCode = errorMessageData[2].split(":");

                                    //Cria a mensagem de erro que vai ser escolhida consuante o número do code
                                    String mensagemFinalDeErro;
                                    switch (Integer.parseInt(errorCode[1])) {
                                        case 0:
                                            mensagemFinalDeErro = context.getResources().getString(R.string.UsernameInUse);
                                            break;
                                        case 1:
                                            mensagemFinalDeErro = context.getResources().getString(R.string.NifInUse);
                                            break;
                                        case 2:
                                            mensagemFinalDeErro = context.getResources().getString(R.string.EmailInUse);
                                            break;
                                        case 3:
                                            mensagemFinalDeErro = context.getResources().getString(R.string.PhoneNumberInUse);
                                            break;
                                        default:
                                            mensagemFinalDeErro = context.getResources().getString(R.string.Error);
                                    }
                                    //Mostra a mensagem de erro
                                    Toast.makeText(context, mensagemFinalDeErro, Toast.LENGTH_SHORT).show();

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //Mostra a mensagem de erro se o status não for 409
                                Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                            }
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
            // cria um request JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                        // Quando o pedido é executado corretamente
                        @Override
                        public void onResponse(JSONObject response) {
                            SignupListener signupListener = (SignupListener) context;
                            signupListener.onSignup(false);
                        }
                    }, new Response.ErrorListener() {
                        // Quando o pedido não é executado corretamente (Erro)
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Verifica se o status de erro é 409 que significa Conflit
                            if (error.networkResponse.statusCode == 409) {
                                try {
                                    //Recebe a informação em json da mensagem, code, status...
                                    final String errorMessage = new String(error.networkResponse.data, "UTF-8");

                                    //Cria uma lista com a informação do erro dividindo por ","
                                    String[] errorMessageData = errorMessage.split(",");

                                    //Divide a chave:valor do code numa lista em que a posição 0 é
                                    // a chave e a posição 1 é o número correspondente ao code
                                    String[] errorCode = errorMessageData[2].split(":");

                                    //Cria a mensagem de erro que vai ser verificado se o code do erro é 0,
                                    // que significa email em uso
                                    String mensagemFinalDeErro;
                                    if (Integer.parseInt(errorCode[1]) == 0) {
                                        mensagemFinalDeErro = context.getResources().getString(R.string.EmailInUse);
                                    } else {
                                        mensagemFinalDeErro = context.getResources().getString(R.string.Error);
                                    }
                                    //Mostra a mensagem de erro
                                    Toast.makeText(context, mensagemFinalDeErro, Toast.LENGTH_SHORT).show();

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //Mostra a mensagem de erro se o status não for 409
                                Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // Adicionar pedido à fila
            queue.add(jsonObjectRequest);
        }
    }
}