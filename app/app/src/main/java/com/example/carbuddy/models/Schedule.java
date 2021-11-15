package com.example.carbuddy.models;

public class Schedule {
    private int id, carId, companyId;
    private String currentDate, schedulingDate, repairDescription, state, repairType;

    public Schedule(int id, int carId, int companyId, String currentDate, String schedulingDate, String repairDescription, String state, String repairType) {
        this.id = id;
        this.carId = carId;
        this.companyId = companyId;
        this.currentDate = currentDate;
        this.schedulingDate = schedulingDate;
        this.repairDescription = repairDescription;
        this.state = state;
        this.repairType = repairType;
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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(String schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    @Override
    public String toString() {
        return "Schedules{" +
                "id=" + id +
                ", carId=" + carId +
                ", companyId=" + companyId +
                ", currentDate='" + currentDate + '\'' +
                ", schedulingDate='" + schedulingDate + '\'' +
                ", repairDescription='" + repairDescription + '\'' +
                ", state='" + state + '\'' +
                ", repairType='" + repairType + '\'' +
                '}';
    }
}
