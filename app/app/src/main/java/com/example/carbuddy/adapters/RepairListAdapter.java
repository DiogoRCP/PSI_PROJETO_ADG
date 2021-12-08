package com.example.carbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Repair;

import java.text.BreakIterator;
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
        holder.textViewRepairDate.setText(listaRepairs.get(position).getRepairDate());
        holder.textViewRepairType.setText(listaRepairs.get(position).getRepairtype());
        holder.textViewRepairDescp.setText(listaRepairs.get(position).getRepairDescription());
        holder.textViewRepairKm.setText(listaRepairs.get(position).getKilometers());
        holder.textViewRepairState.setText(listaRepairs.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return listaRepairs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewRepairDate, textViewRepairType, textViewRepairDescp, textViewRepairKm, textViewRepairState;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRepairDate = (TextView) itemView.findViewById(R.id.textViewRepairDate);
            textViewRepairType = (TextView) itemView.findViewById(R.id.textViewRepairType);
            textViewRepairDescp = (TextView) itemView.findViewById(R.id.textViewRepairDescp);
            textViewRepairKm = (TextView) itemView.findViewById(R.id.textViewRepairKm);
            textViewRepairState = (TextView) itemView.findViewById(R.id.textViewRepairState);
        }
    }
}
