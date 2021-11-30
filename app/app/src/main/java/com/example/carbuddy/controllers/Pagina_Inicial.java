package com.example.carbuddy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.carbuddy.R;
import com.example.carbuddy.models.CarSingleton;
import com.example.carbuddy.models.ModeloBDHelper;

public class Pagina_Inicial extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_carbuddy_logo_svg);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            fragmentManager = getSupportFragmentManager();
            CarregarFragmentoInicial();
        }
        CarregarSingletons();
        ModeloBDHelper database = new ModeloBDHelper(this);
    }

    public void onClickGaragem(View view) {
        fragment = new fragment_garage();
        setTitle(R.string.Garage);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    public void onClickSchedulesAppointment(View view) {
        CarregarFragmentoSchedulesAppointment();
    }

    public void onClickSchedules(View view) {
        CarregarFragmentoInicial();
    }

    public void onClickCar(View view) {
        fragment = new fragment_carInfo();
        setTitle(R.string.InfoCarro);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    public void CarregarFragmentoInicial() {
        fragment = new fragment_schedules();
        setTitle(R.string.Schedules);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    public void CarregarFragmentoSchedulesAppointment() {
        fragment = new Schedules_Appointment();
        setTitle(R.string.Schedulesappointment);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    private void CarregarSingletons(){
        CarSingleton.getInstance(this);
    }
}