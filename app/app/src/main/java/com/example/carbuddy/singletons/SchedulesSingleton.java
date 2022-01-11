package com.example.carbuddy.singletons;

import static com.example.carbuddy.utils.Json_Objects_Convertor.IP;
import static com.example.carbuddy.utils.Json_Objects_Convertor.objectjsonConvert;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbuddy.controllers.CompaniesActivity;
import com.example.carbuddy.controllers.fragment_schedules;
import com.example.carbuddy.listeners.CompaniesListener;
import com.example.carbuddy.listeners.SchedulesListener;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.models.Schedule;
import com.example.carbuddy.utils.Json_Objects_Convertor;

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




    /** Responsável por fazer com que se crie só uma unica vez a instância
    Caso haja uma instância, a mesma é retornada **/
    public static synchronized SchedulesSingleton getInstance(Context context) {
        if (instancia == null) {
            instancia = new SchedulesSingleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    //É a função que vai preencher as schedules com os dados da base de dados
    public SchedulesSingleton(Context context) {
        database = new ModeloBDHelper(context);
        schedules = new ArrayList<Schedule>();
        for (Schedule dbSchedule: database.getAllSchedules()) {
            schedules.add(dbSchedule);
        }
    }

    public void CarregarListaSchedules(Context context) {
        if (!Json_Objects_Convertor.isInternetConnection(context)) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        } else {
            final String URL = IP + "schedules/getschedulesclient?access-token=" + LoginSingleton.getInstance(context).getLogin().getToken();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            schedules = new ArrayList<Schedule>();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject resp = response.getJSONObject(i);
                                    Schedule schedule = (Schedule) objectjsonConvert(resp, Schedule.class);
                                    schedules.add(schedule);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            schedulesListener.onRefreshSchedules(schedules);
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

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedulesListener(fragment_schedules fragment){
        this.schedulesListener = fragment;
    }
}