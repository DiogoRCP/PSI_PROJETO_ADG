package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.libs.isInternetConnection;
import static com.example.carbuddy.utils.libs.spinnerTheme;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_form_car#newInstance} factory method to
 * create an instance of this fragment.
 */

/** extends Fragment - herança de classe do Fragmento
 * implements CarListener - implementação do Listener
 * */
public class fragment_form_car extends Fragment implements CarsListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    /** Definição das variáveis globais*/
    private String mParam1;
    private String mParam2;
    private EditText txtVin, txtBrand, txtModel, txtYear, txtDisplacement,
            txtRegistration, txtKilometers;
    private Spinner spFuelType, spCarType;
    private Button btColor;
    private ColorPickerDialog colorPickerDialog;
    public static RequestQueue volleyQueue = null;
    private Car car;
    private boolean editar;
    private String carColor;

    /** Construtor do fragmento em vazio - requerido pelo programa*/
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
    /** Método de criação de uma nova instância do fragmento */
    public static fragment_form_car newInstance(String param1, String param2) {
        fragment_form_car fragment = new fragment_form_car();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /** Função onCreate
     * - provém da extensão do Fragment
     * - Inicialização dos argumentos da instância criada anteriormente
     * - Volley: Criação de um request para a API
     * - Bundle: Receber o carro e utilizar este fragmento para editar
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleyQueue = Volley.newRequestQueue(getContext());
        CarSingleton.getInstance(getContext()).setCreateListener(this);
        editar = false;
        carColor = "#ffffff";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            car = (Car) bundle.getSerializable("carToEdit");
            if (bundle.getSerializable("carToEdit") != null) {
                editar = true;
            }
        }
    }

    /** Função onCreateView
     * - Gerar a view e tudo o que é visual
     * - Associar o layout Fragment Form Car ao objeto view
     * - Definição de título
     * - Definição e chamada de textviews do formulário
     * - Definição e chamada de botão
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int title = (editar) ? R.string.EditVehicle : R.string.AddVehicle;

        getActivity().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

        View view = inflater.inflate(R.layout.fragment_form_car,
                container, false);

        ColorPicker(view);
        findId(view);
        vinSearch();

        spinnerTheme(getContext(), spCarType, R.array.carType_array);
        spinnerTheme(getContext(), spFuelType, R.array.fuelType_array);

        view.findViewById(R.id.btAddCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidarCampos()) {
                    Car newCar = new Car(txtVin.getText().toString(), txtBrand.getText().toString(),
                            txtModel.getText().toString(), carColor,
                            spCarTypeToApi(),
                            Float.parseFloat(txtDisplacement.getText().toString()),
                            spFuelToApi(),
                            txtRegistration.getText().toString(), Integer.parseInt(txtYear.getText().toString()),
                            Integer.parseInt(txtKilometers.getText().toString()));
                    try {
                        if (!editar) {
                            CarSingleton.getInstance(getContext()).AddCar(getContext(), newCar);
                        } else {
                            newCar.setId(car.getId());
                            CarSingleton.getInstance(getContext()).EditCar(getContext(), newCar);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (editar) {
            editarVehicleForm();
        }
        return view;
    }

    /** É uma função que não permite a criação ou edição de um carro quando os dados não estão
     * devidamente preenchidos */
    private boolean ValidarCampos() {
        boolean error = true;
        if (txtVin.getText().length() < 17) {
            txtVin.setError(getString(R.string.VinCheck));
            error = false;
        }
        if (txtBrand.getText().toString().isEmpty()) {
            txtBrand.setError(getString(R.string.EditTextRequired));
            error = false;
        }
        if (txtModel.getText().toString().isEmpty()) {
            txtModel.setError(getString(R.string.EditTextRequired));
            error = false;
        }
        if (txtYear.getText().toString().length() < 4 ||
                Integer.parseInt(txtYear.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR) ||
                Integer.parseInt(txtYear.getText().toString()) < 1950) {
            txtYear.setError(getString(R.string.ValidYear));
            error = false;
        }
        if (txtDisplacement.getText().toString().isEmpty()) {
            txtDisplacement.setError(getString(R.string.EditTextRequired));
            error = false;
        }
        if (txtRegistration.getText().toString().isEmpty()) {
            txtRegistration.setError(getString(R.string.EditTextRequired));
            error = false;
        }
        if (txtKilometers.getText().toString().isEmpty()) {
            txtKilometers.setError(getString(R.string.EditTextRequired));
            error = false;
        }

        return error;
    }

    /** Passa os dados do objeto para o design */
    private void editarVehicleForm() {
        txtVin.setText(car.getVin());
        txtBrand.setText(car.getBrand());
        txtModel.setText(car.getModel());
        txtYear.setText(String.valueOf(car.getModelyear()));
        txtDisplacement.setText(String.valueOf(car.getDisplacement()));
        txtRegistration.setText(car.getRegistration());
        txtKilometers.setText(String.valueOf(car.getKilometers()));
        spFuelType.setSelection(spinnerFuelNameToIndex());
        spCarType.setSelection(spinnerTypeNameToIndex());
        colorPickerDialog.setInitialColor(Color.parseColor(car.getColor()));
        btColor.setBackgroundColor(Color.parseColor(car.getColor()));
        carColor = car.getColor();
    }

    /** Receber o objeto da view*/
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

    /** Função responsável pelo campo de definição de cor do veículo */
    private void ColorPicker(View view) {
        colorPickerDialog = ColorPickerDialog.createColorPickerDialog(getContext(), ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                btColor.setBackgroundColor(color);
                carColor = "#" + hexVal.substring(3, 9);
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

    /** Procura dos dados do veículo através do VIN, utilizando uma API externa */
    private void vinSearch() {
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
                    if (!editar || !txtVin.getText().toString().equals(car.getVin())) {
                        vinAPI();
                    }
                }
            }
        });
    }

    /** Preenchimento dos dados do veículo através do Vin, utilizando uma API externa */
    private void vinAPI() {
        if (!isInternetConnection(getContext())) {
            Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET,
                            "https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/" + txtVin.getText() + "?format=json",
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (!response.getJSONArray("Results").getJSONObject(6).getString("Value").equals("null")) {
                                    txtBrand.setText(response.getJSONArray("Results").getJSONObject(6).getString("Value"));
                                }
                                if (!response.getJSONArray("Results").getJSONObject(8).getString("Value").equals("null")) {
                                    txtModel.setText(response.getJSONArray("Results").getJSONObject(8).getString("Value"));
                                }
                                if (!response.getJSONArray("Results").getJSONObject(9).getString("Value").equals("null")) {
                                    txtYear.setText(response.getJSONArray("Results").getJSONObject(9).getString("Value"));
                                }
                                if (!response.getJSONArray("Results").getJSONObject(69).getString("Value").equals("null")) {
                                    txtDisplacement.setText(response.getJSONArray("Results").getJSONObject(69).getString("Value"));
                                }

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

    /** Obtenção dos dados FuelType */
    private int spinnerFuelNameToIndex() {
        switch (car.getFueltype()) {
            case "Diesel":
                return 0;
            case "Hybrid":
                return 1;
            case "Electric":
                return 2;
            case "Gasoline":
                return 3;
        }
        return 0;
    }

    /** Obtenção dos dados CarType */
    private int spinnerTypeNameToIndex() {
        switch (car.getCartype()) {
            case "PASSENGER CAR":
                return 0;
            case "MULTIPURPOSE PASSENGER VEHICLE (MPV)":
                return 1;
            case "TRUCK":
                return 2;
            case "MOTORCYCLE":
                return 3;
        }
        return 0;
    }

    /** Obtenção do index e retornar o FuelType */
    private String spFuelToApi() {
        switch (spFuelType.getSelectedItemPosition()) {
            case 0:
                return "Diesel";
            case 1:
                return "Hybrid";
            case 2:
                return "Electric";
            case 3:
                return "Gasoline";
        }
        return "Diesel";
    }

    /** Obtenção do index e retornar o CarType */
    private String spCarTypeToApi() {
        switch (spCarType.getSelectedItemPosition()) {
            case 0:
                return "PASSENGER CAR";
            case 1:
                return "MULTIPURPOSE PASSENGER VEHICLE (MPV)";
            case 2:
                return "TRUCK";
            case 3:
                return "MOTORCYCLE";
        }
        return "PASSENGER CAR";
    }

    @Override
    public void onRefreshCars(ArrayList<Car> cars) {

    }

    /** Função que mostra a mensagem se o carro foi adicionado ou editado*/
    @Override
    public void onDeleteCreateCar() {
        if (!editar) {
            Toast.makeText(getContext(), getString(R.string.Added), Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), getString(R.string.Edited), Toast.LENGTH_SHORT).show();
            Fragment fragment = new fragment_garage();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        }
    }
}