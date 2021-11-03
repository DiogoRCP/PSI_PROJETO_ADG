package com.example.carbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PathEffect;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    private EditText editTextUser, editTextPass;
    private Button buttonLogin;


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
     /*   buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { efetuarLogin(); }

        });*/
    }

        private boolean efetuarLogin() {
            String user = editTextUser.getText().toString();
            String pass = editTextPass.getText().toString();
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


    public void onClickLogin(View view) {
        if(efetuarLogin()==true) {
            Intent paginalinicial = new Intent(this, Pagina_Inicial.class);
            startActivity(paginalinicial);
        }
    }
}