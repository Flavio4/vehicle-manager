package com.rtz.vehicle_manager.errors;

public class CarNotfoundException extends RuntimeException {

    private Long id;

    public CarNotfoundException(Long id) {
        super("Vehiculo con id " + id + " no encontrado");
    }
}