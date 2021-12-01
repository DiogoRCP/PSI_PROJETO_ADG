package com.example.carbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Company;

import java.util.ArrayList;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Company> listaCompanies;

    public CompanyListAdapter(Context context, ArrayList<Company> listaCompanies) {
        this.context = context;
        this.listaCompanies = listaCompanies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_company, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewCompanyNameValue.setText(listaCompanies.get(position).getCompanyName());
    }


    @Override
    public int getItemCount() {
        return listaCompanies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCompanyNameValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCompanyNameValue = (TextView) itemView.findViewById(R.id.textViewCompanyNameValue);
        }
    }
}
