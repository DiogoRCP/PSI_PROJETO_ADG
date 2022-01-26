package com.example.carbuddy.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuddy.R;
import com.example.carbuddy.adapters.RepairListAdapter;
import com.example.carbuddy.listeners.RepairsListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.models.Repair;
import com.example.carbuddy.singletons.CarSingleton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/** extends Fragment - herança de classe do Fragmento
 * implements RepairsListener - implementação do Listener
 * */
public class RepairFragment extends Fragment implements RepairsListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    /** Definição das variáveis globais*/
    private String mParam1;
    private String mParam2;
    private RecyclerView myRecyclerView;
    private ArrayList<Repair> lstRepair;
    View v;
    private static ModeloBDHelper database;
    private Car car;
    private String carRegistration;

    /** Construtor do fragmento em vazio - requerido pelo programa*/
    public RepairFragment() {
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

    /** Método de criação de uma nova instância do fragmento */
    public static RepairFragment newInstance(String param1, String param2) {
        RepairFragment fragment = new RepairFragment();
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
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database = new ModeloBDHelper(getContext());

        //Recebe o carro vindo do car info
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            car = (Car) bundle.getSerializable("car");
            carRegistration = bundle.getString("carRegistration");
        }else{
            car = null;
        }

        //Define o fragmento onde é disparado o listener
        CarSingleton.getInstance(getContext()).setRepairsListener(this);

        //Carregar a Singleton com os dados da API
        CarSingleton.getInstance(getContext()).CarregarListaRepairs(getContext(), car);

        //Variavel fica inicialmente carregada com o Singleton
        if(CarSingleton.getInstance(getContext()).getRepairs(car) != null) {
            lstRepair = CarSingleton.getInstance(getContext()).getRepairs(car);
        }else{
            lstRepair = new ArrayList<>();
        }
    }

    /** Função onCreateView
     * - Gerar a view e tudo o que é visual
     * - Associar o layout Fragment Repair ao objeto view
     * - Definição de título
     * - Definição e chamada da RecyclerView
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Define o titulo do fragmento
        getActivity().setTitle(getString(R.string.Repairs));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.Repairs);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(carRegistration);

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_garage, container, false);
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_repair, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclerViewRepairs);
        RepairListAdapter listaRepairs = new RepairListAdapter(getContext(), lstRepair);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(listaRepairs);
        return v;
    }

    /** Função onRefreshRepair
     * Vai buscar as repairs à base de dados
     * Mostra os dados na recyclerview
     * */
    @Override
    public void onRefreshRepair(ArrayList<Repair> repairs) {
        for (Repair repair: repairs) {
            database.insertRepairs(repair);
        }
        lstRepair = CarSingleton.getInstance(getContext()).getRepairs(car);
        myRecyclerView.setAdapter(new RepairListAdapter(getContext(), lstRepair));
    }
}