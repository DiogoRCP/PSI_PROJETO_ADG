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

public class MainActivity extends AppCompatActivity implements LoginListener {


    private EditText editTextUser, editTextPass;
    private String user, pass;
    private ModeloBDHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        LoginSingleton.getInstance(this).setLoginListener(this);
        database = new ModeloBDHelper(this);
        editTextUser = findViewById(R.id.editTextTextPersonName);
        editTextPass = findViewById(R.id.editTextTextPassword);

        VerificarLogin();
        CompaniesSingleton.getInstance(this);

    }

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


    private boolean isUserValid(String user) {
        if (user.length() > 1 && user != "Username")
            return true;
        else
            return false;
    }

    private boolean isPassValid(String pass) {
        if (pass == null)
            return false;

        if (pass.length() < 8)
            return false;
        else
            return true;
    }


    public void onClickLogin(View view) throws JSONException {
        if (efetuarLogin()) {
            LoginSingleton.getInstance(this).apiLogin(this, user, pass);
        }
    }

    public void onClickRegister(View view) {
        Intent signup = new Intent(this, SignupActivity.class);
        startActivity(signup);
    }

    public void onClickCompanies(View view) {
        Intent companiesView = new Intent(this, CompaniesActivity.class);
        startActivity(companiesView);
    }

    private void VerificarLogin() {
        if (database.getAllLogin().size() > 0) {
            Intent paginaInicial = new Intent(this, Pagina_Inicial.class);
            startActivity(paginaInicial);
        }
    }

    @Override
    public void onValidateLogin(Login login) {
        if (login.getToken() != null) {
            database.insertLogin(login);
            Intent paginaInicial = new Intent(this, Pagina_Inicial.class);
            startActivity(paginaInicial);
        } else {
            Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume() {
        database = new ModeloBDHelper(this);
        super.onResume();
    }
}