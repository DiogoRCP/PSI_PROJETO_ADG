package com.example.carbuddy.models;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getSchedulingdate() {
        return schedulingdate;
    }

    public void setSchedulingdate(String schedulingdate) {
        this.schedulingdate = schedulingdate;
    }

    public String getRepairdescription() {
        return repairdescription;
    }

    public void setRepairdescription(String repairdescription) {
        this.repairdescription = repairdescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepairtype() {
        return repairtype;
    }

    public void setRepairtype(String repairtype) {
        this.repairtype = repairtype;
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
