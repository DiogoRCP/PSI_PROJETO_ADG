package com.example.carbuddy.singletons;

import static com.example.carbuddy.utils.libs.ACCESSTOKEN;
import static com.example.carbuddy.utils.libs.IP;
import static com.example.carbuddy.utils.libs.isInternetConnection;
import static com.example.carbuddy.utils.libs.objectjsonConvert;

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

    /** Responsável por fazer com que se crie só uma unica vez a instância
     Caso haja uma instância, a mesma é retornada **/
    public static synchronized CarSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CarSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    /** Construtor da Class CarSingleton **/
    public CarSingleton(Context context) {
        database = new ModeloBDHelper(context);
        cars = new ArrayList<>();
        for (Car dbCar : database.getAllCars()) {
            ArrayList<Repair> carRepairs = new ArrayList<>();
            carRepairs.addAll(database.getAllRepairs(dbCar.getId()));
            dbCar.setRepairs(carRepairs);
            cars.add(dbCar);
        }
    }

    /** Função que faz GET das carros **/
    public void CarregarListaCarros(Context context) {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de obtenção de carros
        else {
            // URL do endpoint da API
            String url = IP + "cars/carsuser" + ACCESSTOKEN(context);
            // cria um request JsonArrayRequest GET
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Limpa a lista de carros
                            cars.clear();
                            //Ciclo for para receber todos os carros e 1 a 1 adicioná-la à lista carros
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    //Receber um objeto carro na posição i
                                    JSONObject resp = response.getJSONObject(i);
                                    //Converter o json em objeto
                                    Car car = (Car) objectjsonConvert(resp, Car.class);
                                    //Adicionar o carro na posição i à lista depois de convertida para objeto
                                    cars.add(car);
                                } catch (JSONException e) {
                                    //Exception
                                    e.printStackTrace();
                                }
                            }
                            //Chamar o listener
                            carsListener.onRefreshCars(cars);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Mensagem de erro
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Adicionar pedido à fila
            volleyQueue.add(jsonArrayRequest);

        }
    }

    /** Função que faz GET das reparações de um carro **/
    public void CarregarListaRepairs(Context context, Car car) {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de obtenção de repairs
        else {
            //criação de um request
            RequestQueue queue = Volley.newRequestQueue(context);
            // URL do endpoint da API
            String url = IP + "repairs/history/" + car.getId() + ACCESSTOKEN(context);

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
                            car.setRepairs(repairs);

                            repairsListener.onRefreshRepair(repairs);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });

            queue.add(jsonArrayRequest);
        }
    }

    public void DeleteCar(Context context, int carId) {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "cars/deleted/" + carId + ACCESSTOKEN(context);

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
                                    case 403:
                                        Toast.makeText(context, R.string.NotDeleteCar, Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {
                                carsListener.onDeleteCreateCar();
                            }
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public void AddCar(Context context, Car car) throws JSONException {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "cars/post" + ACCESSTOKEN(context);

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

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, carData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            carsListener.onDeleteCreateCar();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public void EditCar(Context context, Car car) throws JSONException {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "cars/put/" + car.getId() + ACCESSTOKEN(context);

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

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, carData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            carsListener.onDeleteCreateCar();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCarsListener(fragment_garage fragment) {
        this.carsListener = fragment;
    }

    public ArrayList<Repair> getRepairs(Car car) {
        return car.getRepairs();
    }

    public void setRepairsListener(RepairFragment fragment) {
        this.repairsListener = fragment;
    }

    public void setDeleteListener(fragment_carInfo fragment) {
        this.carsListener = fragment;
    }

    public void setCreateListener(fragment_form_car fragment) {
        this.carsListener = fragment;
    }
}