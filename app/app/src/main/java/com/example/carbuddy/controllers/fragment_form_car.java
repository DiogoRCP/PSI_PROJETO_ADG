package com.example.carbuddy.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.example.carbuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_form_car#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_form_car extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_form_car() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_form_car.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_form_car newInstance(String param1, String param2) {
        fragment_form_car fragment = new fragment_form_car();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.AddVehicle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.AddVehicle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

        View view = inflater.inflate(R.layout.fragment_form_car,
                container, false);

        ColorPickerDialog colorPickerDialog= ColorPickerDialog.createColorPickerDialog(getContext(), ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                view.findViewById(R.id.bt_choose_color).setBackgroundColor(color);
            }
        });

        colorPickerDialog.hideOpacityBar();

        view.findViewById(R.id.bt_choose_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog.show();
            }
        });
        return view;
    }
}