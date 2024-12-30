package com.rtz.vehicle_manager.models;

import com.rtz.vehicle_manager.enums.CarStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CarModel {

    @NotNull(message = "El id de la marca es obligatorio")
    @NotEmpty(message = "El id de la marca no puede estar vacío")
    private Long brandId;

    @NotNull(message = "El modelo del vehículo es obligatorio")
    @NotEmpty(message = "El modelo del vehículo no puede estar vacío")
    private String model;

    private int year;

    private double price;

    private CarStatus status;

    private String color;

    private String plate;

    public CarModel() {
    }

    public CarModel(Long brandId, String model, int year, double price, CarStatus status, String color, String plate) {
        this.brandId = brandId;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
        this.color = color;
        this.plate = plate;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
