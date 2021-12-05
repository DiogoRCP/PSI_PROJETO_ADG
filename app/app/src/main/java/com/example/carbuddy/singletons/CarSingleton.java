package com.example.carbuddy.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.controllers.fragment_garage;
import com.example.carbuddy.listeners.CarsListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.utils.Json_Objects_Convertor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarSingleton {
    ArrayList<Car> cars;
    ModeloBDHelper database;

    private static CarSingleton instancia = null;

    // volley
    private static RequestQueue volleyQueue = null;

    // listener
    private CarsListener carsListener = null;

    public static synchronized CarSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CarSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    public CarSingleton(Context context) {
        database = new ModeloBDHelper(context);
        cars = new ArrayList<Car>();
        for (Car dbCar: database.getAllCars()) {
            cars.add(dbCar);
        }
    }

    public void CarregarListaCarros(Context context) {
        if (!Json_Objects_Convertor.isInternetConnection(context)) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://10.0.2.2:8080/api/cars/carsuser?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            cars.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject resp = response.getJSONObject(i);
                                    Car car = (Car) Json_Objects_Convertor.objectjsonConvert(resp, Car.class);
                                    cars.add(car);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            carsListener.onRefreshCars(cars);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            volleyQueue.add(jsonArrayRequest);

        }
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCarsListener(fragment_garage fragment){
        this.carsListener = fragment;
    }
}