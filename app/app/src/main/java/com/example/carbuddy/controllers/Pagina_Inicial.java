package com.example.carbuddy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.carbuddy.R;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.singletons.CompaniesSingleton;
import com.example.carbuddy.singletons.LoginSingleton;

public class Pagina_Inicial extends AppCompatActivity{

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Menu menu;
    private int fragmentNumber;

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        getMenuInflater().inflate(R.menu.car_repair, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }else{
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
        if (fragmentNumber != 1) {
            fragment = new fragment_garage();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.Garage);

            fragmentNumber = 1;
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack("garage")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        }
    }

    public void onClickSchedulesAppointment(View view) {
        if (fragmentNumber != 2) {
            fragment = new Schedules_Appointment();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.Schedulesappointment);

            fragmentNumber = 2;
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack("schedulesappointment")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        }
    }

    public void onClickSchedules(View view) {
        if (fragmentNumber != 3) {
            fragment = new fragment_schedules();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.Schedules);

            fragmentNumber = 3;
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack("schedules")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        }
    }
    
    public void CarregarFragmentoInicial() {
        fragmentNumber = 1;
        fragment = new fragment_garage();

        getSupportActionBar().setTitle(R.string.Garage);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    public void onClickCompaniesMenu(MenuItem item) {
        Intent companiesView = new Intent(this, CompaniesActivity.class);
        startActivity(companiesView);
    }

    public void onClickLogoutMenu(MenuItem item) {
        this.deleteDatabase("carbuddy");
        this.finish();
    }
}