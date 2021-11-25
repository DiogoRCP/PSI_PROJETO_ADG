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

public class CompaniesSingleton {
    ArrayList<Company> companies;

    private static CompaniesSingleton instancia = null;

    public static synchronized CompaniesSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CompaniesSingleton(context);
        }
        return instancia;
    }

    public CompaniesSingleton(Context context) {
        CarregarListaCompanies(context);
    }

    private void CarregarListaCompanies(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:8080/api/companies";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        companies = new ArrayList<Company>();

                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject resp = response.getJSONObject(i);
                                Company company = (Company) Json_Objects_Convertor.objectjsonConvert(resp, Company.class);
                                companies.add(company);
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

    public ArrayList<Company> getCompanies() {
        return companies;
    }
}