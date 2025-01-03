package com.rtz.vehicle_manager.errors;

public class BrandNotfoundException extends RuntimeException {
    public BrandNotfoundException(String message) {
        super(message);
    }
}