package com.example.carbuddy.models;

import android.content.Context;

import com.example.carbuddy.singletons.CompaniesSingleton;

public class Schedule {
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
