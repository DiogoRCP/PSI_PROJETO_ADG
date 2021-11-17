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

public class CarSingleton {
    ArrayList<Car> cars;

    private static CarSingleton instancia = null;

    public static synchronized CarSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CarSingleton(context);
        }
        return instancia;
    }

    public CarSingleton(Context context) {
        CarregarListaCarros(context);
    }

    private void CarregarListaCarros(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:8080/api/cars/carsuser?access-token="+LoginSingleton.getInstance(context, "", "").getLogin().getToken();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        cars = new ArrayList<Car>();

                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject resp = response.getJSONObject(i);
                                Car car = (Car) Json_Objects_Convertor.objectjsonConvert(resp, Car.class);
                                cars.add(car);
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

    public ArrayList<Car> getCars() {
        return cars;
    }
}