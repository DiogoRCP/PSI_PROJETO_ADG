package com.example.carbuddy.models;

import android.content.Context;

import com.example.carbuddy.singletons.CompaniesSingleton;

public class Schedule {
    private int id;
    private String currentdate, schedulingdate, repairdescription, state, repairtype, company, carregistration, carbrand, carmodel;

    public Schedule(int id, String currentdate, String schedulingdate, String repairdescription, String state, String repairtype, String company, String carregistration, String carbrand, String carmodel) {
        this.id = id;
        this.currentdate = currentdate;
        this.schedulingdate = schedulingdate;
        this.repairdescription = repairdescription;
        this.state = state;
        this.repairtype = repairtype;
        this.company = company;
        this.carregistration = carregistration;
        this.carbrand = carbrand;
        this.carmodel = carmodel;
    }

    /** Construtor para receber os dados do formulário **/
    public Schedule(int carId, String companyName, String schedulingDate, String repairDescription, String repairType, Context context){
        this.id = 0;
        this.carId = carId;
        this.companyId = checkCompanyIdByName(companyName, context);
        this.currentDate = "";
        this.schedulingDate = schedulingDate;
        this.repairDescription = repairDescription;
        this.state = "Pending";
        this.repairType = repairType;
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

    public String getCompany() {
        return company;
    }

    public String getCarregistration() {
        return carregistration;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public String getCarmodel() {
        return carmodel;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCarregistration(String carregistration) {
        this.carregistration = carregistration;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
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
                ", currentdate='" + currentdate + '\'' +
                ", schedulingdate='" + schedulingdate + '\'' +
                ", repairdescription='" + repairdescription + '\'' +
                ", state='" + state + '\'' +
                ", repairtype='" + repairtype + '\'' +
                ", company='" + company + '\'' +
                ", carregistration='" + carregistration + '\'' +
                ", carbrand='" + carbrand + '\'' +
                ", carmodel='" + carmodel + '\'' +
                '}';
    }
}
