package com.example.carbuddy.models;

import android.content.ContentValues;
import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Car implements Serializable {
    private int id, kilometers, userId, modelyear;
    private String vin, brand, model, color, carType, fuelType, registration, state;
    private float displacement;
    private ArrayList<Repair> repairs;

    public Car(int id, String vin, String brand, String model, String color, String carType, float displacement, String fuelType, String registration, int modelyear, int kilometers, String state, int userId) {
        this.id = id;
        this.kilometers = kilometers;
        this.userId = userId;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.carType = carType;
        this.fuelType = fuelType;
        this.registration = registration;
        this.modelyear = modelyear;
        this.state = state;
        this.displacement = displacement;

        this.repairs = new ArrayList<>();
    }

    /** Construtor sem id e sem UserId **/
    public Car(String vin, String brand, String model, String color, String carType, float displacement, String fuelType, String registration, int modelyear, int kilometers){
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.carType = carType;
        this.displacement = displacement;
        this.fuelType = fuelType;
        this.registration = registration;
        this.modelyear = modelyear;
        this.kilometers = kilometers;
        this.state = "Accepted";
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCartype() {
        return carType;
    }

    public void setCartype(String cartype) {
        this.carType = cartype;
    }

    public String getFueltype() {
        return fuelType;
    }

    public void setFueltype(String fueltype) {
        this.fuelType = fueltype;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getModelyear() {
        return modelyear;
    }

    public void setModelyear(int modelyear) {
        this.modelyear = modelyear;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }

    public ArrayList<Repair> getRepairs() {
        return repairs;
    }

    public void setRepairs(ArrayList<Repair> repairs) {
        this.repairs = repairs;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", kilometers=" + kilometers +
                ", userid=" + userId +
                ", vin='" + vin + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", cartype='" + carType + '\'' +
                ", fueltype='" + fuelType + '\'' +
                ", registration='" + registration + '\'' +
                ", modelyear='" + modelyear + '\'' +
                ", state='" + state + '\'' +
                ", displacement=" + displacement +
                '}';
    }
}
