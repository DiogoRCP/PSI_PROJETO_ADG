package com.example.carbuddy.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbuddy.R;
import com.example.carbuddy.models.LoginSingleton;

public class MainActivity extends AppCompatActivity {


    private EditText editTextUser, editTextPass;
    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        editTextUser = findViewById(R.id.editTextTextPersonName);
        editTextPass = findViewById(R.id.editTextTextPassword);
    }

        private boolean efetuarLogin() {
            user = editTextUser.getText().toString();
            pass = editTextPass.getText().toString();
            boolean userBool = isUserValid(user);
            boolean passBool = isPassValid(pass);

            if(userBool != true)
                editTextUser.setError("Invalid User");

            if (passBool != true)
                editTextPass.setError("Invalid Password");

            if(userBool == true && passBool == true) {
                return true;
            }
            return false;
        }


        private boolean isUserValid(String user){
            if(user.length() > 1 && user != "Username")
                return true;
            else
                return false;
        }

        private boolean isPassValid(String pass){
            if(pass == null)
                return false;

            if (pass.length() < 8)
                return false;
            else
                return true;
        }


    public void onClickLogin(View view ) {
        if(efetuarLogin()==true) {

            LoginSingleton.getInstance(this, user, pass);
            if(LoginSingleton.getInstance(this, user, pass).getLogin() != null) {
                if (LoginSingleton.getInstance(this, user, pass).getLogin().isEntrar() == true) {
                    Intent paginaInicial = new Intent(this, Pagina_Inicial.class);
                    startActivity(paginaInicial);
                } else {
                    Toast.makeText(this, "Conta não existente", Toast.LENGTH_SHORT).show();
                    LoginSingleton.setInstancia(null);
                }
            }
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
}