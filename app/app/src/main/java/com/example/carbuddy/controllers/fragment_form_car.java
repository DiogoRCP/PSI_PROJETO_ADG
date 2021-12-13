package com.example.carbuddy.controllers;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.example.carbuddy.R;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.utils.Json_Objects_Convertor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private EditText txtVin, txtBrand, txtModel, txtYear, txtDisplacement,
            txtRegistration, txtKilometers;
    private Spinner spFuelType, spCarType;
    public static RequestQueue volleyQueue = null;

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
        volleyQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.AddVehicle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.AddVehicle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

        View view = inflater.inflate(R.layout.fragment_form_car,
                container, false);

        ColorPicker(view);
        findId(view);
        vinSearch(view);

        return view;
    }

    private void findId(View view) {
        txtVin = view.findViewById(R.id.txtVin);
        txtBrand = view.findViewById(R.id.txtBrand);
        txtModel = view.findViewById(R.id.txtModel);
        txtYear = view.findViewById(R.id.txtYear);
        txtDisplacement = view.findViewById(R.id.txtDisplacement);
        txtRegistration = view.findViewById(R.id.txtRegistration);
        txtKilometers = view.findViewById(R.id.txtKilometers);
    }

    private void ColorPicker(View view) {
        ColorPickerDialog colorPickerDialog = ColorPickerDialog.createColorPickerDialog(getContext(), ColorPickerDialog.DARK_THEME);
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
    }

    private void vinSearch(View view) {
        txtVin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Json_Objects_Convertor.isInternetConnection(getContext())) {
                        Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
                    } else {
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET,
                                        "https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/" + txtVin.getText() + "?format=json",
                                        null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            txtBrand.setText(response.getJSONArray("Results").getJSONObject(6).getString("Value"));
                                            txtModel.setText(response.getJSONArray("Results").getJSONObject(8).getString("Value"));
                                            txtYear.setText(response.getJSONArray("Results").getJSONObject(9).getString("Value"));
                                            txtDisplacement.setText(response.getJSONArray("Results").getJSONObject(69).getString("Value"));
                                            //spCarType.(response.getJSONArray("Results").getJSONObject(13).getString("Value"));
                                            //spFuelType.(response.getJSONArray("Results").getJSONObject(75).getString("Value"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                        System.out.println("Ola");
                                    }
                                });

                        volleyQueue.add(jsonObjectRequest);
                    }
                }
            }
        });
    }
}