package com.example.carbuddy.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Signup;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, nif, phonenumber, password, passwordR;
    private DatePicker birsthday;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        getElements();
    }

    private void getElements(){
        username = findViewById(R.id.txusername);
        email = findViewById(R.id.txemail);
        nif = findViewById(R.id.txnif);
        birsthday = (DatePicker)findViewById(R.id.txbirsthday);
        phonenumber = findViewById(R.id.txphone);
        password = findViewById(R.id.txpassword);
        passwordR = findViewById(R.id.txpassword2);

    }

    public void btSignup(View view) {
        if (Signup.PasswordVerify(password.getText().toString(), passwordR.getText().toString())) {

            Signup form = new Signup(
                    username.getText().toString(),
                    email.getText().toString(),
                    nif.getText().toString(),
                    birsthday.getYear()+"/"+birsthday.getMonth()+"/"+birsthday.getDayOfMonth(),
                    phonenumber.getText().toString(),
                    password.getText().toString()
            );

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
