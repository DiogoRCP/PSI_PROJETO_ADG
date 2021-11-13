package com.example.carbuddy.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.carbuddy.R;

public class Pagina_Inicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void onClickGaragem(View view) {
        Intent garagem = new Intent(this, garagem.class);
        startActivity(garagem);
    }

    public void onClickAllRepairs(View view) {
        //Intent allrepairs = new Intent(this, Repairs.class);
        //startActivity(allrepairs);
    }

    public void onClickUsers(View view) {
        //Intent user = new Intent(this, User.class);
        //startActivity(user);
    }
}