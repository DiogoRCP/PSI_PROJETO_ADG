package com.example.carbuddy.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Signup;

import org.json.JSONException;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, nif, phonenumber, password, passwordR;
    private DatePicker birsthday;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public void btSignup(View view) throws JSONException {
        if (Signup.PasswordVerify(password.getText().toString(), passwordR.getText().toString())) {

            Signup form = new Signup(
                    username.getText().toString(),
                    email.getText().toString(),
                    nif.getText().toString(),
                    birsthday.getYear()+"/"+(birsthday.getMonth()+1)+"/"+birsthday.getDayOfMonth(),
                    phonenumber.getText().toString(),
                    password.getText().toString()
            );

            form.DoSignup(this);
            Toast.makeText(getApplicationContext(), "Account Created Successfully. You can start a session.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            //Mensagem de erro a endicar que as passwors não correspondem
            Toast.makeText(getApplicationContext(), "Password and its Replay are different.", Toast.LENGTH_SHORT).show();
        }
    }

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
}
