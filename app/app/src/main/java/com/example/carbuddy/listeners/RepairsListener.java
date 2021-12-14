package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Repair;

import java.util.ArrayList;

public interface RepairsListener {
        void onRefreshRepair(final ArrayList<Repair> repairs);
    }
