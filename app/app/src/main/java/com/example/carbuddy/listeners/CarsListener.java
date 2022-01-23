package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Car;

import java.util.ArrayList;

/** Ficar à escuta para atualizar a lista de Carros (garagem) **/
public interface CarsListener {
    void onRefreshCars(final ArrayList<Car> cars);
    //Dispara quando queremos apagar um carro e carregamos yes no pop up de delete confirmation
    void onDeleteCreateCar();
}
