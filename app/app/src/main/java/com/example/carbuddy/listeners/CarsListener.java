package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Car;

import java.util.ArrayList;

public interface CarsListener {
    void onRefreshCars(final ArrayList<Car> cars);
    void onDeleteCreateCar();
}
