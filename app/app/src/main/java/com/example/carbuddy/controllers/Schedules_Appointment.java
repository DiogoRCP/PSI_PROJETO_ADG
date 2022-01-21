package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.libs.spinnerTheme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carbuddy.R;
import com.example.carbuddy.listeners.CompaniesListener;
import com.example.carbuddy.listeners.SchedulesListener;
import com.example.carbuddy.models.Car;
import com.example.carbuddy.models.Company;
import com.example.carbuddy.models.Schedule;
import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.singletons.CompaniesSingleton;
import com.example.carbuddy.singletons.SchedulesSingleton;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Schedules_Appointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Schedules_Appointment extends Fragment implements SchedulesListener, CompaniesListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Car car;
    private TextView tvDate;
    private TextView tvHour;
    private TextView textViewCompanySchedule;
    final Calendar myCalendar = Calendar.getInstance();
    private Spinner spCompany;
    private Spinner spRepairType;
    private Button btSubmit;
    private EditText edtxtDescription;
    private boolean edit;
    private Schedule schedule;

    public Schedules_Appointment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Schedules_Appointment.
     */
    // TODO: Rename and change types and number of parameters
    public static Schedules_Appointment newInstance(String param1, String param2) {
        Schedules_Appointment fragment = new Schedules_Appointment();
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
        //Caso o objetivo seja o post de um agendamento- true se for para editar
        edit = false;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            car = (Car) bundle.getSerializable("car");
            if(bundle.getSerializable("schedule") != null){
                schedule = (Schedule) bundle.getSerializable("schedule");
                edit = true;
            }
        } else {
            car = null;
        }

        CompaniesSingleton.getInstance(getContext()).setCompaniesOnSchedulingListener(this);
        SchedulesSingleton.getInstance(getContext()).addSchedulesListener(this);

        CompaniesSingleton.getInstance(getContext()).CarregarListaCompanies(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Titulo da página para post
        getActivity().setTitle(R.string.Schedulesappointment);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.Schedulesappointment);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

        View view = inflater.inflate(R.layout.fragment_schedules__appointment, container, false);

        spCompany = view.findViewById(R.id.spCompany);
        spRepairType = view.findViewById(R.id.spRepairType);
        edtxtDescription = view.findViewById(R.id.edtxtDescription);
        textViewCompanySchedule = view.findViewById(R.id.textViewCompanySchedule);

        ChargeCompanies(view);
        // Alterar data
        dateManagement(view);

        // Alterar hora
        hourManagement(view);

        btSubmit = view.findViewById(R.id.btSubmitSchedule);
        btSubmitClick();

        //Verirficar se é para editar schedule
        editSchedule();

        return view;
    }

    private void dateManagement(View view) {
        tvDate = view.findViewById(R.id.tvDateSchedule);
        updateLabelDate();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelDate() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        tvDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void hourManagement(View view) {
        tvHour = view.findViewById(R.id.tvHourSchedule);
        updateLabelHour();

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateLabelHour();
            }
        };
        tvHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void updateLabelHour() {
        String myFormat = "HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        tvHour.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void ChargeCompanies(View view) {
        ArrayList<String> companies = new ArrayList<>();
        for (Company company : CompaniesSingleton.getInstance(getContext()).getCompanies()) {
            companies.add(company.getCompanyName());
        }

        spinnerTheme(getContext(), spCompany, companies);
        spinnerTheme(getContext(), spRepairType, R.array.repairtype_array);
    }

    private void btSubmitClick() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDescricao();
                if (verificarDescricao()) {

                    //POST
                    if(!edit) {
                        Schedule schedules = new Schedule(schedule.getCarId(), spCompany.getSelectedItem().toString(), tvDate.getText() + " " + tvHour.getText(), edtxtDescription.getText().toString(), spRepairType.getSelectedItem().toString(), v.getContext());

                        try {
                            SchedulesSingleton.getInstance(v.getContext()).AddSchedule(v.getContext(), schedules);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //PUT
                    else {
                        schedule.setSchedulingdate(tvDate.getText() + " " + tvHour.getText());
                        schedule.setRepairdescription(edtxtDescription.getText().toString());
                        schedule.setRepairtype(spRepairType.getSelectedItem().toString());
                        try {
                            SchedulesSingleton.getInstance(v.getContext()).PutSchedule(v.getContext(), schedule);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private boolean verificarDescricao() {
        if (edtxtDescription.getText().toString().isEmpty()) {
            edtxtDescription.setError(getString(R.string.WriteDescription));
            return false;
        }
        return true;
    }

    public void editSchedule(){
        if(edit){
            getActivity().setTitle(R.string.editschedule);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.editschedule);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
            textViewCompanySchedule.setVisibility(View.GONE);
            spCompany.setVisibility(View.GONE);
            btSubmit.setText(R.string.editschedule);

            //Carregar o fragmento com os dados da schedule
            tvDate.setText(schedule.getDateTime()[0]);
            tvHour.setText(schedule.getDateTime()[1]);
            edtxtDescription.setText(schedule.getRepairdescription());

            //carregar o spinner do repair type quando for edit
            switch (schedule.getRepairtype()){
                case "Maintenance":
                    spRepairType.setSelection(0);
                    break;
                case "Repair":
                    spRepairType.setSelection(1);
                    break;
            }
        }
    }

    @Override
    public void onRefreshSchedules(ArrayList<Schedule> schedules) {

    }

    @Override
    public void onDeleteCreateSchedule() {
        Toast.makeText(getContext(), schedule.getRepairtype() + " " + getString(R.string.Added), Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRefreshCompanies(ArrayList<Company> companies) {

    }
}