package com.example.carbuddy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.carbuddy.R;

public class Pagina_Inicial extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            fragmentManager = getSupportFragmentManager();
            CarregarFragmentoInicial();
        }
    }

    public void onClickGaragem(View view) {
        Fragment fragment = new fragment_garage();
        setTitle("Schedules");
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    public void onClickSchedulesAppointment(View view) {
        CarregarFragmentoSchedulesAppointment();
    }

    public void onClickUsers(View view) {
        CarregarFragmentoInicial();
    }

    public void CarregarFragmentoInicial() {
        Fragment fragment = new fragment_schedules();
        setTitle("Schedules");
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    public void CarregarFragmentoSchedulesAppointment() {
        Fragment fragment = new Schedules_Appointment();
        setTitle("Schedules");
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }
}