package com.example.carbuddy.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbuddy.R;
import com.example.carbuddy.listeners.LoginListener;
import com.example.carbuddy.singletons.CompaniesSingleton;
import com.example.carbuddy.models.Login;
import com.example.carbuddy.singletons.LoginSingleton;
import com.example.carbuddy.models.ModeloBDHelper;

import org.json.JSONException;

/** extends AppCompatActivity - Funcionalidades de Activity
 * implements LoginListener - implementação do Listener
 * */
public class MainActivity extends AppCompatActivity implements LoginListener {

    /** Definição das variáveis globais*/
    private EditText editTextUser, editTextPass;
    private String user, pass;
    private ModeloBDHelper database;

    /** Função onCreate
     * - provém da extensão do AppCompatActivity
     * - Gerar a view e tudo o que é visual
     * - Conexão à base de dados
     * - Declaração da RecyclerView
     * - Atribuição do adaptador
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        LoginSingleton.getInstance(this).setLoginListener(this);
        editTextUser = findViewById(R.id.editTextTextPersonName);
        editTextPass = findViewById(R.id.editTextTextPassword);

        VerificarLogin();
        //Instanciar a singleton comapnies caso o user queira aceder à activity companies os dados ja estarem carregados
        CompaniesSingleton.getInstance(this);

    }

    /** Função efetuarLogin
     * - Atribuição de mensagens de erro caso um ou ambos os elementos nao estejam preenchidos*/
    private boolean efetuarLogin() {
        user = editTextUser.getText().toString();
        pass = editTextPass.getText().toString();
        boolean userBool = isUserValid(user);
        boolean passBool = isPassValid(pass);

        if (!userBool)
            editTextUser.setError("Invalid User");

        if (!passBool)
            editTextPass.setError("Invalid Password");

        if (userBool && passBool) {
            return true;
        }
        return false;
    }

    /** Verificar se os dados do username estão preenchidos*/
    private boolean isUserValid(String user) {
        if (user.length() > 1 && user != "Username")
            return true;
        else
            return false;
    }

    /** Verificar se os dados da password estão preenchidos*/
    private boolean isPassValid(String pass) {
        if (pass == null)
            return false;

        if (pass.length() < 8)
            return false;
        else
            return true;
    }

    /** Botão que instancia a singleton e executa o ednpoit da API, caso os dados de login tenham sido preenchidos*/
    public void onClickLogin(View view) throws JSONException {
        if (efetuarLogin()) {
            LoginSingleton.getInstance(this).apiLogin(this, user, pass);
        }
    }

    /** Ao clicar no registo de conta, leva-nos para a SignupActivity*/
    public void onClickRegister(View view) {
        Intent signup = new Intent(this, SignupActivity.class);
        startActivity(signup);
    }

    /** Ao clicar no botão companies, leva-nos para a CompaniesActivity*/
    public void onClickCompanies(View view) {
        Intent companiesView = new Intent(this, CompaniesActivity.class);
        startActivity(companiesView);
    }

    /** Verificar se já existem dados de login na base de dados e entrar na garagem automaticamente, pois já temos o cliente/user em questão*/
    private void VerificarLogin() {
        if(LoginSingleton.getInstance(this).getLogin() != null){
            Intent paginaInicial = new Intent(this, Pagina_Inicial.class);
            startActivity(paginaInicial);
        }
    }

    /** Função chamada pelo listener no momento do login no singleton*/
    @Override
    public void onValidateLogin(Login login) {
        if (login.getToken() != null) {
            Intent paginaInicial = new Intent(this, Pagina_Inicial.class);
            startActivity(paginaInicial);
        } else {
            Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    /** Instanciar uma nova base de dados quando o programa deixa de estar em background ou quando inicia */
    protected void onResume() {
        super.onResume();
    }
}