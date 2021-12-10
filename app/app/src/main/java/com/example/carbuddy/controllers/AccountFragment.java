package com.example.carbuddy.controllers;

import android.accounts.Account;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.AccountListAdapter;
import com.example.carbuddy.adapters.RepairListAdapter;
import com.example.carbuddy.listeners.LoginListener;
import com.example.carbuddy.models.Login;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.singletons.LoginSingleton;

import java.util.ArrayList;

public class AccountFragment extends Fragment implements LoginListener {



    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link com.example.carbuddy.controllers.RepairFragment#newInstance} factory method to
     * create an instance of this fragment.
     */

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
        private ModeloBDHelper database;
        private Login login;

        public AccountFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RepairFragment.
         */
        // TODO: Rename and change types and number of parameters

        public static com.example.carbuddy.controllers.AccountFragment newInstance(String param1, String param2) {
            com.example.carbuddy.controllers.AccountFragment fragment = new com.example.carbuddy.controllers.AccountFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database = new ModeloBDHelper(getContext());


        //Instanciar a Singleton
        LoginSingleton.getInstance(getContext()).setLoginListenerAccount(this);

        //Carregar os Dados da API
        LoginSingleton.getInstance(getContext()).apiAccount(getContext());

        //Variavel fica inicialmente carregada com o Singleton
        login = LoginSingleton.getInstance(getContext()).getLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account,
                container, false);


        getActivity().setTitle(getString(R.string.Account));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.Account);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(null);

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_garage, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onValidateLogin(Login login) {


    }
}

