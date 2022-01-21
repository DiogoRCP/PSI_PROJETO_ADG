package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.MQTT.connectionMQTTRepair;
import static com.example.carbuddy.utils.MQTT.connectionMQTTSchedule;
import static com.example.carbuddy.utils.libs.SelectedMainMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.carbuddy.R;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.singletons.CompaniesSingleton;
import com.example.carbuddy.singletons.LoginSingleton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Pagina_Inicial extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    public int fragmentNumber;

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelRepair = new NotificationChannel("VehicleRepair", "VehicleRepair", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelSchedule = new NotificationChannel("VehicleSchedule", "VehicleSchedule", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelRepair);
            manager.createNotificationChannel(channelSchedule);
        }

        connectionMQTTRepair(this);
        connectionMQTTSchedule(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Both navigation bar back press and title bar back press will trigger this method
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            if (fragmentManager.getBackStackEntryCount() == 1) {
                fragmentNumber = 1;
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public void onClickGaragem(View view) {
        if (fragmentNumber != 1) {
            fragment = new fragment_garage();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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

    public void onClickAccount(View view) {
        if (fragmentNumber != 2) {
            fragment = new AccountFragment();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.Account);

            fragmentNumber = 2;

            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack("Account")
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

        SelectedMainMenu(this, R.id.btMainGarage);

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