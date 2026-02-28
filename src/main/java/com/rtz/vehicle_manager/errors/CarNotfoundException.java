package com.rtz.vehicle_manager.errors;

public class CarNotfoundException extends RuntimeException {

    private Long id;

    public CarNotfoundException(Long id) {
        super("Vehiculo con id " + id + " no encontrado");
    }

    public CarNotfoundException(String message) {
        super(message);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}