package com.example.carbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Repair;

import java.util.ArrayList;

public class RepairListAdapter extends RecyclerView.Adapter<RepairListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Repair> listaRepairs;

    public RepairListAdapter(Context context, ArrayList<Repair> listaRepairs) {
        this.context = context;
        this.listaRepairs = listaRepairs;
    }

    @NonNull
    @Override
    public RepairListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_repair, parent, false);
        RepairListAdapter.MyViewHolder vHolder = new RepairListAdapter.MyViewHolder(v);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RepairListAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
