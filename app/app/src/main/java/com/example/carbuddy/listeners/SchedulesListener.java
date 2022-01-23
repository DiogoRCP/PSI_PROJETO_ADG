package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Schedule;

import java.util.ArrayList;

/** Ficar à escuta para atualizar a lista de Schedules **/
public interface SchedulesListener {
    void onRefreshSchedules(final ArrayList<Schedule> schedules);
    //Dispara quando queremos apagar um agendamento e carregamos yes no pop up de delete confirmation
    void onDeleteCreateSchedule();
}
