package com.example.carbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.models.Car;

import java.util.ArrayList;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Car> listaCarros;

    public CarListAdapter(Context context, ArrayList<Car> listaCarros) {
        this.context = context;
        this.listaCarros = listaCarros;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_car, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvRegistration.setText(listaCarros.get(position).getRegistration());
    }

    @Override
    public int getItemCount() {
        return listaCarros.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvRegistration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRegistration = (TextView) itemView.findViewById(R.id.textViewRegistrationValue);

        }
    }
}
