package com.example.carbuddy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, nif, birsthday, phonenumber, password;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.txusername);
        email = findViewById(R.id.txemail);
        nif = findViewById(R.id.txnif);
        birsthday = findViewById(R.id.txbirsthday);
        phonenumber = findViewById(R.id.txphone);
        password = findViewById(R.id.txpassword);
    }

    public void Signup(View view) {
        Form form = new Form();
        form.username = username.getText().toString();
        form.email = email.getText().toString();
        form.nif = nif.getText().toString();
        form.birsthday = birsthday.getText().toString();
        form.phonenumber = phonenumber.getText().toString();
        form.password = password.getText().toString();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String formjson = gson.toJson(form, Form.class);
        System.out.println(formjson);
        sendPost(formjson);
    }

    private class Form{
        String username;
        String email;
        String nif;
        String birsthday;
        String phonenumber;
        String password;
    }

    public void sendPost(String formjson) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://http://192.168.1.174:8080/api/signup/post");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Log.i("JSON", formjson);
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(formjson);

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
