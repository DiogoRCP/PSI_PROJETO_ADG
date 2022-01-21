package com.example.carbuddy.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.controllers.Schedules_Appointment;
import com.example.carbuddy.controllers.fragment_schedules;
import com.example.carbuddy.models.Schedule;

import java.util.ArrayList;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Schedule> listaSchedules;
    private FragmentManager manager;

    public ScheduleListAdapter(Context context, ArrayList<Schedule> listaSchedules, FragmentManager manager) {
        this.context = context;
        this.listaSchedules = listaSchedules;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ScheduleListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_schedule, parent, false);
        ScheduleListAdapter.MyViewHolder vHolder = new ScheduleListAdapter.MyViewHolder(v, manager);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ScheduleListAdapter.MyViewHolder holder, int position) {
        holder.textViewSchedulingDate.setText(listaSchedules.get(position).getSchedulingdate());
        holder.textViewCompany.setText(listaSchedules.get(position).getCompanyName(context));
        holder.textViewCar.setText(listaSchedules.get(position).getCarInfo(context).get(0) + " " + listaSchedules.get(position).getCarInfo(context).get(1));
        holder.textViewCar2.setText(listaSchedules.get(position).getCarInfo(context).get(2));
        holder.textViewState.setText(listaSchedules.get(position).getState());
        holder.listaSchedules = this.listaSchedules;
    }

    @Override
    public int getItemCount() {
        return listaSchedules.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ArrayList<Schedule> listaSchedules;

        private TextView textViewSchedulingDate, textViewCompany, textViewCar, textViewCar2, textViewState;
        Fragment fragment;
        FragmentManager manager;
        ConstraintLayout llschedules;

        public MyViewHolder(@NonNull View itemView, FragmentManager manager) {
            super(itemView);
            textViewSchedulingDate = (TextView) itemView.findViewById(R.id.textViewSchedulingDate);
            textViewCompany = (TextView) itemView.findViewById(R.id.textViewCompany);
            textViewCar = (TextView) itemView.findViewById(R.id.textViewCar);
            textViewCar2 = (TextView) itemView.findViewById(R.id.textViewCar2);
            textViewState = (TextView) itemView.findViewById(R.id.textViewState);
            llschedules = (ConstraintLayout) itemView.findViewById(R.id.llschedules);
            this.manager = manager;

            llschedules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new Schedules_Appointment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("schedule", listaSchedules.get(getAdapterPosition()));
                    fragment.setArguments(bundle);
                    manager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack("editschedule")
                            .commit();
                }
            });
        }
    }
}
