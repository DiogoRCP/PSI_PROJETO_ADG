package com.example.carbuddy.controllers;

import static com.example.carbuddy.models.Signup.PasswordVerify;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbuddy.R;
import com.example.carbuddy.listeners.SignupListener;
import com.example.carbuddy.models.Login;
import com.example.carbuddy.models.Signup;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * extends AppCompatActivity - Funcionalidades de Activity
 */
public class SignupActivity extends AppCompatActivity implements SignupListener {

    /**
     * Definição das variáveis globais
     */
    private EditText username, email, nif, phonenumber, password, passwordR;
    private DatePicker birsthday;
    private Login login;
    private boolean edit;
    private Button buttonSignup;

    /**
     * Função onCreate
     * - provém da extensão do AppCompatActivity
     * - Gerar a view e tudo o que é visual
     * - Chamada da ActionBar
     * - Serialização
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Definir layout associado à atividade
        setContentView(R.layout.activity_signup);

        //Botão de pops back
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obter todas as referencias das textviews do layout
        getElements();

        //Edit = false quando o objetivo é criar um utilizador
        edit = false;

        //Se for recebido um intent ao entrar na activity o objetivo é atualizar a sua password e email edit=true
        if (getIntent().getSerializableExtra("userEdit") != null) {
            login = (Login) getIntent().getSerializableExtra("userEdit");
            //chamar a função edit acount que vai tornar algumas labels e text views invisiveis
            editAccount();
            edit = true;
        }
        //Definir data maxima para o dia de hoje
        birsthday.setMaxDate(new Date().getTime());
    }

    /**
     * Definição das labels a aparecer na edição de conta
     * - Definição do botão e respetiva string para edição de conta
     */
    private void editAccount() {
        setTitle(R.string.EditAccount);

        email.setText(login.getEmail());
        username.setVisibility(View.GONE);
        nif.setVisibility(View.GONE);
        birsthday.setVisibility(View.GONE);
        phonenumber.setVisibility(View.GONE);
        findViewById(R.id.lbBirthday).setVisibility(View.GONE);
        buttonSignup.setText(R.string.EditAccount);
    }

    /**
     * Obtenção dos elementos para as textviews
     */
    private void getElements() {
        username = findViewById(R.id.txusername);
        email = findViewById(R.id.txemail);
        nif = findViewById(R.id.txnif);
        birsthday = (DatePicker) findViewById(R.id.txbirsthday);
        phonenumber = findViewById(R.id.txphone);
        password = findViewById(R.id.txpassword);
        passwordR = findViewById(R.id.txpassword2);
        buttonSignup = findViewById(R.id.btnSignup);
    }

    /**
     * Configuração do botão de SignUp consoante os dados inseridos nas labels: verificações de dados
     */
    public void btSignup(View view) throws JSONException {
        //Verificar se os campos estão preenchidos corretamente
        if (verificarCampos()) {
            //Verificar se as passwords e a sua repetição são iguais
            if (PasswordVerify(password.getText().toString(), passwordR.getText().toString())) {

                //Registo do user
                if (!edit) {
                    Signup form = new Signup(
                            username.getText().toString(),
                            email.getText().toString(),
                            nif.getText().toString(),
                            birsthday.getYear() + "/" + (birsthday.getMonth() + 1) + "/" + birsthday.getDayOfMonth(),
                            phonenumber.getText().toString(),
                            password.getText().toString()
                    );
                    if (!boxesEmptyVerify(username).isEmpty() &&
                            !boxesEmptyVerify(email).isEmpty() &&
                            !boxesEmptyVerify(nif).isEmpty() &&
                            !boxesEmptyVerify(phonenumber).isEmpty() &&
                            !boxesEmptyVerify(password).isEmpty()
                    ) {
                        //Singleton POST user
                        form.DoSignup(this);
                    }

                    //Edit do user
                } else {
                    Signup form = new Signup(
                            email.getText().toString(),
                            password.getText().toString()
                    );
                    //Singleton PUT user
                    form.updateAccount(this);
                }
            } else {
                //Mensagem de erro a indicar que as passwors não correspondem
                passwordR.setError(getString(R.string.PassDMatch));
            }
        }
    }

    /**
     * Verificação e atribuição de mensagens de erro a campos vazios
     */
    private boolean verificarCampos() {
        String[] usernameSpaces = username.getText().toString().split(" ");
        boolean error = true;
        //verificar se o useername esta bem preenchido, com mais que 1 caracter
        if (usernameSpaces.length > 1) {
            username.setError(getString(R.string.FieldSpaces));
            error = false;
        }
        String[] emailSpaces = email.getText().toString().split(" ");
        //Verificar se o email tem espaços
        if (emailSpaces.length > 1) {
            email.setError(getString(R.string.FieldSpaces));
            error = false;
        }
        String[] emailArroba = email.getText().toString().split("@");
        String[] emailPonto = (emailArroba.length == 2) ? emailArroba[1].split("\\.") : new String[0];
        //Verifica se o email é valido
        if (emailArroba.length != 2 || emailPonto.length < 2) {
            email.setError(getString(R.string.ValidEmail));
            error = false;
        }
        //Só no caso de ser edit é possivel deixar a password vazia para não a editar
        if(!edit) {
            if (password.length() < 8) {
                password.setError(getString(R.string.PasswordMin));
                error = false;
            }
        }
        // se o objetivo for criar um user verifica se a password tem 8 caracteres
        else{
            if (password.length() > 0 && password.length() < 8) {
                password.setError(getString(R.string.PasswordMin));
                error = false;
            }
        }

        return error;
    }

    /**
     * Verificação e atribuição de mensagens de erro a campos vazios
     */
    private String boxesEmptyVerify(EditText box) {
        if (box.getText().toString().isEmpty()) {
            box.setError(getString(R.string.EditTextRequired));
        } else {
            return box.getText().toString();
        }
        return "";
    }

    /**
     * - Função que permite gerar uma ação ao clicar no item do menu
     * - Identificação do item do menu
     * - Aceder ao main page
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Função que é disparada quando o Post ou PUT do signup é feito na api
    @Override
    public void onSignup(boolean create) {
        //Verificar se foi feito um post
        if (create) {
            //Mostrar mensagem de conta criada com sucesso
            Toast.makeText(getApplicationContext(), getString(R.string.AccountCreated), Toast.LENGTH_SHORT).show();
            //Fechar activity signup e regressar ao login
            finish();
            //Caso tenha sido feito um put
        } else {
            //Mostrar mensagem de conta atualizada com sucesso
            Toast.makeText(getApplicationContext(), getString(R.string.AccountUpdated), Toast.LENGTH_SHORT).show();
            //Fechar activity signup e regressar ao login
            finish();
        }
    }
}
