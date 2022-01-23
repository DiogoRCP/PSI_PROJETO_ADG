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
import com.example.carbuddy.controllers.CompaniesActivity;
import com.example.carbuddy.controllers.Schedules_Appointment;
import com.example.carbuddy.controllers.fragment_schedules;
import com.example.carbuddy.listeners.CompaniesListener;
import com.example.carbuddy.listeners.SchedulesListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.models.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SchedulesSingleton {
    ArrayList<Schedule> schedules;
    ModeloBDHelper database;

    private static SchedulesSingleton instancia = null;

    public static RequestQueue volleyQueue = null;

    private SchedulesListener schedulesListener = null;

    /** synchronized para ser Responsável por fazer com que se crie só uma unica vez a instância
     Caso haja uma instância, a mesma é retornada se não vai para o construtor **/
    public static synchronized SchedulesSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new SchedulesSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    /** Construtor da Class SchedulesSingleton **/
    public SchedulesSingleton(Context context) {
        database = new ModeloBDHelper(context);
        schedules = new ArrayList<Schedule>();
        for (Schedule dbSchedule: database.getAllSchedules()) {
            schedules.add(dbSchedule);
        }
    }

    /** Função que faz GET das schedules **/
    public void CarregarListaSchedules(Context context) {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de obtenção de schedules
        else {
            // indicação do URL do endpoint da API
            final String URL = IP + "schedules/getschedulesclient" + ACCESSTOKEN(context);
            // cria um request JsonArrayRequest
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //criação de uma lista de schedules para guardar as schedules recebidas
                            schedules = new ArrayList<Schedule>();
                            //Ciclo for para receber todas as schedules e 1 a 1 adicioná-la à lista schedules
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    //Receber um objeto schedule na posição i
                                    JSONObject resp = response.getJSONObject(i);
                                    //Converter o json em objeto
                                    Schedule schedule = (Schedule) objectjsonConvert(resp, Schedule.class);
                                    //Adicionar a schedule na posição i à lista depois de convertida para objeto
                                    schedules.add(schedule);
                                } catch (JSONException e) {
                                    //Exception
                                    e.printStackTrace();
                                }
                            }
                            //Chamar o listener
                            schedulesListener.onRefreshSchedules(schedules);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, R.string.NoConnection, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Adicionar pedido à fila
            volleyQueue.add(jsonArrayRequest);
        }
    }

    /** Função que faz POST de uma schedule **/
    public void AddSchedule(Context context, Schedule schedule) throws JSONException {
        // Se não houver conexão à internet mostra mensagem de erro
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
        // Se houver internet executa o código de POST de schedules
        else {
            //Criação de um novo request
            RequestQueue queue = Volley.newRequestQueue(context);
            // indicação do URL do endpoint da API
            String url = IP + "schedules/post" + ACCESSTOKEN(context);
            //Criação de um jsonobject com os dados provenientes do fragment shcedules appointmenmt
            JSONObject scheduleData = new JSONObject();
            scheduleData.put("schedulingdate", schedule.getSchedulingdate());
            scheduleData.put("repairdescription", schedule.getRepairdescription());
            scheduleData.put("repairtype", schedule.getRepairtype());
            scheduleData.put("carId", schedule.getCarId());
            scheduleData.put("companyId", schedule.getCompanyId());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, scheduleData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            schedulesListener.onDeleteCreateSchedule();
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

    /** Função que faz PUT de uma schedule **/
    public void PutSchedule(Context context, Schedule schedule) throws JSONException {
        if (!isInternetConnection(context)) {
            Toast.makeText(context, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = IP + "schedules/putclient/" + schedule.getId() + ACCESSTOKEN(context);

            JSONObject scheduleData = new JSONObject();
            scheduleData.put("schedulingdate", schedule.getSchedulingdate());
            scheduleData.put("repairdescription", schedule.getRepairdescription());
            scheduleData.put("repairtype", schedule.getRepairtype());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, scheduleData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            schedulesListener.onDeleteCreateSchedule();
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

    /** Obter todas as schedules em arraylist **/
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    /** Chamar o listener do schedules **/
    public void setSchedulesListener(fragment_schedules fragment){
        this.schedulesListener = fragment;
    }

    /** Chamar o listener do add account **/
    public void addSchedulesListener(Schedules_Appointment fragment){
        this.schedulesListener = fragment;
    }
}