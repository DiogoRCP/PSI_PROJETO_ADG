package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Repair;

import java.util.ArrayList;

public interface RepairsListener {
        void onRefreshCompanies(final ArrayList<Repair> repairs);
    }
