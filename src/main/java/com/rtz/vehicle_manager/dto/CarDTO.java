package com.rtz.vehicle_manager.dto;

import com.rtz.vehicle_manager.enums.CarStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CarDTO {

    private Long id;

    @NotNull(message = "El id de la marca es obligatorio")
    private Long brandId;

    private String brandName;

    @NotNull(message = "El modelo del vehículo es obligatorio")
    @NotEmpty(message = "El modelo del vehículo no puede estar vacío")
    private String model;

    @Positive(message = "El año del vehículo debe ser mayor a 0")
    private int year;

    @Positive(message = "El precio del vehículo debe ser mayor a 0")
    private double price;

    private CarStatus status;

    private String color;

    private String plate;

    public CarDTO() {
    }

    public CarDTO(Long id, Long brandId, String brandName, String model, int year, double price, CarStatus status, String color, String plate) {
        this.id = id;
        this.brandId = brandId;
        this.brandName = brandName;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
        this.color = color;
        this.plate = plate;
    }

    public CarDTO(Long brandId, String brandName, String model, int year, double price, CarStatus status, String color, String plate) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
        this.color = color;
        this.plate = plate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", status=" + status +
                ", color='" + color + '\'' +
                ", plate='" + plate + '\'' +
                '}';
    }
}
