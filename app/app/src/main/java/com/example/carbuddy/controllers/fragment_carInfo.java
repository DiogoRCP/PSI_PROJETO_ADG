package com.example.carbuddy.controllers;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.CarListAdapter;
import com.example.carbuddy.listeners.CarsListener;
import com.example.carbuddy.listeners.DeleteDialogListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.utils.DeleteConfirmationDialogFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_carInfo#newInstance} factory method to
 * create an instance of this fragment.
 */

/** extends Fragment - herança de classe do Fragmento
 * implements DeleteDialogListener, CarsListener - implementação do Listener
 * */
public class fragment_carInfo extends Fragment implements DeleteDialogListener, CarsListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    /** Definição das variáveis globais*/
    private String mParam1;
    private String mParam2;
    private Car car;

    private ImageView imageCar;
    private TextView txtVin, txtBrand, txtModel, txtRegistration, txtCarType, txtFuelType, txtDisplacement, txtModelYear, txtKilometers;

    /** Construtor do fragmento em vazio - requerido pelo programa*/
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

    /** Método de criação de uma nova instância do fragmento */
    public static fragment_carInfo newInstance(String param1, String param2) {
        fragment_carInfo fragment = new fragment_carInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /** Função onCreate
     * - provém da extensão do Fragment
     * - Inicialização dos argumentos da instância criada anteriormente
     * - setHasOptionsMenu(true): Função necessária para conseguirmos ter acesso ao menu existente
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Recebe os dados do carro vindo da garagem com um bundle
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            car = (Car) bundle.getSerializable("car");
        } else {
            car = null;
        }
        setHasOptionsMenu(true);

        CarSingleton.getInstance(getContext()).setDeleteListener(this);
    }

    /** Função necessária para conseguirmos ter acesso ao menu existente */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.car_repair, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /** - Função que permite gerar uma ação ao clicar no item do menu
     *  - Identificação do item do menu
     *  - Criação de um novo intent para inicializar a ação do menu neste fragmento
     *  - Aceder ás activities que são provenientes de fragmentos
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        Bundle bundle;
        switch (item.getItemId()) {
            case R.id.bt_repairs_menu:
                fragment = new RepairFragment();
                bundle = new Bundle();
                bundle.putSerializable("car", car);
                bundle.putString("carRegistration", car.getRegistration());
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack("Repair")
                        .commit();
                break;
            case R.id.bt_schedules_menu:
                fragment = new Schedules_Appointment();
                bundle = new Bundle();
                bundle.putSerializable("car", car);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack("SchedulesAppointment")
                        .commit();
                break;
            case R.id.bt_editar_menu_car:
                fragment = new fragment_form_car();
                bundle = new Bundle();
                bundle.putSerializable("carToEdit", car);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack("EditCar")
                        .commit();
                break;
            case R.id.bt_apagar_menu_car:
                DeleteConfirmationDialogFragment deleteFragment = new DeleteConfirmationDialogFragment();
                deleteFragment.setDeleteYesListener(this);
                deleteFragment.show(getChildFragmentManager(), DeleteConfirmationDialogFragment.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Função onCreateView
     * - Gerar a view e tudo o que é visual
     * - Associar o layout Fragment Car Info ao objeto view
     * - Definição de título */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Definição do titulo do fragmento
        getActivity().setTitle(R.string.InfoCarro);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.InfoCarro);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

        //Definição do layout associado ao fragmento
        View view = inflater.inflate(R.layout.fragment_car_info,
                container, false);

        //Obtem as referencias dos objetos na view e coloca os dados no carro
        writeForm(view);

        return view;
    }

    //Obtem as referencias dos objetos na view e coloca os dados no carro
     /** Definição e chamada de textviews e imagens */
    private void writeForm(View view){
        imageCar = view.findViewById(R.id.imageViewCar);
        txtVin = view.findViewById(R.id.textViewVin);
        txtBrand = view.findViewById(R.id.textViewBrand);
        txtModel = view.findViewById(R.id.textViewModel);
        txtRegistration = view.findViewById(R.id.textViewRegistration);
        txtCarType = view.findViewById(R.id.textViewCarType);
        txtFuelType = view.findViewById(R.id.textViewFuelType);
        txtDisplacement = view.findViewById(R.id.textViewDisplacement);
        txtModelYear = view.findViewById(R.id.textViewModelYear);
        txtKilometers = view.findViewById(R.id.textViewKilometers);

        chooseTypeColor();
        txtBrand.setText(car.getBrand());
        txtModel.setText(car.getModel());
        txtVin.setText(car.getVin());
        txtRegistration.setText(car.getRegistration());
        txtCarType.setText(car.getCartype());
        txtFuelType.setText(car.getFueltype());
        txtDisplacement.setText(String.valueOf(car.getDisplacement()));
        txtModelYear.setText(String.valueOf(car.getModelyear()));
        txtKilometers.setText(String.valueOf(car.getKilometers()));
    }

    /** Seletor do tipo de carro para definir a imagem*/
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
            case "TRUCK":
                imageCar.setImageResource(R.drawable.ic_truck);
                break;
        }
        imageCar.setColorFilter(Color.parseColor(car.getColor()));
    }

    /** Opção de eliminação de um veículo */
    @Override
    public void onDeleteYes(int id) {
        switch (id) {
            case -1:
                // Botão yes
                CarSingleton.getInstance(getContext()).DeleteCar(getContext(), car.getId());
                break;
            case -2:
                // Botão no
                break;
            default:
                break;
        }
    }

    /** Refresh cars */
    @Override
    public void onRefreshCars(ArrayList<Car> cars) {

    }

    /** Função que apaga o carro da base de dados e mostra a mensagem de como foi eliminado */
    @Override
    public void onDeleteCreateCar() {
        ModeloBDHelper database = new ModeloBDHelper(getContext());
        database.deleteCar(car.getId());
        Toast.makeText(getContext(), car.getBrand()+" "+car.getModel()+" "+getString(R.string.Deleted), Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}