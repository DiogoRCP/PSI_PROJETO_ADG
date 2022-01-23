package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Company;

import java.util.ArrayList;

/** Ficar à escuta para atualizar a lista de Companies **/
public interface CompaniesListener {
    void onRefreshCompanies(final ArrayList<Company> companies);
}
