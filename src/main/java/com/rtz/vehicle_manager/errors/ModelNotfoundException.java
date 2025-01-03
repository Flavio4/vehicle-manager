package com.rtz.vehicle_manager.errors;

public class ModelNotfoundException extends RuntimeException {
    public ModelNotfoundException(String message) {
        super(message);
    }
}