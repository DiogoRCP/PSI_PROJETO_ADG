package com.example.carbuddy.controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.CarListAdapter;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.CarSingleton;
import com.example.carbuddy.models.ModeloBDHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_garage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_garage extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView myRecyclerView;
    private ArrayList<Car> lstCar;
    View v;

    public fragment_garage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_garage.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_garage newInstance(String param1, String param2) {
        fragment_garage fragment = new fragment_garage();
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
        lstCar = CarSingleton.getInstance(getContext()).getCars();

        ModeloBDHelper database = new ModeloBDHelper(getContext());

        database.insertCars(lstCar.get(0));
        System.out.println(database.getAllCars().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_garage, container, false);
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_garage, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclesViewCars);
        CarListAdapter listaAdapter = new CarListAdapter(getContext(), lstCar);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(listaAdapter);
        return v;
    }

}
