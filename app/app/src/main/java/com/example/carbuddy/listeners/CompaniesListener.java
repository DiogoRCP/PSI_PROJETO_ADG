package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Company;

import java.util.ArrayList;

public interface CompaniesListener {
    void onRefreshCompanies(final ArrayList<Company> companies);
}
