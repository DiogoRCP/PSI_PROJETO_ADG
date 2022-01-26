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

/** extends Fragment - herança de classe do Fragmento
 * */
public class Pagina_Inicial extends AppCompatActivity {

    /** Definição das variáveis globais*/
    private FragmentManager fragmentManager;
    private Fragment fragment;
    public int fragmentNumber;

    /** Função onCreate
     * - provém da extensão do AppCompatActivity
     * - Gerar a view e tudo o que é visual
     * - Declaração do fragmento inicial
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Declarar o fragmento inicial
        setContentView(R.layout.activity_pagina_inicial);

        //Instanciar botão de back
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setIcon(R.drawable.ic_action_back);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            fragmentManager = getSupportFragmentManager();
            CarregarFragmentoInicial();
        }

        //Criar as notificações vindas do MQTT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelRepair = new NotificationChannel("VehicleRepair", "VehicleRepair", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelSchedule = new NotificationChannel("VehicleSchedule", "VehicleSchedule", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelRepair);
            manager.createNotificationChannel(channelSchedule);
        }

        //Conectar os canais do MQTT
        connectionMQTTRepair(this);
        connectionMQTTSchedule(this);
    }

    /**Criação do menu lateral(inflater, chamar o menu)*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** - Função que permite gerar uma ação voltar atrás (pops back)
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Both navigation bar back press and title bar back press will trigger this method
     * Botão de retornar à página anterior*/
    @Override
    public void onBackPressed() {
        fragmentManager.popBackStack();
    }

    /** Ao clicar no botão Garagem, leva-nos para o fragmento garage*/
    public void onClickGaragem(View view) {
        fragment = new fragment_garage();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("garage")
                .commit();
    }

    /** Ao clicar no botão Account, leva-nos para o fragmento account*/
    public void onClickAccount(View view) {
        fragment = new AccountFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("Account")
                .commit();
    }

    /** Ao clicar no botão Schedules, leva-nos para o fragmento schedules*/
    public void onClickSchedules(View view) {
        fragment = new fragment_schedules();

        fragmentManager.beginTransaction().
                replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("schedules")
                .commit();
    }

    /** Função responsável por definir o fragmento inicial, colocar o titulo etc*/
    public void CarregarFragmentoInicial() {
        fragmentNumber = 1;
        fragment = new fragment_garage();
        //Definir o titulo
        getSupportActionBar().setTitle(R.string.Garage);
        //Botão selecionado no menu principal(para mudar a cor do botão)
        SelectedMainMenu(this, R.id.btMainGarage);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    /** Função onResume, conectar os canais do MQTT
     *  */
    @Override
    protected void onResume() {
        super.onResume();
        connectionMQTTRepair(this);
        connectionMQTTSchedule(this);
    }

    /** Ao clicar no botão Companies, presente no menu, leva-nos para a atividade companies*/
    public void onClickCompaniesMenu(MenuItem item) {
        Intent companiesView = new Intent(this, CompaniesActivity.class);
        startActivity(companiesView);
    }

    /** Ao clicar no botão Logout, presente no menu,
     * termina a sessão, apaga a base de dados e leva-nos para a pánina de Login*/
    public void onClickLogoutMenu(MenuItem item) {
        this.deleteDatabase("carbuddy");
        this.finish();
    }
}