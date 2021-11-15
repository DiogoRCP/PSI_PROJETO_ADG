package com.example.carbuddy.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.carbuddy.R;
import com.example.carbuddy.models.CarSingleton;

public class garagem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garagem);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Log.i("", CarSingleton.getInstance(this).getCars().toString());
    }

    public void onClickVeiculo(View view) {
        Intent carinfo = new Intent(this, InfoCarro.class);
        startActivity(carinfo);
    }
}