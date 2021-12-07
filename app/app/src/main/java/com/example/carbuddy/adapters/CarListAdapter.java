package com.example.carbuddy.adapters;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.controllers.fragment_carInfo;
import com.example.carbuddy.models.Car;

import java.util.ArrayList;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Car> listaCarros;
    private FragmentManager manager;
    private ActionBar actionbar;

    public CarListAdapter(Context context, ArrayList<Car> listaCarros, FragmentManager manager, ActionBar actionBar) {
        this.context = context;
        this.listaCarros = listaCarros;
        this.manager = manager;
        this.actionbar = actionBar;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_car, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v, manager, actionbar);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvRegistration.setText(listaCarros.get(position).getRegistration());
        holder.tvModel.setText(listaCarros.get(position).getBrand() + " " + listaCarros.get(position).getModel());
        chooseTypeColor(holder, position);
    }

    private void chooseTypeColor(MyViewHolder holder, int position) {
        switch (listaCarros.get(position).getCartype()) {
            case "PASSENGER CAR":
                holder.imgView.setImageResource(R.drawable.ic_car);
                break;
            case "MOTORCYCLE":
                holder.imgView.setImageResource(R.drawable.ic_motorcycle);
                break;
            case "MULTIPURPOSE PASSENGER VEHICLE (MPV)":
                holder.imgView.setImageResource(R.drawable.ic_mpv);
                break;
            case "TRUCK ":
                holder.imgView.setImageResource(R.drawable.ic_truck);
                break;
        }
        holder.imgView.setColorFilter(Color.parseColor(listaCarros.get(position).getColor()));
    }

    @Override
    public int getItemCount() {
        return listaCarros.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvRegistration, tvModel;
        private ImageView imgView;
        Fragment fragment;
        FragmentManager manager;
        ActionBar actionBar;

        public MyViewHolder(@NonNull View itemView, FragmentManager manager, ActionBar actionBar) {
            super(itemView);
            tvRegistration = (TextView) itemView.findViewById(R.id.textViewRegistrationValue);
            tvModel = (TextView) itemView.findViewById(R.id.textViewModelValue);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            this.manager = manager;
            this.actionBar = actionBar;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            fragment = new fragment_carInfo();
            Bundle bundle = new Bundle();
            bundle.putInt("position", getAdapterPosition());
            fragment.setArguments(bundle);
            manager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack("infocar")
                    .commit();
        }
    }
}
