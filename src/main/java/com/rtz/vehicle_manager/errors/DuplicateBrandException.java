package com.rtz.vehicle_manager.errors;

public class DuplicateBrandException extends RuntimeException {
    public DuplicateBrandException() {
        super("La marca que intenta registrar ya existe");
    }
}