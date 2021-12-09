package com.example.carbuddy.controllers;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.CarListAdapter;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.singletons.CarSingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_carInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_carInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int position;
    private Car car;

    private ImageView imageCar;
    private TextView txtVin, txtRegistration, txtCarType, txtFuelType, txtDisplacement, txtModelYear, txtKilometers;

    public fragment_carInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_carInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_carInfo newInstance(String param1, String param2) {
        fragment_carInfo fragment = new fragment_carInfo();
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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
            car = CarSingleton.getInstance(getContext()).getCars().get(position);
        } else {
            car = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(car.getBrand());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(car.getBrand());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(car.getModel());

        View view = inflater.inflate(R.layout.fragment_car_info,
                container, false);

        imageCar = view.findViewById(R.id.imageViewCar);
        txtVin = view.findViewById(R.id.textViewVin);
        txtRegistration = view.findViewById(R.id.textViewRegistration);
        txtCarType = view.findViewById(R.id.textViewCarType);
        txtFuelType = view.findViewById(R.id.textViewFuelType);
        txtDisplacement = view.findViewById(R.id.textViewDisplacement);
        txtModelYear = view.findViewById(R.id.textViewModelYear);
        txtKilometers = view.findViewById(R.id.textViewKilometers);

        chooseTypeColor();
        txtVin.setText(car.getVin());
        txtRegistration.setText(car.getRegistration());
        txtCarType.setText(car.getCartype());
        txtFuelType.setText(car.getFueltype());
        txtDisplacement.setText(String.valueOf(car.getDisplacement()));
        txtModelYear.setText(car.getModelyear());
        txtKilometers.setText(String.valueOf(car.getKilometers()));

        Button button = (Button) view.findViewById(R.id.btRepairs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RepairFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("carPosition", position);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack("Repair")
                        .commit();
            }
        });
        return view;
    }

    private void chooseTypeColor() {
        switch (car.getCartype()) {
            case "PASSENGER CAR":
                imageCar.setImageResource(R.drawable.ic_car);
                break;
            case "MOTORCYCLE":
                imageCar.setImageResource(R.drawable.ic_motorcycle);
                break;
            case "MULTIPURPOSE PASSENGER VEHICLE (MPV)":
                imageCar.setImageResource(R.drawable.ic_mpv);
                break;
            case "TRUCK ":
                imageCar.setImageResource(R.drawable.ic_truck);
                break;
        }
        imageCar.setColorFilter(Color.parseColor(car.getColor()));
    }
}