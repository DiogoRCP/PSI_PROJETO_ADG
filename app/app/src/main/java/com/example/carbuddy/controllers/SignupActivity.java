package com.example.carbuddy.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Signup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, nif, birsthday, phonenumber, password;

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
    }

    public void btSignup(View view) {
        Signup form = new Signup();
        form.setUsername(username.getText().toString());
        form.setEmail(email.getText().toString());
        form.setNif(nif.getText().toString());
        form.setBirsthday(birsthday.getText().toString());
        form.setPhonenumber(phonenumber.getText().toString());
        form.setPassword(password.getText().toString());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String formjson = gson.toJson(form, Signup.class);
        System.out.println(formjson);
        form.sendPost(formjson);
    }
}