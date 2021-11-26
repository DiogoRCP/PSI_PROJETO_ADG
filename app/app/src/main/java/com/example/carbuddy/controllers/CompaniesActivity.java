package com.example.carbuddy.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.CarListAdapter;
import com.example.carbuddy.adapters.CompanyListAdapter;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.CarSingleton;
import com.example.carbuddy.models.CompaniesSingleton;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.ModeloBDHelper;

import java.util.ArrayList;

public class CompaniesActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private ArrayList<Company> lstCompany;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_companies);

        lstCompany = CompaniesSingleton.getInstance(this).getCompanies();

        /*ModeloBDHelper database = new ModeloBDHelper(this);

        database.insertCompanies(lstCompany.get(0));
        System.out.println(database.getAllCompanies().toString());*/


        myRecyclerView = (RecyclerView) this.findViewById(R.id.RecyclerViewCompanies);
        CompanyListAdapter listaAdapter = new CompanyListAdapter(this, lstCompany);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(listaAdapter);

    }
}
