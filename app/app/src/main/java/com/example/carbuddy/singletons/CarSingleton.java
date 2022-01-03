package com.example.carbuddy.singletons;

import static com.example.carbuddy.utils.Json_Objects_Convertor.IP;
import static com.example.carbuddy.utils.Json_Objects_Convertor.isInternetConnection;
import static com.example.carbuddy.utils.Json_Objects_Convertor.objectjsonConvert;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.R;
import com.example.carbuddy.controllers.RepairFragment;
import com.example.carbuddy.controllers.fragment_carInfo;
import com.example.carbuddy.controllers.fragment_form_car;
import com.example.carbuddy.controllers.fragment_garage;
import com.example.carbuddy.listeners.CarsListener;
import com.example.carbuddy.listeners.RepairsListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.models.Repair;
import com.example.carbuddy.utils.DeleteConfirmationDialogFragment;
import com.example.carbuddy.utils.Json_Objects_Convertor;
import com.google.gson.JsonObject;

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
    private RepairsListener repairsListener = null;

    public static synchronized CarSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CarSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    public CarSingleton(Context context) {
        database = new ModeloBDHelper(context);
        cars = new ArrayList<>();
        for (Car dbCar: database.getAllCars()) {
            ArrayList<Repair> carRepairs = new ArrayList<>();
            carRepairs.addAll(database.getAllRepairs(dbCar.getId()));
            dbCar.setRepairs(carRepairs);
            cars.add(dbCar);
        }
    }

    public void CarregarListaCarros(Context context) {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        } else {
            String url = IP + "cars/carsuser?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            cars.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject resp = response.getJSONObject(i);
                                    Car car = (Car) objectjsonConvert(resp, Car.class);
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

    public void CarregarListaRepairs(Context context, int carPosition) {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "repairs/history/" + cars.get(carPosition).getId() + "?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ArrayList<Repair> repairs = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject resp = response.getJSONObject(i);
                                    Repair repair = (Repair) objectjsonConvert(resp, Repair.class);
                                    repairs.add(repair);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            cars.get(carPosition).setRepairs(repairs);

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

    public void DeleteCar(Context context, int carId) {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "cars/" + carId + "?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                switch (error.networkResponse.statusCode) {
                                    case 200:
                                        carsListener.onDeleteCreateCar();
                                        break;
                                    case 500:
                                        Toast.makeText(context, R.string.NotDeleteCar, Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception ex){
                                carsListener.onDeleteCreateCar();
                            }
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public void AddCar(Context context, Car car) throws JSONException {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "cars/post?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();

            JSONObject carData = new JSONObject();
            carData.put("vin", car.getVin());
            carData.put("brand", car.getBrand());
            carData.put("model", car.getModel());
            carData.put("color", car.getColor());
            carData.put("carType", car.getCartype());
            carData.put("fuelType", car.getFueltype());
            carData.put("registration", car.getRegistration());
            carData.put("modelyear", car.getModelyear());
            carData.put("kilometers", car.getKilometers());
            carData.put("displacement", car.getDisplacement());
            carData.put("state", car.getState());

            System.out.println(carData);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, carData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            carsListener.onDeleteCreateCar();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.toString());
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCarsListener(fragment_garage fragment){
        this.carsListener = fragment;
    }

    public ArrayList<Repair> getRepairs(int carId){
        return cars.get(carId).getRepairs();
    }

    public void setRepairsListener(RepairFragment fragment){
        this.repairsListener = fragment;
    }

    public void setDeleteListener(fragment_carInfo fragment){
        this.carsListener = fragment;
    }

    public void setCreateListener(fragment_form_car fragment){
        this.carsListener = fragment;
    }
}