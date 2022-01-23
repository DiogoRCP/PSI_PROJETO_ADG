package com.example.carbuddy.controllers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.CompanyListAdapter;
import com.example.carbuddy.listeners.CompaniesListener;
import com.example.carbuddy.singletons.CompaniesSingleton;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.ModeloBDHelper;

import java.util.ArrayList;

/** extends Fragment - herança de classe do Fragmento
 * implements CompaniesListener - implementação do Listener
 * */
public class CompaniesActivity extends AppCompatActivity implements CompaniesListener {

    /** Definição das variáveis globais*/
    private RecyclerView myRecyclerView;
    private ArrayList<Company> lstCompany;
    private ModeloBDHelper database;

    /** Função onCreate
     * - provém da extensão do AppCompatActivity
     * - Gerar a view e tudo o que é visual
     * - Conexão à base de dados
     * - Declaração da RecyclerView
     * - Atribuição do adaptador
     * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_companies);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.Companies);
        getSupportActionBar().setSubtitle(null);

        database = new ModeloBDHelper(this);

        //Define o fragmento onde é disparado o listener
        CompaniesSingleton.getInstance(this).setCompaniesListener(this);

        //Carregar a Singleton com os Dados da API
        CompaniesSingleton.getInstance(this).CarregarListaCompanies(this);

        //Carrega os dados do Singleton provenientes da base de dados
        lstCompany = CompaniesSingleton.getInstance(this).getCompanies();

        myRecyclerView = (RecyclerView) this.findViewById(R.id.RecyclerViewCompanies);
        CompanyListAdapter listaAdapter = new CompanyListAdapter(this, lstCompany);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(listaAdapter);

    }

    /** - Função que permite gerar uma ação ao clicar no item do menu
     *  - Identificação do item do menu
     *  - Aceder ao main page
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /** Função onRefreshCompanies
     * Vai buscar as companies à base de dados
     * Mostra os dados na recyclerview
     * */
    @Override
    public void onRefreshCompanies(ArrayList<Company> companies) {
        for (Company company: companies) {
            database.insertCompanies(company);
        }
        lstCompany = CompaniesSingleton.getInstance(this).getCompanies();
        myRecyclerView.setAdapter(new CompanyListAdapter(this, lstCompany));
    }
}
