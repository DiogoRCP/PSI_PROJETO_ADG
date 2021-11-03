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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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


    public void onClickLogin(View view ) {
        if(efetuarLogin()==true) {




            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://localhost:8080/users";

// Request a string response from the provided URL.
            //JsonObjectRequest
            //JsonArrayRequest
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Toast.makeText(MainActivity.this, response.substring(0,500), Toast.LENGTH_LONG);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG);
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);







            Intent paginalinicial = new Intent(this, Pagina_Inicial.class);
            startActivity(paginalinicial);
        }
    }
}