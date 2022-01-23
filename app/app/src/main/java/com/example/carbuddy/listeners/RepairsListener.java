package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Repair;

import java.util.ArrayList;

/** Ficar à escuta para atualizar a lista de Repairs **/
public interface RepairsListener {
        void onRefreshRepair(final ArrayList<Repair> repairs);
    }
