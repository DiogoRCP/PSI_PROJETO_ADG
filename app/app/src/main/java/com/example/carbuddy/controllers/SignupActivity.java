package com.example.carbuddy.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Signup;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, nif, birsthday, phonenumber, password, passwordR;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        getElements();
    }

    private void getElements(){
        username = findViewById(R.id.txusername);
        email = findViewById(R.id.txemail);
        nif = findViewById(R.id.txnif);
        birsthday = findViewById(R.id.txbirsthday);
        phonenumber = findViewById(R.id.txphone);
        password = findViewById(R.id.txpassword);
        passwordR = findViewById(R.id.txpassword2);

    }


    public void btSignup(View view) {
        //Verificação das passwords para verificar se ambas são iguais
        String password1 = null;
        String password2 = null;
        //Toast.makeText(getApplicationContext(), "Password:" +password1+ "Repetição:" +password2, Toast.LENGTH_SHORT).show();

        password1 = password.getText().toString();
        password2 = passwordR.getText().toString();
        //Toast.makeText(getApplicationContext(), "Password:" +password1+ "Repetição:" +password2, Toast.LENGTH_SHORT).show();

        if (password1.matches(password2)) {
            //Toast.makeText(getApplicationContext(), "Password:" +password1+ "Repetição:" +password2, Toast.LENGTH_SHORT).show();
            //Inicio do Post de Registo do Cliente
            Signup form = new Signup();
            form.setUsername(username.getText().toString());
            form.setEmail(email.getText().toString());
            form.setNif(nif.getText().toString());
            form.setBirsthday(birsthday.getText().toString());
            form.setPhonenumber(phonenumber.getText().toString());
            form.setPassword(password.getText().toString());

            form.DoSignup();
            Toast.makeText(getApplicationContext(), "Conta Criada com Sucesso. Pode iniciar sessão", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, MainActivity.class);
            startActivity(login);
        }
        else {
            //Mensagem de erro a endicar que as passwors não correspondem
            Toast.makeText(getApplicationContext(), "Password e a sua Repetição são diferentes", Toast.LENGTH_SHORT).show();
        }
    }
}