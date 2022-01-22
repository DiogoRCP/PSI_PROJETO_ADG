package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.libs.SelectedMainMenu;

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
import com.example.carbuddy.adapters.ScheduleListAdapter;
import com.example.carbuddy.listeners.CarsListener;
import com.example.carbuddy.listeners.SchedulesListener;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.models.Repair;
import com.example.carbuddy.models.Schedule;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.singletons.SchedulesSingleton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_schedules#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_schedules extends Fragment implements SchedulesListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static ModeloBDHelper database;

    private ArrayList<Schedule> lstSchedule;

    private RecyclerView myRecyclerView;

    View v;

    public fragment_schedules() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_schedules.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_schedules newInstance(String param1, String param2) {
        fragment_schedules fragment = new fragment_schedules();
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
        SchedulesSingleton.getInstance(getContext()).setSchedulesListener(this);

        //Carregar os Dados da API
        SchedulesSingleton.getInstance(getContext()).CarregarListaSchedules(getContext());

        if(SchedulesSingleton.getInstance(getContext()).getSchedules() != null) {
            lstSchedule = SchedulesSingleton.getInstance(getContext()).getSchedules();
        }else{
            lstSchedule = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.Schedules);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.Schedules);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(null);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_schedules, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclerViewSchedules);
        ScheduleListAdapter listaSchedules = new ScheduleListAdapter(getContext(), lstSchedule, super.getFragmentManager());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(listaSchedules);

        SelectedMainMenu(getActivity(), R.id.btMainSchedules);

        return v;
    }

    @Override
    public void onRefreshSchedules(ArrayList<Schedule> schedules) {
        for (Schedule schedule: schedules) {
            database.insertSchedules(schedule);
        }
        lstSchedule = SchedulesSingleton.getInstance(getContext()).getSchedules();
        myRecyclerView.setAdapter(new ScheduleListAdapter(getContext(), lstSchedule, super.getFragmentManager()));
    }

    @Override
    public void onDeleteCreateSchedule() {

    }
}