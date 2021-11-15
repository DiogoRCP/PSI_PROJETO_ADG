package com.example.carbuddy.models;

import android.graphics.Color;

public class Car {
    private int id, kilometers, userId;
    private String vin, brand, model, color, cartype, fueltype, registration, modelyear, state;
    private float displacement;

    public Car(int id, int kilometers, int userId, String vin, String brand, String model, String color, String cartype, String fueltype, String registration, String modelyear, String state, float displacement) {
        this.id = id;
        this.kilometers = kilometers;
        this.userId = userId;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.cartype = cartype;
        this.fueltype = fueltype;
        this.registration = registration;
        this.modelyear = modelyear;
        this.state = state;
        this.displacement = displacement;
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
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getModelyear() {
        return modelyear;
    }

    public void setModelyear(String modelyear) {
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
                ", cartype='" + cartype + '\'' +
                ", fueltype='" + fueltype + '\'' +
                ", registration='" + registration + '\'' +
                ", modelyear='" + modelyear + '\'' +
                ", state='" + state + '\'' +
                ", displacement=" + displacement +
                '}';
    }
}
