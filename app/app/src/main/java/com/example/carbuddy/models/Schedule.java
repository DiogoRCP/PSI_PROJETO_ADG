package com.example.carbuddy.models;

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
