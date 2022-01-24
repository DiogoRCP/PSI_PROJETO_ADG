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
import com.example.carbuddy.models.Login;
import com.example.carbuddy.models.Signup;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, nif, phonenumber, password, passwordR;
    private DatePicker birsthday;
    private Login login;
    private boolean edit;
    private Button buttonSignup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getElements();

        edit = false;

        if (getIntent().getSerializableExtra("userEdit") != null) {
            login = (Login) getIntent().getSerializableExtra("userEdit");
            editAccount();
            edit = true;
        }

        birsthday.setMaxDate(new Date().getTime());
    }

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

    public void btSignup(View view) throws JSONException {
        if (verificarCampos()) {
            if (PasswordVerify(password.getText().toString(), passwordR.getText().toString())) {

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
                        form.DoSignup(this);
                        Toast.makeText(getApplicationContext(), getString(R.string.AccountCreated), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    Signup form = new Signup(
                            email.getText().toString(),
                            password.getText().toString()
                    );

                    form.updateAccount(this);
                    Toast.makeText(getApplicationContext(), getString(R.string.AccountUpdated), Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                //Mensagem de erro a indicar que as passwors não correspondem
                passwordR.setError(getString(R.string.PassDMatch));
            }
        }
    }

    private boolean verificarCampos() {
        String[] usernameSpaces = username.getText().toString().split(" ");
        boolean error = true;
        if (usernameSpaces.length > 1) {
            username.setError(getString(R.string.FieldSpaces));
            error = false;
        }
        String[] emailSpaces = email.getText().toString().split(" ");
        if (emailSpaces.length > 1) {
            email.setError(getString(R.string.FieldSpaces));
            error = false;
        }
        String[] emailArroba = email.getText().toString().split("@");
        String[] emailPonto = (emailArroba.length == 2) ? emailArroba[1].split("\\.") : new String[0];

        if(emailArroba.length != 2 || emailPonto.length < 2){
            email.setError(getString(R.string.ValidEmail));
            error = false;
        }
        if (password.length() < 8) {
            password.setError(getString(R.string.PasswordMin));
            error = false;
        }

        return error;
    }

    private String boxesEmptyVerify(EditText box) {
        if (box.getText().toString().isEmpty()) {
            box.setError(getString(R.string.EditTextRequired));
        } else {
            return box.getText().toString();
        }
        return "";
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
