package com.example.carbuddy.models;

import android.content.Context;

import com.example.carbuddy.singletons.CarSingleton;
import com.example.carbuddy.singletons.CompaniesSingleton;

import java.io.Serializable;
import java.util.ArrayList;

public class Schedule implements Serializable {
    private int id, carId, companyId;
    private String currentdate, schedulingdate, repairdescription, state, repairtype;

    public Schedule(int id, int carId, int companyId, String currentdate, String schedulingdate, String repairdescription, String state, String repairtype) {
        this.id = id;
        this.carId = carId;
        this.companyId = companyId;
        this.currentdate = currentdate;
        this.schedulingdate = schedulingdate;
        this.repairdescription = repairdescription;
        this.state = state;
        this.repairtype = repairtype;
    }

    /** Construtor para receber os dados do formulário **/
    public Schedule(int carId, String companyName, String schedulingDate, String repairDescription, String repairType, Context context){
        this.id = 0;
        this.carId = carId;
        this.companyId = checkCompanyIdByName(companyName, context);
        this.currentdate = "";
        this.schedulingdate = schedulingDate;
        this.repairdescription = repairDescription;
        this.state = "Pending";
        this.repairtype = repairType;
    }

    public int getId() {
        return id;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public String getSchedulingdate() {
        return schedulingdate;
    }

    public String getRepairdescription() {
        return repairdescription;
    }

    public String getState() {
        return state;
    }

    public String getRepairtype() {
        return repairtype;
    }

    public int getCarId() {
        return carId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public void setSchedulingdate(String schedulingdate) {
        this.schedulingdate = schedulingdate;
    }

    public void setRepairdescription(String repairdescription) {
        this.repairdescription = repairdescription;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRepairtype(String repairtype) {
        this.repairtype = repairtype;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    private int checkCompanyIdByName(String name, Context context){
        int CompId = 0;
        for (Company company : CompaniesSingleton.getInstance(context).getCompanies()) {
            if(company.getCompanyName().equals(name)){
                CompId = company.getId();
            }
        }
        return CompId;
    }

    public String getCompanyName(Context context){
        for (Company company : CompaniesSingleton.getInstance(context).getCompanies()) {
            if(company.getId() == this.companyId)
                return company.getCompanyName();
        }
        return "";
    }

    /** Devolve uma lista de três informações do carro
     * (0 - marca),
     * (1 - modelo),
     * (2 - matrícula) **/
    public ArrayList<String> getCarInfo(Context context){
        ArrayList<String> info = new ArrayList<>();
        for (Car car : CarSingleton.getInstance(context).getCars()) {
            if(car.getId() == this.carId) {
                info.add(car.getBrand());
                info.add(car.getModel());
                info.add(car.getRegistration());
            }
        }
        return info;
    }

    /** Método de retorna 0 - Data; 1- Hora **/
    public String[] getDateTime(){
        String[] dateTime = this.schedulingdate.split(" ");
        return dateTime;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", carId=" + carId +
                ", companyId=" + companyId +
                ", currentdate='" + currentdate + '\'' +
                ", schedulingdate='" + schedulingdate + '\'' +
                ", repairdescription='" + repairdescription + '\'' +
                ", state='" + state + '\'' +
                ", repairtype='" + repairtype + '\'' +
                '}';
    }
}
