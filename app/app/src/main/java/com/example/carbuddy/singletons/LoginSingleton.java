package com.example.carbuddy.singletons;

import static com.example.carbuddy.utils.libs.ACCESSTOKEN;
import static com.example.carbuddy.utils.libs.IP;
import static com.example.carbuddy.utils.libs.isInternetConnection;
import static com.example.carbuddy.utils.libs.objectjsonConvert;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.R;
import com.example.carbuddy.controllers.AccountFragment;
import com.example.carbuddy.controllers.MainActivity;
import com.example.carbuddy.listeners.LoginListener;
import com.example.carbuddy.models.Login;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.utils.libs;

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

    /** synchronized para ser Responsável por fazer com que se crie só uma unica vez a instância
     Caso haja uma instância, a mesma é retornada se não vai para o construtor **/
    public static synchronized LoginSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new LoginSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    /** Construtor da Class LoginSingleton **/
    public LoginSingleton(Context context) {
        database = new ModeloBDHelper(context);
        if (database.getAllLogin().size() > 0) {
            login = database.getAllLogin().getFirst();
        }
    }

    /** Função que realiza o login **/
    public void apiLogin(Context context, final String user, final String pass) throws JSONException {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de LOGIN
        else {
            // cria um request em volley
            RequestQueue queue = Volley.newRequestQueue(context);
            // indicação do URL do endpoint da API
            final String URL_LOGIN = IP + "login/do";
            // Colocar os dados do POST no jsonObject
            JSONObject loginData = new JSONObject();
            loginData.put("username", user);
            loginData.put("password", pass);

            // cria um request JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, URL_LOGIN, loginData, new Response.Listener<JSONObject>() {
                        // Quando o pedido é executado corretamente
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //Receber os dados do Login
                                login = (Login) objectjsonConvert(response.getJSONObject("user"), Login.class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            loginListener.onValidateLogin(login);
                        }
                    }, new Response.ErrorListener() {
                        // Quando o pedido não é executado corretamente (Mostrar Erro)
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String message = "";
                            if(error.networkResponse != null) {
                                switch (error.networkResponse.statusCode) {
                                    case 401:
                                        message = context.getResources().getString(R.string.FailedLogin);
                                        break;
                                    default:
                                        message = context.getResources().getString(R.string.Error);
                                }

                            }else{
                                message = context.getResources().getString(R.string.NoConnection);
                            }
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Adicionar pedido à fila
            volleyQueue.add(jsonObjectRequest);
        }
    }

    /** Função que recebe dados da conta **/
    public void apiAccount(Context context) {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!libs.isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de obtenção de dados da conta
        else {
            // indicação do URL do endpoint da API
            final String URL_LOGIN = IP + "user/account" + ACCESSTOKEN(context);
            // cria um request JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET,
                            URL_LOGIN,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //Guarda os dados do login e converte os dados de json para objeto
                                    login = (Login) objectjsonConvert(response, Login.class);
                                    //Chama o login listener
                                    loginListener.onValidateLogin(login);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Mensagem de erro
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Adicionar pedido à fila
            volleyQueue.add(jsonObjectRequest);
        }
    }

    /** Obter dados do Login **/
    public Login getLogin() {
        return login;
    }

    /** Chamar o listener do login **/
    public void setLoginListener(MainActivity mainActivity) {
        this.loginListener = mainActivity;
    }

    /** Chamar o listener do edit account **/
    public void setLoginListenerAccount(AccountFragment accountFragment) {
        this.loginListener = accountFragment;
    }
}