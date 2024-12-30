package com.rtz.vehicle_manager.enums;

public enum CarStatus {

    SOLD(0),
    AVAILABLE(1);

    private final int status;

    CarStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
