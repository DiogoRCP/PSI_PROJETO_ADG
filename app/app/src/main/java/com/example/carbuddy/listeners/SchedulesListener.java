package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Schedule;

import java.util.ArrayList;

public interface SchedulesListener {
    void onRefreshSchedules(final ArrayList<Schedule> schedules);
    void onDeleteCreateSchedule();
}
