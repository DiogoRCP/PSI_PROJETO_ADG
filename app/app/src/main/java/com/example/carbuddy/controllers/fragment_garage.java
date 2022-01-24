package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.libs.SelectedMainMenu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.CarListAdapter;
import com.example.carbuddy.listeners.CarsListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.models.ModeloBDHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_garage#newInstance} factory method to
 * create an instance of this fragment.
 */

/** extends Fragment - herança de classe do Fragmento
 * implements CarsListener - implementação do Listener
 * */
public class fragment_garage extends Fragment implements CarsListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    /** Definição das variáveis globais*/
    private String mParam1;
    private String mParam2;
    private RecyclerView myRecyclerView;
    private ArrayList<Car> lstCar;
    View v;
    private static ModeloBDHelper database;

    /** Construtor do fragmento em vazio - requerido pelo programa*/
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

    /** Método de criação de uma nova instância do fragmento */
    public static fragment_garage newInstance(String param1, String param2) {
        fragment_garage fragment = new fragment_garage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /** Função onCreate
     * - provém da extensão do Fragment
     * - Inicialização dos argumentos da instância criada anteriormente
     * - Conexão à base de dados
     * - carregarDados(): Inicializa a Singleton
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database = new ModeloBDHelper(getContext());
        carregarDados();
    }

    /** Inicializa a Singleton */
    private void carregarDados(){
        //Define o fragmento onde é disparado o listener
        CarSingleton.getInstance(getContext()).setCarsListener(this);

        //Carregar a Singleton com os Dados da API
        CarSingleton.getInstance(getContext()).CarregarListaCarros(getContext());

        //Variavel fica inicialmente carregada com o Singleton
        lstCar = CarSingleton.getInstance(getContext()).getCars();
    }

    /** Função onCreateView
     * - Gerar a view e tudo o que é visual
     * - Associar o layout Fragment Garage ao objeto view
     * - Definição de título
     * - Definição e chamada de textviews
     * - Definição e chamada de botão
     * - Chamada da RecyclerView
     * - Chamada do Adapter da view
     * - Floating Action Button - Implementação do botão float
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.Garage);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.Garage);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_garage, container, false);
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_garage, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclesViewCars);
        CarListAdapter listaAdapter = new CarListAdapter(getContext(), lstCar, super.getFragmentManager(), ((AppCompatActivity) getActivity()).getSupportActionBar());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(listaAdapter);

        v.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new fragment_form_car();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack("form_car")
                        .commit();
            }
        });

        SelectedMainMenu(getActivity(), R.id.btMainGarage);

        return v;
    }

    /** Função onRefreshCars
     * Vai buscar os carros à base de dados
     * Mostra os dados na recyclerview
     * */
    @Override
    public void onRefreshCars(ArrayList<Car> cars) {
        for (Car car : cars) {
            database.insertCars(car);
        }
        lstCar = cars;
        myRecyclerView.setAdapter(new CarListAdapter(getContext(), lstCar, super.getFragmentManager(), ((AppCompatActivity) getActivity()).getSupportActionBar()));
    }

    /** ?? */
    @Override
    public void onDeleteCreateCar() {

    }

    /** Função onResume
     * - atualizar os dados quando a view entra no estado onResume
     * - quando alteramos um dado e retornamos para a página anterior (que estava on pause anteriormente)
     * irá fazer onResume e atualizar automaticamente os dados
     * */

    /** ?? */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        carregarDados();
    }
}
