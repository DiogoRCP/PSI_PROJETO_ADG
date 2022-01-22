package com.example.carbuddy.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.carbuddy.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class libs {

    public static final String IP = "http://10.0.2.2:8000/api/";

    public static String jsonObjectConvert(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(object);
    }

    public static Object objectjsonConvert(JSONObject json, Class objectClass){
        Object object = new Object();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        object = gson.fromJson(json.toString(), objectClass);

        return object;
    }

    public static boolean isInternetConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    /** Dá o estilo aos spinners com listas estáticas que se encontra na pasta res/layout **/
    public static void spinnerTheme(Context context, Spinner spinner, int list){
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(context, list, R.layout.color_spinner_layout);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(arrayAdapter);
    }

    /** Dá o estilo aos spinners com listas dinâmicas que se encontra na pasta res/layout **/
    public static void spinnerTheme(Context context, Spinner spinner, ArrayList<String> list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.color_spinner_layout, list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(arrayAdapter);
    }

    /** Método para dar efeito de selecionado aos botões do menu **/
    public static void SelectedMainMenu(Activity activity, int btId){
        Button btMainMenuSchedules, btMainMenuGarage, btMainMenuAccount;
        btMainMenuSchedules = activity.findViewById(R.id.btMainSchedules);
        btMainMenuGarage = activity.findViewById(R.id.btMainGarage);
        btMainMenuAccount = activity.findViewById(R.id.btMainAccount);

        btMainMenuSchedules.setBackground(null);
        btMainMenuGarage.setBackground(null);
        btMainMenuAccount.setBackground(null);

        Button btMainMenuSelected = activity.findViewById(btId);
        btMainMenuSelected.setBackgroundResource(R.drawable.button_states);
    }
}
