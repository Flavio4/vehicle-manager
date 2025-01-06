package com.rtz.vehicle_manager.dto;

import com.rtz.vehicle_manager.entities.Image;
import com.rtz.vehicle_manager.enums.CarStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    private String description;

    private List<ImageDTO> images;

    public CarDTO() {
    }

    public CarDTO(Long id, Long brandId, String brandName, String model, int year, double price, CarStatus status, String color, String plate,String description, List<ImageDTO> images) {
        this.id = id;
        this.brandId = brandId;
        this.brandName = brandName;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
        this.color = color;
        this.plate = plate;
        this.description = description;
        this.images = images;
    }

    public CarDTO(Long brandId, String brandName, String model, int year, double price, CarStatus status, String color, String plate,String description, List<ImageDTO> images) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
        this.color = color;
        this.plate = plate;
        this.description = description;
        this.images = images;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> imagesUrl) {
        this.images = imagesUrl;
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
