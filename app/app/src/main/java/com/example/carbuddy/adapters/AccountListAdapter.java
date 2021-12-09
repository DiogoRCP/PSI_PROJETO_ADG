package com.example.carbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.RepairListAdapter;
import com.example.carbuddy.models.Account;
import com.example.carbuddy.models.Repair;

import java.util.ArrayList;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Account> listaAccount;

    public AccountListAdapter(Context context, ArrayList<Account> listaAccount) {
        this.context = context;
        this.listaAccount = listaAccount;
    }

    @NonNull
    @Override
    public AccountListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list_account, parent, false);
        AccountListAdapter.MyViewHolder vHolder = new AccountListAdapter().MyViewHolder(v);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RepairListAdapter.MyViewHolder holder, int position) {
        holder.textViewAccUserName.setText(listaAccount.get(position).getRepairDate());
        holder.textViewAccuUserPass.setText(listaAccount.get(position).getRepairtype());
        holder.textViewNif.setText(String.valueOf(listaAccount.get(position).getKilometers()));
        holder.textViewBirthday.setText(listaAccount.get(position).getState());
        holder.textViewPhoneNumber.setText(listaAccount.get(position).getState());
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
