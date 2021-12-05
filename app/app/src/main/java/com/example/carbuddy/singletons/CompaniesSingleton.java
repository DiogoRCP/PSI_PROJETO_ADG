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
import com.example.carbuddy.controllers.CompaniesActivity;
import com.example.carbuddy.controllers.fragment_garage;
import com.example.carbuddy.listeners.CompaniesListener;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.utils.Json_Objects_Convertor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompaniesSingleton {
    ArrayList<Company> companies;
    ModeloBDHelper database;

    private static CompaniesSingleton instancia = null;

    public static RequestQueue volleyQueue = null;

    private CompaniesListener companiesListener = null;

    private final String URL = "http://10.0.2.2:8080/api/companieslist";


    /** Responsável por fazer com que se crie só uma unica vez a instância
    Caso haja uma instância, a mesma é retornada **/
    public static synchronized CompaniesSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CompaniesSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    //É a função que vai preencher as companies com os dados da base de dados
    public CompaniesSingleton(Context context) {
        database = new ModeloBDHelper(context);
        companies = new ArrayList<Company>();
        for (Company dbCompany: database.getAllCompanies()) {
            companies.add(dbCompany);
        }
    }

    public void CarregarListaCompanies(Context context) {
        if (!Json_Objects_Convertor.isInternetConnection(context)) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            companies = new ArrayList<Company>();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject resp = response.getJSONObject(i);
                                    Company company = (Company) Json_Objects_Convertor.objectjsonConvert(resp, Company.class);
                                    companies.add(company);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            companiesListener.onRefreshCompanies(companies);
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

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompaniesListener(CompaniesActivity activity){
        this.companiesListener = activity;
    }
}