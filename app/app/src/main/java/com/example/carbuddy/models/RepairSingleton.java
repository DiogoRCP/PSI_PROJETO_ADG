package com.example.carbuddy.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.controllers.Json_Objects_Convertor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RepairSingleton {
    ArrayList<Repair> repairs;

    private static RepairSingleton instancia = null;

    public static synchronized RepairSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new RepairSingleton(context);
        }
        return instancia;
    }

    public RepairSingleton(Context context) {
        CarregarListaRepairs(context);
    }

    private void CarregarListaRepairs(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:8080/api/cars/carsuser?access-token="+LoginSingleton.getInstance(context, "", "").getLogin().getToken();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        repairs = new ArrayList<Repair>();

                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject resp = response.getJSONObject(i);
                                Repair repair = (Repair) Json_Objects_Convertor.objectjsonConvert(resp, Repair.class);
                                repairs.add(repair);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", error.toString());
                    }
                });

        queue.add(jsonArrayRequest);
    }

    public ArrayList<Repair> getCars() {
        return repairs;
    }
}
