package com.example.carbuddy.singletons;

import static com.example.carbuddy.utils.libs.IP;
import static com.example.carbuddy.utils.libs.objectjsonConvert;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.R;
import com.example.carbuddy.controllers.CompaniesActivity;
import com.example.carbuddy.controllers.Schedules_Appointment;
import com.example.carbuddy.listeners.CompaniesListener;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.utils.libs;

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

    // indicação do URL do endpoint da API
    private final String URL = IP + "companieslist";


    /** Responsável por fazer com que se crie só uma unica vez a instância
    Caso haja uma instância, a mesma é retornada **/
    public static synchronized CompaniesSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new CompaniesSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    /** Construtor da Class CompaniesSingleton **/
    public CompaniesSingleton(Context context) {
        database = new ModeloBDHelper(context);
        companies = new ArrayList<Company>();
        for (Company dbCompany: database.getAllCompanies()) {
            companies.add(dbCompany);
        }
    }

    /** Função que faz GET das companies **/
    public void CarregarListaCompanies(Context context) {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!libs.isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de obtenção de companies
        else {
            // cria um request JsonArrayRequest GET com indicação do URL do endpoint da API
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //criação de uma lista de empresas para guardar as empresas recebidas
                            companies = new ArrayList<Company>();
                            //Ciclo for para receber todas as empresas e 1 a 1 adicioná-la à lista companies
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    //Receber um objeto company na posição i
                                    JSONObject resp = response.getJSONObject(i);
                                    //Converter o json em objeto
                                    Company company = (Company) objectjsonConvert(resp, Company.class);
                                    //Adicionar a company na posição i à lista depois de convertida para objeto
                                    companies.add(company);
                                } catch (JSONException e) {
                                    //Exception
                                    e.printStackTrace();
                                }
                            }
                            //Chamar o listener para atualizar as empresas após o pedido
                            companiesListener.onRefreshCompanies(companies);
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

    /** Obter todas as companies em arraylist **/
    public ArrayList<Company> getCompanies() {
        return companies;
    }

    /** Chamar o listener companies **/
    public void setCompaniesListener(CompaniesActivity activity){
        this.companiesListener = activity;
    }

    /** Chamar o listener do setCompaniesOnSchedulingListener **/
    public void setCompaniesOnSchedulingListener(Schedules_Appointment fragment){
        this.companiesListener = fragment;
    }
}