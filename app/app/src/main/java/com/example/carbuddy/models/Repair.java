package com.example.carbuddy.models;

public class Repair {
    private int id, kilometers, carId, contributorId;
    private String repairdate, repairdescription, state, repairtype;

    public Repair(int id, int kilometers, int carId, int contributorId, String repairdate, String repairdescription, String state, String repairtype) {
        this.id = id;
        this.kilometers = kilometers;
        this.carId = carId;
        this.contributorId = contributorId;
        this.repairdate = repairdate;
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

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    public String getRepairDate() {
        return repairdate;
    }

    public void setRepairDate(String repairDate) {
        this.repairdate = repairDate;
    }

    public String getRepairDescription() {
        return repairdescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairdescription = repairDescription;
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
        return "Repair{" +
                "id=" + id +
                ", kilometers=" + kilometers +
                ", carId=" + carId +
                ", contributorId=" + contributorId +
                ", repairDate='" + repairdate + '\'' +
                ", repairDescription='" + repairdescription + '\'' +
                ", state='" + state + '\'' +
                ", repairtype='" + repairtype + '\'' +
                '}';
    }
}
