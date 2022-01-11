package com.example.carbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Schedule;

import java.util.ArrayList;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Schedule> listaSchedules;

    public ScheduleListAdapter(Context context, ArrayList<Schedule> listaSchedules) {
        this.context = context;
        this.listaSchedules = listaSchedules;
    }

    @NonNull
    @Override
    public ScheduleListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_schedule, parent, false);
        ScheduleListAdapter.MyViewHolder vHolder = new ScheduleListAdapter.MyViewHolder(v);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ScheduleListAdapter.MyViewHolder holder, int position) {
        holder.textViewSchedulingDate.setText(listaSchedules.get(position).getSchedulingDate());
        holder.textViewCompany.setText(listaSchedules.get(position).getCompanyId());
        holder.textViewCar.setText(listaSchedules.get(position).getCarId());
    }

    @Override
    public int getItemCount() {
        return listaSchedules.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewSchedulingDate, textViewCompany, textViewCar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSchedulingDate = (TextView) itemView.findViewById(R.id.textViewSchedulingDate);
            textViewCompany = (TextView) itemView.findViewById(R.id.textViewCompany);
            textViewCar = (TextView) itemView.findViewById(R.id.textViewCar);
        }
    }
}
