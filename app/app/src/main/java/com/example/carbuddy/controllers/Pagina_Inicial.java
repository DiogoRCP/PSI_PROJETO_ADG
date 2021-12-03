package com.example.carbuddy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.carbuddy.R;
import com.example.carbuddy.models.CarSingleton;
import com.example.carbuddy.models.ModeloBDHelper;

public class Pagina_Inicial extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setIcon(R.drawable.ic_action_back);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            fragmentManager = getSupportFragmentManager();
            CarregarFragmentoInicial();
        }
        CarregarSingletons();
        ModeloBDHelper database = new ModeloBDHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }else{
            System.out.println("ola");
        }
        return super.onOptionsItemSelected(item);
    }

    //Both navigation bar back press and title bar back press will trigger this method
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0 ) {
            fragmentManager.popBackStack();
            if(fragmentManager.getBackStackEntryCount() == 1){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public void onClickGaragem(View view) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragment = new fragment_garage();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("garage")
                .commit();
    }

    public void onClickSchedulesAppointment(View view) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragment = new Schedules_Appointment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("schedulesappointment")
                .commit();
    }

    public void onClickSchedules(View view) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragment = new fragment_schedules();
        fragmentManager.beginTransaction().
                replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("schedules")
                .commit();
    }

    public void onClickCar(View view) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragment = new fragment_carInfo();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("infocar")
                .commit();
    }

    public void CarregarFragmentoInicial() {
        fragment = new fragment_garage();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    private void CarregarSingletons(){
        CarSingleton.getInstance(this);
    }
}