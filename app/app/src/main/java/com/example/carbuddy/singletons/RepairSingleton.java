package com.example.carbuddy.singletons;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.controllers.RepairFragment;
import com.example.carbuddy.listeners.RepairsListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.models.Repair;
import com.example.carbuddy.utils.Json_Objects_Convertor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RepairSingleton {
    ArrayList<Repair> repairs;
    ModeloBDHelper database;

    private static RepairSingleton instancia = null;

    //Volley
    public static RequestQueue volleyQueue = null;

    //Listener
    private RepairsListener repairsListener = null;

    //private final String URL = Json_Objects_Convertor.IP + "";

    public static synchronized RepairSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new RepairSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    public RepairSingleton(Context context) {
        database = new ModeloBDHelper(context);
        repairs = new ArrayList<Repair>();
        for (Repair dbRepair : database.getAllRepairs()) {
            repairs.add(dbRepair);
        }
    }

    private void CarregarListaRepairs(Context context) {
        if (!Json_Objects_Convertor.isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Json_Objects_Convertor.IP + "cars/carsuser?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            repairs = new ArrayList<Repair>();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject resp = response.getJSONObject(i);
                                    Repair repair = (Repair) Json_Objects_Convertor.objectjsonConvert(resp, Repair.class);
                                    repairs.add(repair);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            repairsListener.onRefreshRepair(repairs);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Error", error.toString());
                        }
                    });

            queue.add(jsonArrayRequest);
        }
    }

        public ArrayList<Repair> getRepairs(){
            return repairs;
        }

        public void setRepairsListener(RepairFragment fragment){
            this.repairsListener = fragment;
        }
    }
