package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.libs.spinnerTheme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.example.carbuddy.R;
import com.example.carbuddy.listeners.CarsListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.utils.libs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_form_car#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_form_car extends Fragment implements CarsListener {

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
    private Button btColor;
    private ColorPickerDialog colorPickerDialog;
    public static RequestQueue volleyQueue = null;
    Car car;

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
        CarSingleton.getInstance(getContext()).setCreateListener(this);
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

        spinnerTheme(getContext(), spCarType, R.array.carType_array);
        spinnerTheme(getContext(), spFuelType, R.array.fuelType_array);

        view.findViewById(R.id.btAddCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car = new Car(txtVin.getText().toString(), txtBrand.getText().toString(),
                        txtModel.getText().toString(), "#"+colorPickerDialog.getCurrentColorAsHexa().substring(3, 9),
                        spCarType.getSelectedItem().toString(),
                        Float.parseFloat(txtDisplacement.getText().toString()),
                        spFuelType.getSelectedItem().toString(),
                        txtRegistration.getText().toString(), Integer.parseInt(txtYear.getText().toString()),
                        Integer.parseInt(txtKilometers.getText().toString()));

                System.out.println(car);
                try {
                    CarSingleton.getInstance(getContext()).AddCar(getContext(), car);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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
        spCarType = view.findViewById(R.id.spCarType);
        spFuelType = view.findViewById(R.id.spFuelType);

        spCarType.setSelection(-1);
        spFuelType.setSelection(-1);
        btColor = view.findViewById(R.id.bt_choose_color);
    }

    private void ColorPicker(View view) {
        colorPickerDialog = ColorPickerDialog.createColorPickerDialog(getContext(), ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                btColor.setBackgroundColor(color);
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
        txtVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtVin.getText().length() == 17) {
                    vinAPI();
                }
            }
        });
    }

    private void vinAPI() {
        if (!libs.isInternetConnection(getContext())) {
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

                                switch (response.getJSONArray("Results").getJSONObject(13).getString("Value")) {
                                    case "PASSENGER CAR":
                                        spCarType.setSelection(0);
                                        break;
                                    case "MULTIPURPOSE PASSENGER VEHICLE (MPV)":
                                        spCarType.setSelection(1);
                                        break;
                                    case "TRUCK ":
                                        spCarType.setSelection(2);
                                        break;
                                    case "TRUCK":
                                        spCarType.setSelection(2);
                                        break;
                                    case "MOTORCYCLE":
                                        spCarType.setSelection(3);
                                        break;
                                }

                                switch (response.getJSONArray("Results").getJSONObject(75).getString("Value")) {
                                    case "Diesel":
                                        spFuelType.setSelection(0);
                                        break;
                                    case "Hybrid":
                                        spFuelType.setSelection(1);
                                        break;
                                    case "Electric":
                                        spFuelType.setSelection(2);
                                        break;
                                    case "Gasoline":
                                        spFuelType.setSelection(3);
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            volleyQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onRefreshCars(ArrayList<Car> cars) {

    }

    @Override
    public void onDeleteCreateCar() {
        Toast.makeText(getContext(), car.getBrand()+" "+car.getModel()+" "+getString(R.string.Added), Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}